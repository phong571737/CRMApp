package com.example.crmmobile.OpportunityDirectory;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;

public class OpportunityFormFragment extends Fragment {

    private OpportunityFormViewModel formVM;
    private OpportunityFormHandler handler;

    private String mode;
    private int opId = -1;
    private Opportunity existing;
    private EditText etName, etValue, etDate1, etDate2, etDesc;
    private AutoCompleteTextView etCompany, etContact, etStage, etManager;
    private TextView tvTitle;
    private Button btnSave, btnCancel;
    private ImageButton btnBack;

    private boolean loaded1, loaded2, loaded3;

    private static final String TAG = "OP_FORM_DEBUG";

    public static OpportunityFormFragment newInstance(int id, String m) {
        Bundle b = new Bundle();
        b.putInt("id", id);
        b.putString("mode", m);

        OpportunityFormFragment f = new OpportunityFormFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        Log.d(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.layout_opportunity_form, container, false);

        formVM = new ViewModelProvider(requireActivity()).get(OpportunityFormViewModel.class);
        handler = formVM.getHandler();

        initViews(v);
        readArgs();
        setupActions();
        observeOpportunity();
        observeDropdowns();
        setupStaticDropdowns();

        setupDatePickerIcon(etDate1);
        setupDatePickerIcon(etDate2);

        if (getArguments() != null) {
            Log.d(TAG, "ARGS mode = " + getArguments().getString("mode")
                    + ", id = " + getArguments().getInt("id", -1));
        } else {
            Log.e(TAG, "ARGS = NULL");
        }

        return v;
    }

    private void observeOpportunity() {
        formVM.getEditingOpportunity().observe(getViewLifecycleOwner(), o -> {
            Log.d(TAG, "observeOpportunity() o = " + o);

            if (o != null) {
                existing = o;     // <<< SAVE
                Log.d(TAG, "existing SET → tryPopulate()");

                tryPopulate();
            }
        });
    }

    private void initViews(View v) {
        tvTitle = v.findViewById(R.id.tv_opportunity_title_create);
        btnBack = v.findViewById(R.id.btn_opportunity_back);

        etName = v.findViewById(R.id.et_opportunity_name);
        etCompany = v.findViewById(R.id.et_company);
        etContact = v.findViewById(R.id.et_contact);
        etValue = v.findViewById(R.id.et_value);
        etStage = v.findViewById(R.id.sp_sales_stage);
        etDate1 = v.findViewById(R.id.et_expected_date);
        etDate2 = v.findViewById(R.id.et_expected_date_2);
        etDesc = v.findViewById(R.id.et_description);
        etManager = v.findViewById(R.id.et_management);

        btnSave = v.findViewById(R.id.btn_save);
        btnCancel = v.findViewById(R.id.btn_cancel);
    }

    private void readArgs() {
        Log.d(TAG, "readArgs() START");

        if (getArguments() != null) {
            mode = getArguments().getString("mode");
            opId = getArguments().getInt("id", -1);
        }

        Log.d(TAG, "readArgs() mode = " + mode + ", opId = " + opId);


        if ("update".equals(mode)) {
            Log.d(TAG, "MODE = UPDATE → loadOpportunityById(" + opId + ")");

            tvTitle.setText("Cập nhật cơ hội");
            btnSave.setText("Cập nhật");
        } else {
            Log.d(TAG, "MODE = CREATE");

            tvTitle.setText("Thêm cơ hội mới");
            btnSave.setText("Lưu");

        }

        // Load object từ id
        if ("update".equals(mode)) {
            formVM.loadOpportunityById(opId);
        }
    }

    private void setupActions() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        btnCancel.setOnClickListener(v -> requireActivity().finish());

