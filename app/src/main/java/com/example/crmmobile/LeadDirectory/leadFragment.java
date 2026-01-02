package com.example.crmmobile.LeadDirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterLead;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.BottomSheet.BottomSheetActionLead;
import com.example.crmmobile.DataBase.LeadRepository;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class leadFragment extends Fragment {
    private static final String TAG = "SEARCH";
    private RecyclerView recyclerLead;
    private AdapterLead adapter;
    private List<Lead> leadDB;
    private ArrayList<Lead> leadList;
    private FloatingActionButton lead_create_button;
    private BottomNavigationView navFooter;
    private ViewPager2 viewPager;
    private FrameLayout contain;
    private LeadRepository db;
    private ConstraintLayout serch_bar_Lead;
    private EditText text_search;

    public ActivityResultLauncher<Intent> editLeadLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == EditLeadActivity.RESULT_OK){
                    reloadList();
                }
            });

    public ActivityResultLauncher<Intent> convertLeadLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
                if (result.getResultCode() == ConvertLeadActivity.RESULT_OK){
                    reloadList();
                }
            });

    private void reloadList() {
        Executors.newSingleThreadExecutor().execute(() -> {
            leadDB = db.getAllLead();

            requireActivity().runOnUiThread(() -> {
                leadList.clear();
                leadList.addAll(leadDB);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        initViews(view);
        recyclerLead.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new LeadRepository(getContext());
        leadList = new ArrayList<>();

        text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterLead(s.toString());
            }
        });

        lead_create_button.setOnClickListener(v -> {
            Fragment createFragment = new create_Lead();

            viewPager.setVisibility(View.GONE);
            navFooter.setVisibility(View.GONE);
            contain.setVisibility(View.VISIBLE);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .hide(this)
                    .replace(R.id.main_container, createFragment)
                    .addToBackStack(null)
                    .commit();
        });
        adapter = new AdapterLead(requireContext(), leadList, new AdapterLead.onItemClickListener() {
            @Override
            public void onDotsClick(Lead item, int position) {
                BottomSheetActionLead.ShowBottomSheetLead(requireContext(), item, new BottomSheetActionLead.OnActionListenerLead() {
                    @Override
                    public void onEdit(Lead lead) {
                        Intent intent = new Intent(getContext(), EditLeadActivity.class);
                        intent.putExtra(AppConstant.LEAD_MODE, AppConstant.EDIT_MODE);
                        intent.putExtra("id", lead.getID());
                        editLeadLauncher.launch(intent);
                    }

                    @Override
                    public void onDelete(Lead lead) {
                        db.DeleteLead(lead.getID());//delete from database
                        leadList.remove(position); // delete from list(list displayed by recyclerview)
                        leadDB.remove(position);//delete from database

                        //update recyclerview
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, leadList.size());
                    }

                    @Override
                    public void onConvertLead(Lead lead) {
                        Intent intent = new Intent(getContext(), ConvertLeadActivity.class);
                        intent.putExtra(AppConstant.LEAD_MODE, AppConstant.CONVERT_MODE);
                        intent.putExtra("id", lead.getID());
                        convertLeadLauncher.launch(intent);
                    }

                    @Override
                    public void onOpenCalls(Lead lead) {
                        String phoneNumber = lead.getDienThoai();
                        if (!TextUtils.isEmpty(phoneNumber)) {
                            // Loại bỏ khoảng trắng và ký tự đặc biệt để chỉ giữ lại số
                            phoneNumber = phoneNumber.replaceAll("[^0-9+]", "");
                            if (!phoneNumber.isEmpty()) {
                                Uri phoneUri = Uri.parse("tel:" + phoneNumber);
                                Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onSendEmail(Lead lead) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        String email = lead.getEmail();
                        if (!TextUtils.isEmpty(email)){
                            emailIntent.setData(Uri.parse("mailto:" + email));
                            startActivity(emailIntent);
                        }
                    }

                });
            }

            @Override
            public void onMenuClick(Lead lead, int id) {
                Intent intent = new Intent(getContext(), DetailLeadActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        recyclerLead.setAdapter(adapter);
        loadLead();
        return view;
    }

    private void initViews(View view) {
        recyclerLead = view.findViewById(R.id.LeadRecycler);
        lead_create_button = view.findViewById(R.id.btn_add_lead);
        serch_bar_Lead = view.findViewById(R.id.serch_bar_Lead);
        text_search = view.findViewById(R.id.text_search);

        navFooter = requireActivity().findViewById(R.id.nav_footer);
        contain = requireActivity().findViewById(R.id.main_container);
        viewPager = requireActivity().findViewById(R.id.viewPager);
    }

    private void filterLead(String key) {
        if (leadDB == null) return;
        key = key.toLowerCase().trim();
        leadList.clear();
        if(key.isEmpty()){
            leadList.addAll(leadDB);
        }else {
            for (Lead lead : leadDB){
                String name = (lead.getTen() != null) ? lead.getTen().toLowerCase() : "";
                String hovatendem = (lead.getHovaTendem() != null) ? lead.getHovaTendem().toLowerCase() : "";
                String title = (lead.getTitle() != null) ? lead.getTitle().toLowerCase() : "";
                Log.e(TAG, "Danh xung: " + lead.getTitle());
                String fullname = title + " " + hovatendem + " " + name;
                if (fullname.contains(key)){
                    leadList.add(lead);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void loadLead() {
        Executors.newSingleThreadExecutor().execute(()->{
            leadDB = db.getAllLead();
            requireActivity().runOnUiThread(()->{
                leadList.clear();
                leadList.addAll(leadDB);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(AppConstant.KEY_CREATE_LEAD,
                this, (requestKey, bundle) -> {
            boolean refresh = bundle.getBoolean(AppConstant.REFRESH, false);
            if(refresh){
                // Load lại database
                reloadList();
            }
        });

        getParentFragmentManager().setFragmentResultListener(
                "REFRESH_HOATDONG",
                this,
                ((requestKey, result) -> {
                    if (result.getBoolean(AppConstant.REFRESH, false) && adapter != null){
                        adapter.notifyDataSetChanged();
                    }
                })
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}
