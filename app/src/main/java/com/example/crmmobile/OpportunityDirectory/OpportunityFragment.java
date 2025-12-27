package com.example.crmmobile.OpportunityDirectory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterOpportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityBottomSheetHelper;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityFormActivity;
import com.example.crmmobile.OpportunityDirectory.OpportunityFormFragment;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OpportunityFragment extends Fragment {
    private View btnAddOpportunity;
    private RecyclerView rvOpportunityBody;
    private OpportunityViewModel viewModel;
    private AdapterOpportunity opportunityAdapter;
    private EditText etSearch;


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
        btnAddOpportunity = view.findViewById(R.id.btn_add_opportunity);
        rvOpportunityBody = view.findViewById(R.id.rv_opportunity_body);

        // search EditText nằm trong layout include
        etSearch = view.findViewById(R.id.et_opportunity_search);

        // Nút back ở header fragment (nếu có)
        ImageButton btnOpportunityBack = view.findViewById(R.id.btn_opportunity_back);
        if (btnOpportunityBack != null) {
            btnOpportunityBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }
    }

    private void setupRecyclerView() {
        rvOpportunityBody.setLayoutManager(new LinearLayoutManager(getContext()));

        // INIT VIEWMODEL
        viewModel = new ViewModelProvider(requireActivity())
                .get(OpportunityViewModel.class);

        // INIT ADAPTER (không truyền list ở đây)
        opportunityAdapter = new AdapterOpportunity(
                new ArrayList<>(), // list rỗng, chờ LiveData cập nhật
                (item, id, anchor) -> {
                    // Sử dụng id nếu muốn update/delete trực tiếp
//                    OpportunityBottomSheetHelper.showBottomSheet(requireContext(), item, id, anchor);

                    OpportunityBottomSheetHelper.showBottomSheet(
                            requireContext(),
                            item,
                            anchor,
                            opportunityId -> viewModel.delete(opportunityId)
                    );
                },
                (item, id) -> openOpportunityDetail(id)
        );

        rvOpportunityBody.setAdapter(opportunityAdapter);

        // OBSERVE DATA TỪ VIEWMODEL
        viewModel.getOpportunities().observe(getViewLifecycleOwner(), list -> {
            Log.d("OpportunityFragment", "LiveData size = " + list.size());
            for (Opportunity o : list) {
                Log.d("OpportunityFragment", "Title: " + o.getTitle() + ", Price: " + o.getPrice());
            }
            opportunityAdapter.setData(list);
        });
    }

    private void setupClickListeners() {
        // Nút bubble → tạo mới → mở FormActivity
        if (btnAddOpportunity != null) {
            btnAddOpportunity.setOnClickListener(v -> openOpportunityCreateForm());
        }

//        search by keyword
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void openOpportunityDetail(int id) {
        Intent intent = new Intent(getContext(), OpportunityDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void openOpportunityCreateForm() {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "create");
        startActivity(intent);
    }

    private void openOpportunityUpdateForm(int id) {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "update");
        intent.putExtra("id", id);
        startActivity(intent);
    }

    // Reload data mỗi khi fragment visible lại (cập nhật sau add/update/delete)
    @Override
    public void onResume() {
        super.onResume();

        // Chỉ cần yêu cầu ViewModel load lại dữ liệu
        if (viewModel != null) {
            viewModel.reloadData();   // hàm này bạn thêm trong OpportunityViewModel
        }
    }

}