        btnSave.setOnClickListener(v -> {
            try {
                saveForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void observeDropdowns() {

        formVM.getCompanies().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etCompany, list);
            loaded1 = true;
            if ("update".equals(mode)) tryPopulate();
        });

        formVM.getContacts().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etContact, list);
            loaded2 = true;
            if ("update".equals(mode)) tryPopulate();
        });

        formVM.getEmployees().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etManager, list);
            loaded3 = true;
            if ("update".equals(mode)) tryPopulate();
        });
    }

    private <T> void setAdapter(AutoCompleteTextView ac, List<T> list) {
        ArrayAdapter<T> ad = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, list);
        ac.setAdapter(ad);
        ac.setThreshold(0);
        ac.setOnClickListener(v -> ac.showDropDown());

        ac.setOnItemClickListener((p, v, pos, id) -> {
            if (list.get(pos) instanceof ToChuc)
                formVM.setSelectedCompanyId(((ToChuc) list.get(pos)).getId());

            if (list.get(pos) instanceof CaNhan)
                formVM.setSelectedContactId(((CaNhan) list.get(pos)).getId());

            if (list.get(pos) instanceof Nhanvien)
                formVM.setSelectedManagementId(((Nhanvien) list.get(pos)).getId());
        });
    }

    private void setupStaticDropdowns() {
        String[] stages = {
                "Nhận diện người ra quyết định",
                "Phân tích nhận thức",
                "Đề xuất/ Báo giá",
                "Thương lượng đàm phán",
                "Thành công"
        };
        ArrayAdapter<String> ad = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, stages);
        etStage.setAdapter(ad);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDatePickerIcon(EditText edt) {
        edt.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                int drawableRight = 2;
                if (event.getRawX() >= (edt.getRight() - edt.getCompoundDrawables()[drawableRight].getBounds().width())) {
                    openDatePicker(edt);
                    return true;
                }
            }
            return false;
        });
    }

    private void openDatePicker(EditText targetEdt) {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, y, m, d) -> {
                    String formatted = handler.formatSelectedDate(d, m, y);
                    targetEdt.setText(formatted);
                },
                year, month, day
        );
        dialog.show();
    }


    private void tryPopulate() {
        Log.d(TAG, "tryPopulate() "
                + "existing=" + (existing != null)
                + ", loaded1=" + loaded1
                + ", loaded2=" + loaded2
                + ", loaded3=" + loaded3);

        if (existing == null) return;
        if (!loaded1 || !loaded2 || !loaded3) return;

        Log.d(TAG, "ALL READY → populateForm()");
        populateForm();
    }

    private void populateForm() {
        Log.d(TAG, "populateForm() START with existing=" + existing);

        etName.setText(existing.getTitle());
        etValue.setText(String.valueOf(existing.getPrice()));
        etDate1.setText(existing.getDate());
        etDate2.setText(existing.getExpectedDate2());
        etDesc.setText(existing.getDescription());
        etStage.setText(existing.getStatus(), false);

        formVM.setSelectedCompanyId(existing.getCompany());
        formVM.setSelectedContactId(existing.getContact());
        formVM.setSelectedManagementId(existing.getManagement());

        etCompany.setText(
                handler.findCompanyName(existing.getCompany(), formVM.getCompanies().getValue()),
                false
        );

        etContact.setText(
                handler.findContactName(existing.getContact(), formVM.getContacts().getValue()),
                false
        );

        etManager.setText(
                handler.findEmployeeName(existing.getManagement(), formVM.getEmployees().getValue()),
                false
        );

        Log.d("BUG_TEST", "populateForm()");
        Log.d("BUG_TEST", "existing.company = " + existing.getCompany());
        Log.d("BUG_TEST", "existing.contact = " + existing.getContact());
        Log.d("BUG_TEST", "existing.management = " + existing.getManagement());

        Log.d("BUG_TEST", "VM before set:");
        Log.d("BUG_TEST", "selectedCompanyId = " + formVM.getSelectedCompanyId());
        Log.d("BUG_TEST", "selectedContactId = " + formVM.getSelectedContactId());
        Log.d("BUG_TEST", "selectedManagementId = " + formVM.getSelectedManagementId());
    }

    private void saveForm() throws ParseException {
        Log.d(TAG, "saveForm() mode=" + mode + ", existing=" + existing);


        if (!handler.validateTitle(etName.getText().toString())) {
            etName.setError("Không được để trống");
            return;
        }

        Opportunity o = formVM.createOpportunity(
                mode,
                existing,
                etName.getText().toString(),
                etValue.getText().toString(),
                etStage.getText().toString(),
                etDate1.getText().toString(),
                etDate2.getText().toString(),
                etDesc.getText().toString()
        );

        Log.d("BUG_TEST", "===== SAVE FORM =====");
        Log.d("BUG_TEST", "mode = " + mode);

        Log.d("BUG_TEST", "name UI = " + etName.getText().toString());
        Log.d("BUG_TEST", "price UI = " + etValue.getText().toString());
        Log.d("BUG_TEST", "date1 UI = " + etDate1.getText().toString());
        Log.d("BUG_TEST", "date2 UI = " + etDate2.getText().toString());

        Log.d("BUG_TEST", "selectedCompanyId = " + formVM.getSelectedCompanyId());
        Log.d("BUG_TEST", "selectedContactId = " + formVM.getSelectedContactId());
        Log.d("BUG_TEST", "selectedManagementId = " + formVM.getSelectedManagementId());

        OpportunityViewModel vm = new ViewModelProvider(requireActivity()).get(OpportunityViewModel.class);

        if ("update".equals(mode)) {
            Log.d("OP_FORM_DEBUG", "saveForm() START update DB");
            vm.update(o);
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            Log.d("OP_FORM_DEBUG", "DB UPDATE DONE → finish activity");
        } else {
            vm.add(o);
            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
        }

        // RẢ KẾT QUẢ CHO DETAIL ACTIVITY
        requireActivity().setResult(AppCompatActivity.RESULT_OK);
        requireActivity().finish();

        Log.d("BUG_TEST", "Opportunity to save:");
        Log.d("BUG_TEST", "id = " + o.getId());
        Log.d("BUG_TEST", "company = " + o.getCompany());
        Log.d("BUG_TEST", "contact = " + o.getContact());
        Log.d("BUG_TEST", "management = " + o.getManagement());

    }


}
