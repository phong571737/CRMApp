package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OpportunityFormFragment extends Fragment {

    public static final String MODE_CREATE = "create";
    public static final String MODE_UPDATE = "update";

    private String mode;
    private Opportunity existingOpportunity;
    private int position = -1;

    private EditText etOpportunityName, etValue, etExpectedDate, etExpectedDate2, etDescription, etManagement;
    private AutoCompleteTextView etCompany, etContact, spSalesStage, spSuccessRate;
    private TextView tvHeaderTitle;
    private Button btnSave, btnCancel;
    private ImageButton btnBack;

    // Collapse fields
    private View itemInfoInclude;
    private LinearLayout layoutBody;
    private View layoutSectionHeader;
    private ImageView iconArrowDetail;

    private View layoutManagementHeader;
    private View managementFieldContainer; // TextInputLayout cha của et_management
    private ImageView iconArrowManagement;

    public static OpportunityFormFragment newInstance(Opportunity opportunity, int position, String mode) {
        OpportunityFormFragment fragment = new OpportunityFormFragment();
        Bundle args = new Bundle();
        args.putSerializable("opportunity", opportunity);
        args.putInt("position", position);
        args.putString("mode", mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_opportunity_form, container, false);

        initViews(view);
        setupDropdowns();
        handleArguments();
        setupActions();

        return view;
    }

    private void initViews(View view) {
        // Header
        tvHeaderTitle = view.findViewById(R.id.tv_opportunity_title_create);
        btnBack = view.findViewById(R.id.btn_opportunity_back);

        // Body
        etOpportunityName = view.findViewById(R.id.et_opportunity_name);
        etCompany = view.findViewById(R.id.et_company);
        etContact = view.findViewById(R.id.et_contact);
        etValue = view.findViewById(R.id.et_value);
        spSalesStage = view.findViewById(R.id.sp_sales_stage);
        spSuccessRate = view.findViewById(R.id.sp_success_rate);
        etExpectedDate = view.findViewById(R.id.et_expected_date);
        etExpectedDate2 = view.findViewById(R.id.et_expected_date_2);
        etDescription = view.findViewById(R.id.et_description);
        etManagement = view.findViewById(R.id.et_management);

        // Footer
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔹 Thông tin cơ hội
        setupToggle(view.findViewById(R.id.layout_section_header),
                view.findViewById(R.id.iv_arrow_detail_toggle),
                view.findViewById(R.id.layout_body));

        // 🔹 Thông tin quản lý
        setupToggle(view.findViewById(R.id.layout_management_header),
                view.findViewById(R.id.iv_arrow_management_toggle),
                view.findViewById(R.id.layout_management_content));


    }
    private void setupToggle(View header, ImageView toggleIcon, LinearLayout contentLayout) {
        View.OnClickListener listener = v -> {
            boolean isVisible = contentLayout.getVisibility() == View.VISIBLE;
            contentLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            toggleIcon.setImageResource(isVisible ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
        };

        header.setOnClickListener(listener);
        toggleIcon.setOnClickListener(listener);
    }


    private void setupDropdowns() {
        String[] companies = {"Google", "Microsoft", "Apple", "Meta"};
        String[] contacts = {"John Doe", "Jane Smith", "Alice Johnson"};
        String[] stages = {"Prospecting", "Qualification", "Proposal", "Negotiation", "Closed Won", "Closed Lost"};
        String[] rates = {"10%", "25%", "50%", "75%", "90%", "100%"};

        etCompany.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, companies));
        etContact.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, contacts));
        spSalesStage.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, stages));
        spSuccessRate.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, rates));
    }

    private void handleArguments() {
        if (getArguments() != null) {
            mode = getArguments().getString("mode", MODE_CREATE);
            existingOpportunity = (Opportunity) getArguments().getSerializable("opportunity");
            position = getArguments().getInt("position", -1);

            if (MODE_UPDATE.equals(mode) && existingOpportunity != null) {
                populateForm(existingOpportunity);
                tvHeaderTitle.setText("Cập nhật cơ hội");
                btnSave.setText("Cập nhật");
            } else {
                tvHeaderTitle.setText("Thêm cơ hội mới");
                btnSave.setText("Lưu");
            }
        }
    }

    private void setupActions() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        btnCancel.setOnClickListener(v -> requireActivity().finish());
        btnSave.setOnClickListener(v -> saveOpportunity());
    }

    private void populateForm(Opportunity opportunity) {
        etOpportunityName.setText(opportunity.getTitle());
        etCompany.setText(opportunity.getCompany());
        etContact.setText(opportunity.getContact());
        etValue.setText(opportunity.getPrice());
        spSalesStage.setText(opportunity.getStatus());
        spSuccessRate.setText(opportunity.getSuccessRate());
        etExpectedDate.setText(opportunity.getDate());
        etExpectedDate2.setText(opportunity.getExpectedDate2());
        etDescription.setText(opportunity.getExchangeText());
        etManagement.setText(opportunity.getManagement());
    }

    private void saveOpportunity() {
        Opportunity opportunity = createOpportunityFromForm();

        if (MODE_UPDATE.equals(mode) && position >= 0) {
            OpportunityRepository.getInstance().update(position, opportunity);
        } else {
            OpportunityRepository.getInstance().add(opportunity);
        }

        requireActivity().finish();
    }

    private Opportunity createOpportunityFromForm() {
        return new Opportunity(
                etOpportunityName.getText().toString(),
                etCompany.getText().toString(),
                etContact.getText().toString(),
                etValue.getText().toString(),
                spSalesStage.getText().toString(),
                spSuccessRate.getText().toString(),
                etExpectedDate.getText().toString(),
                etExpectedDate2.getText().toString(),
                etDescription.getText().toString(),
                etManagement.getText().toString()
        );
    }


}
