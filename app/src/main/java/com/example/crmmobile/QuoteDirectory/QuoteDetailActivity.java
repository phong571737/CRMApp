package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterQuoteTab;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.QuoteRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class QuoteDetailActivity extends AppCompatActivity {
    private static final String TAG = "QUOTE_DETAIL";
    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView tv_company, tv_name, tv_user,tv_money,tv_state;
    private CreateQuoteViewModel viewModel;
    private QuoteRepository quoteRepository;
    private CaNhanRepository caNhanRepository;
    private CompanyRepository companyRepository;
    private Quote quote;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);
        viewModel = new ViewModelProvider(this).get(CreateQuoteViewModel.class);
        caNhanRepository = new CaNhanRepository(this);
        quoteRepository = new QuoteRepository(this);
        companyRepository = new CompanyRepository(this);

        initViews();
        int QuoteID = getIntent().getIntExtra("id", -1);
        if (QuoteID  != -1){
            quote = quoteRepository.getQuoteByID(QuoteID);
        }
        if (quote != null){
            setValues();
            int id = quote.getID();
            String code = String.format("BG-%04d", id);
            tv_user.setText(code);
            tv_company.setText(getCompanyByID(quote.getCompanyID()));
            int contactID = quote.getContactPersonID();
            CaNhan cn = caNhanRepository.getById(contactID);
            if (cn != null){
                String full_name = cn.getHoVaTen() + " " + cn.getTen();
                tv_name.setText(full_name);
            }
            else {
                tv_name.setText("");
            }

            Log.e(TAG, "ContactPerson:" + viewModel.ContactPersonID.getValue());
            tv_money.setText((quote.getTotalAmount() == null) ? "0 đ" : String.valueOf(quote.getTotalAmount() + " đ"));
            tv_state.setText(quote.getState());
        }

        setupAdapter();
        //back to quote layout
        iv_back.setOnClickListener(v -> {
            finish();
        });
    }

    private String getCompanyByID(Integer id) {
        List<ToChuc> companies = companyRepository.getAllCompany();
        for (ToChuc tc : companies){
            if (tc.getId() == id) return tc.getCompanyName();
        }
        return "";
    }

    private void setupAdapter() {
        AdapterQuoteTab adapterTab = new AdapterQuoteTab(this);
        viewPager2.setAdapter(adapterTab);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, position) ->{
            switch (position){
                case 0:
                    tab.setText("Tổng quan");
                    break;
                case 1:
                    tab.setText("Chi tiết");
                    break;
                default:
                    break;
            }
        } )).attach();
    }

    private void setValues() {
        viewModel.QuoteName.setValue(quote.getOrderCode());
        viewModel.CompanyName.setValue(quote.getCompany());
        viewModel.companyID.setValue(quote.getCompanyID());
        viewModel.ContactPersonName.setValue(quote.getContactPersonName());
        viewModel.ContactPersonID.setValue(quote.getContactPersonID());
        viewModel.OpportunityName.setValue(quote.getOpportunityName());
        viewModel.OpportunityID.setValue(quote.getOpportunityID());
        viewModel.State.setValue(quote.getState());
        viewModel.TotalAmount.setValue(quote.getTotalAmount());
        viewModel.address_Ship.setValue(quote.getAddress_Ship());
        viewModel.district_ship.setValue(quote.getDistrict_Ship());
        viewModel.province_ship.setValue(quote.getProvince_Ship());
        viewModel.nation_ship.setValue(quote.getNation_Ship());
    }

    private void initViews() {
        iv_back = findViewById(R.id.iv_back);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.vp_tab);
        tv_company = findViewById(R.id.tv_company);
        tv_name = findViewById(R.id.tv_name);
        tv_user = findViewById(R.id.tv_user);
        tv_money = findViewById(R.id.tv_money);
        tv_state = findViewById(R.id.tv_state);
    }
}
