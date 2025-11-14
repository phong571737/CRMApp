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

        // Collapse groups
        // lấy view include
        itemInfoInclude = view.findViewById(R.id.item_opportunity_info);
        if (itemInfoInclude != null) {
            // toggle 1: chi tiết cơ hội
            layoutSectionHeader = itemInfoInclude.findViewById(R.id.layout_section_header);
            layoutBody = itemInfoInclude.findViewById(R.id.layout_body);
            iconArrowDetail = itemInfoInclude.findViewById(R.id.icon_arrow_detail);

            // toggle 2: quản lý
            layoutManagementHeader = itemInfoInclude.findViewById(R.id.layout_management_header);
            iconArrowManagement = itemInfoInclude.findViewById(R.id.icon_arrow_management);
            managementFieldContainer = findParentTextInput(itemInfoInclude.findViewById(R.id.et_management));

            setupToggle(layoutSectionHeader, layoutBody, iconArrowDetail);
            setupToggle(layoutManagementHeader, managementFieldContainer, iconArrowManagement);
        }

        // Footer
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
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


    /**
     * Toggle ẩn/hiện nội dung và đổi icon
     */
    private void setupToggle(View header, final View content, final ImageView icon) {
        if (header == null || content == null || icon == null) return;

        header.setOnClickListener(v -> {
            boolean expanded = content.getVisibility() == View.VISIBLE;
            content.setVisibility(expanded ? View.GONE : View.VISIBLE);
            icon.setImageResource(expanded ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
        });
    }

    /**
     * Tìm TextInputLayout cha của EditText (để toggle đúng container)
     */
    private View findParentTextInput(View child) {
        if (child == null) return null;
        View parent = (View) child.getParent();
        if (parent != null && parent.getClass().getSimpleName().contains("TextInputLayout")) {
            return parent;
        }
        if (parent != null && parent.getParent() instanceof View) {
            View grandParent = (View) parent.getParent();
            if (grandParent.getClass().getSimpleName().contains("TextInputLayout")) {
                return grandParent;
            }
        }
        return parent; // fallback nếu không tìm thấy
    }
}
