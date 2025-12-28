package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.LeadRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityDAO;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConvertLeadActivity extends AppCompatActivity {
    private static final String TAG = "CONVERT_LEAD";
    private TextInputEditText et_chance, et_opportvalue, et_firstname, et_phonenumber, ed_first_lastName, ed_email;
    private MaterialAutoCompleteTextView et_predicted, ed_opportunity, et_sale_step, et_job, et_ship, et_companyname;
    private TextInputLayout til_chance, til_sale_step,til_company, til_email, til_sdt,
                    til_opportunity, til_predicted, til_job;
    private MaterialButton abort_button, save_button;
    private CheckBox cb_new_personal, cb_chance, cb_organization;
    private ImageView iv_back;
    private Lead lead;
    private int leadId = -1;
    private CaNhanRepository caNhanRepository;
    private CompanyRepository companyRepository;
    private OpportunityDAO opportunityDAO;
    private ViewModelLead viewModelLead;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_lead);
        viewModelLead = new ViewModelProvider(this).get(ViewModelLead.class);
        leadId = getIntent().getIntExtra("id", -1);
        caNhanRepository = new CaNhanRepository(this);
        companyRepository = new CompanyRepository(this);
        initVariables();

        setRequiredLabel(til_chance, R.string.chance);
        setRequiredLabel(til_sale_step, R.string.sales_step);
        setRequiredLabel(til_opportunity, R.string.success_rate);
        setRequiredLabel(til_predicted, R.string.predicted_date);
        setRequiredLabel(til_company, R.string.name_company);
        setRequiredLabel(til_email, R.string.Email);
        setRequiredLabel(til_sdt, R.string.SDT);
        setRequiredLabel(til_job, R.string.job_infor);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        abort_button.setOnClickListener(v -> {
            finish();
        });
        setCheckInit();

        cb_new_personal.setOnCheckedChangeListener(setNewPersonChecked);
        cb_chance.setOnCheckedChangeListener(setchanceChecked);
        cb_organization.setOnCheckedChangeListener(setOrganizationChecked);

        if (leadId != -1){
            LeadRepository leadRepository = new LeadRepository(this);
            lead = leadRepository.getLeadByID(leadId);
        }
        if (lead != null){
            bindLeadtoViewModel();
            bindViewModeltoUI();
        }

        save_button.setOnClickListener(v -> {
            if (!cb_new_personal.isChecked() && !cb_organization.isChecked()){
                Toast.makeText(this, "Vui lòng chọn contact hoặc tổ chức để chuyển đổi", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean has_converted = false;
            if (cb_new_personal.isChecked()){
                has_converted |= ConvertFromLeadtoContact();
            }
            if (cb_organization.isChecked()){
                has_converted |= ConvertLeadtoOrganization();
            }
            if (cb_chance.isChecked()){
                has_converted |= ConvertLeadtoChance();
            }

            if (has_converted){
                LeadRepository leadReposity = new LeadRepository(this);
                leadReposity.updateStatus(lead.getID(), "Đã chuyển đổi");
                Toast.makeText(this, "Chuyển đổi thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else {
                Toast.makeText(this, "Chuyển đổi thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindViewModeltoUI() {
        et_firstname.setText(viewModelLead.first_name.getValue());
        ed_first_lastName.setText(viewModelLead.hovatendem.getValue());
        ed_email.setText(viewModelLead.Email.getValue());
        et_phonenumber.setText(viewModelLead.phonenumber.getValue());
        et_companyname.setText(viewModelLead.company.getValue());
        et_job.setText(viewModelLead.Job.getValue());
        int Send_toID = lead.getGiaochoID();
        Log.e(TAG, "ID send: " + Send_toID);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(this);
            String tenNV = nhanVienRepository.getNameByID(lead.getGiaochoID());
            mainHandler.post(()->{
                viewModelLead.SendtoID.setValue(Send_toID);
                viewModelLead.SendtoName.setValue(tenNV);
                et_ship.setText(tenNV);
                Log.e(TAG, "Tên người phụ trách:" + tenNV);
            });
        });
    }

    private void bindLeadtoViewModel() {
        viewModelLead.title.setValue(lead.getTitle());
        viewModelLead.hovatendem.setValue(lead.getHovaTendem());
        viewModelLead.first_name.setValue(lead.getTen());
        viewModelLead.Sex.setValue(lead.getGioitinh());
        viewModelLead.Birthday.setValue(lead.getNgaysinh());
        viewModelLead.phonenumber.setValue(lead.getDienThoai());
        viewModelLead.Email.setValue(lead.getEmail());
        viewModelLead.state.setValue(lead.getTinhTrang());
        viewModelLead.Address.setValue(lead.getDiachi());
        viewModelLead.Province.setValue(lead.getTinh());
        viewModelLead.company.setValue(lead.getCongty());
        viewModelLead.District.setValue(lead.getQuanHuyen());
        viewModelLead.Nation.setValue(lead.getQuocGia());
        viewModelLead.Job.setValue(lead.getNganhnghe());
        viewModelLead.number_of_employees.setValue(lead.getSoNV());
        viewModelLead.Revenue.setValue(lead.getDoanhThu());
        viewModelLead.state.setValue(lead.getTinhTrang());
        viewModelLead.Tax.setValue(lead.getMaThue());
        viewModelLead.description.setValue(lead.getMota());
        viewModelLead.SendtoID.setValue(lead.getGiaochoID());
        viewModelLead.CreatedByID.setValue(lead.getNguoitaoID());
    }

    private boolean ConvertLeadtoChance() {
        if (TextUtils.isEmpty(et_chance.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập cơ hội", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_sale_step.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập bước bán hàng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(ed_opportunity.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập tỉ lệ thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_predicted.getText().toString())){
            Toast.makeText(this, "Vui lòng chọn ngày chốt dự kiến", Toast.LENGTH_SHORT).show();
            return false;
        }

        Opportunity opportunity = new Opportunity();
        opportunityDAO = new OpportunityDAO(this);
        opportunity.setTitle(et_chance.getText().toString());
        opportunity.setDate(et_predicted.getText().toString());
        opportunity.setPrice(TextUtils.isEmpty(et_opportvalue.getText())
                ? 0.0 : Double.parseDouble(et_opportvalue.getText().toString().trim()));
        opportunity.setStatus(et_sale_step.getText().toString());
        opportunityDAO.add(opportunity);
        return true;
    }

    private boolean ConvertFromLeadtoContact() {
        if (TextUtils.isEmpty(et_firstname.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(ed_email.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_phonenumber.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_ship.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập giao cho", Toast.LENGTH_SHORT).show();
            return false;
        }

        CaNhan cn = new CaNhan();
        cn.setDanhXung(lead.getTitle());
        cn.setHoVaTen(ed_first_lastName.getText().toString());
        cn.setTen(et_firstname.getText().toString());
        cn.setCongTy(et_companyname.getText().toString());
        cn.setGioiTinh(lead.getGioitinh());
        cn.setEmail(ed_email.getText().toString());
        cn.setDiDong(et_phonenumber.getText().toString());
        cn.setNgaySinh(lead.getNgaysinh());
        cn.setNgayTao(lead.getNgayLienHe());
        cn.setGiaoChoID(lead.getGiaochoID());
        cn.setDiaChi(lead.getDiachi());
        cn.setQuanHuyen(lead.getQuanHuyen());
        cn.setTinhTP(lead.getTinh());
        cn.setQuocGia(lead.getQuocGia());
        cn.setMoTa(lead.getMota());
        cn.setGhiChu(lead.getGhichu());

        long contactID = caNhanRepository.add(cn);
        return contactID > 0;
    }

    private boolean ConvertLeadtoOrganization() {
        String CompanyName = et_companyname.getText().toString().trim();
        //kiểm tra xem ô công ty có thông tin không
        if (CompanyName.isEmpty()){
            Toast.makeText(this,"Không có công ty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_job.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập ngành nghề", Toast.LENGTH_SHORT).show();
            return false;
        }

        companyRepository = new CompanyRepository(this);
        //Kiểm tra xem công ty có tồn tại hay chưa
        ToChuc existCompany = companyRepository.getCompanyByName(CompanyName);
        if (existCompany != null){//tồn tại
            Toast.makeText(this, "Công ty đã tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }

        //tạo mới
        ToChuc toChuc = new ToChuc();
        toChuc.setAddress(lead.getDiachi());
        toChuc.setCompanyName(et_companyname.getText().toString());
        toChuc.setIndustry(et_job.getText().toString());
        toChuc.setCity(lead.getThanhpho());
        toChuc.setDistrict(lead.getQuanHuyen());
        toChuc.setEmail(lead.getEmail());
        toChuc.setPhone(lead.getDienThoai());
        toChuc.setCountry(lead.getQuocGia());
        toChuc.setAssignedTo(lead.getGiaocho());
        companyRepository.addCompany(toChuc);
        return true;
    }

    private void setCheckInit() {
        //personal
        cb_new_personal.setChecked(false);
        ed_first_lastName.setEnabled(false);
        et_firstname.setEnabled(false);
        ed_email.setEnabled(false);
        et_phonenumber.setEnabled(false);
        et_ship.setEnabled(false);

        //chance
        cb_chance.setChecked(false);
        et_chance.setEnabled(false);
        et_sale_step.setEnabled(false);
        ed_opportunity.setEnabled(false);
        et_predicted.setEnabled(false);
        et_opportvalue.setEnabled(false);

        //organization
        cb_organization.setChecked(false);
        et_companyname.setEnabled(false);
        et_job.setEnabled(false);
    }

    CompoundButton.OnCheckedChangeListener setOrganizationChecked = (buttonView, isChecked) ->{
        et_companyname.setEnabled(isChecked);
        et_job.setEnabled(isChecked);
    };

    CompoundButton.OnCheckedChangeListener setNewPersonChecked = (buttonView, isChecked) -> {
        ed_first_lastName.setEnabled(isChecked);
        et_firstname.setEnabled(isChecked);
        ed_email.setEnabled(isChecked);
        et_phonenumber.setEnabled(isChecked);
        et_ship.setEnabled(isChecked);
    };

    CompoundButton.OnCheckedChangeListener setchanceChecked = (buttonView, isChecked)->{
        et_chance.setEnabled(isChecked);
        et_sale_step.setEnabled(isChecked);
        ed_opportunity.setEnabled(isChecked);
        et_predicted.setEnabled(isChecked);
        et_opportvalue.setEnabled(isChecked);
    };

    private void initVariables() {
        iv_back = findViewById(R.id.iv_back);
        et_chance = findViewById(R.id.ed_chance);
        et_sale_step = findViewById(R.id.et_sale_step);
        String[] stages = {
                "Nhận diện người ra quyết định",
                "Phân tích nhận thức",
                "Đề xuất/ Báo giá",
                "Thương lượng đàm phán"
        };
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, stages);
        et_sale_step.setAdapter(ad);
        ed_opportunity = findViewById(R.id.ed_opportunity);
        et_predicted = findViewById(R.id.ed_predicted);
        et_opportvalue = findViewById(R.id.et_opportvalue);
        et_companyname = findViewById(R.id.ed_company);
        et_job = findViewById(R.id.ed_job);
        et_firstname = findViewById(R.id.ed_firstname);
        et_phonenumber = findViewById(R.id.ed_sdt);
        et_ship = findViewById(R.id.ed_ship);
        abort_button = findViewById(R.id.abort_button);
        save_button = findViewById(R.id.save_button);
        cb_new_personal = findViewById(R.id.cb_new_personal);
        cb_chance = findViewById(R.id.cb_chance);
        cb_organization = findViewById(R.id.cb_organization);
        ed_first_lastName = findViewById(R.id.ed_first_lastName);
        ed_email = findViewById(R.id.ed_email);

        til_chance = findViewById(R.id.til_chance);
        til_sale_step = findViewById(R.id.til_sale_step);
        til_company = findViewById(R.id.til_company);
        getCompany();
        til_email = findViewById(R.id.til_email);
        til_sdt = findViewById(R.id.til_sdt);
        til_opportunity = findViewById(R.id.til_opportunity);
        til_predicted = findViewById(R.id.til_predicted);
        til_job = findViewById(R.id.til_job);
    }

    //List the companies in the database
    private void getCompany() {
        List<ToChuc> companyList = companyRepository.getAllCompany();
        List<String> companyName = new ArrayList<>();
        for (ToChuc tc : companyList){
            companyName.add(tc.getCompanyName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, companyName);
        et_companyname.setAdapter(adapter);
    }

    private void setRequiredLabel(TextInputLayout et, int stringid){
        String hint = getString(stringid) + " <font color='#FF0000'> * </font>";
        et.setHint(Html.fromHtml(hint));
    }
}
