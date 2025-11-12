package com.example.crmmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class OpportunityFragment extends Fragment {

    private View layoutOpportunityForm;
    private View btnAddOpportunity;
    private View btnAddOpportunityBody;
    private RecyclerView rvOpportunityBody;
    private OpportunityAdapter opportunityAdapter;
    private BottomNavigationView bottomNav;
    private ScrollView bodyscroll;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);

        initViews(view);
        setupRecyclerView();
        setupClickListeners();
        return view;
    }

    private void initViews(View view) {
        layoutOpportunityForm = view.findViewById(R.id.layout_opportunity_form);
        btnAddOpportunity = view.findViewById(R.id.btn_add_opportunity);
        btnAddOpportunityBody = view.findViewById(R.id.btn_opportunity_body_add);
        rvOpportunityBody = view.findViewById(R.id.rv_opportunity_body);
        bottomNav = requireActivity().findViewById(R.id.nav_footer);
        bodyscroll = view.findViewById(R.id.scroll_body);

        // Nút back ở header fragment (nếu có)
        ImageButton btnOpportunityBack = view.findViewById(R.id.btn_opportunity_back);
        if (btnOpportunityBack != null) {
            btnOpportunityBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }
    }

    private void setupRecyclerView() {
        rvOpportunityBody.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Opportunity> opportunityList = OpportunityRepository.getInstance().getAll();

        opportunityAdapter = new OpportunityAdapter(
                opportunityList,
                (item, position, anchor) -> {
                    // anchor có thể dùng để đặt vị trí bottomsheet — hiện helper chỉ cần position + item
                    OpportunityBottomSheetHelper.showBottomSheet(requireContext(), item, position, anchor);
                },
                (item, position) -> openOpportunityDetail(item)
        );
        rvOpportunityBody.setAdapter(opportunityAdapter);
    }

    private void setupClickListeners() {
        View.OnClickListener openFormListener = v -> showOpportunityForm();

        if (btnAddOpportunity != null) btnAddOpportunity.setOnClickListener(openFormListener);
        if (btnAddOpportunityBody != null) btnAddOpportunityBody.setOnClickListener(openFormListener);

        // Nút back trong form (nếu layout_opportunity_form nằm cùng fragment)
        if (layoutOpportunityForm != null) {
            ImageButton btnOpportunityFormBack = layoutOpportunityForm.findViewById(R.id.btn_opportunity_back);
            if (btnOpportunityFormBack != null) {
                btnOpportunityFormBack.setOnClickListener(v -> hideOpportunityForm());
            }
        }
    }

    private void showOpportunityForm() {
        if (layoutOpportunityForm != null) layoutOpportunityForm.setVisibility(View.VISIBLE);
        if (bottomNav != null) bottomNav.setVisibility(View.GONE);
        if (bodyscroll != null) bodyscroll.setVisibility(View.GONE);
        if (btnAddOpportunity != null) btnAddOpportunity.setVisibility(View.GONE);
    }

    private void hideOpportunityForm() {
        if (layoutOpportunityForm != null) layoutOpportunityForm.setVisibility(View.GONE);
        if (bottomNav != null) bottomNav.setVisibility(View.VISIBLE);
        if (bodyscroll != null) bodyscroll.setVisibility(View.VISIBLE);
        if (btnAddOpportunity != null) btnAddOpportunity.setVisibility(View.VISIBLE);
    }

    private void openOpportunityDetail(Opportunity item) {
        Intent intent = new Intent(getContext(), OpportunityDetailActivity.class);
        intent.putExtra("opportunity", item);
        startActivity(intent);
    }

    private void openOpportunityCreateForm() {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "create");
        startActivity(intent);
    }

    private void openOpportunityUpdateForm(Opportunity item, int position) {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "update");
        intent.putExtra("opportunity", item);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    // Reload data mỗi khi fragment visible lại (cập nhật sau add/update/delete)
    @Override
    public void onResume() {
        super.onResume();
        // Lấy dữ liệu mới từ Repository và cập nhật adapter
        if (opportunityAdapter != null) {
            opportunityAdapter.setData(OpportunityRepository.getInstance().getAll());
        }
    }
}
