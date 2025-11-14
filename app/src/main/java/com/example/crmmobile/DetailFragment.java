package com.example.crmmobile;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DetailFragment extends Fragment {

    private ImageView iv_addinforlead;
    private ImageView iv_addinforcompany;
    private ImageView iv_addinforaddress;
    private ImageView iv_addinfordescript;
    private ConstraintLayout cl_inforlead;
    private ConstraintLayout cl_addinforcompany;
    private ConstraintLayout cl_addinforaddress;
    private ConstraintLayout cl_addinfordescript;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_lead, container, false);
        iv_addinforlead = view.findViewById(R.id.iv_addinfor);
        cl_inforlead = view.findViewById(R.id.cl_infor_lead);

        iv_addinforcompany = view.findViewById(R.id.iv_add_company_infor);
        cl_addinforcompany = view.findViewById(R.id.cl_company_infor);

        iv_addinforaddress = view.findViewById(R.id.iv_add_address_infor);
        cl_addinforaddress = view.findViewById(R.id.cl_address_infor);

        iv_addinfordescript = view.findViewById(R.id.iv_descriptive_infor);
        cl_addinfordescript = view.findViewById(R.id.cl_descript_infor);

        setupaddInfor(iv_addinforaddress, cl_addinforaddress);
        setupaddInfor(iv_addinforlead, cl_inforlead);
        setupaddInfor(iv_addinforcompany, cl_addinforcompany);
        setupaddInfor(iv_addinfordescript, cl_addinfordescript);

        return view;
    }
    private void setupaddInfor(ImageView iv, ConstraintLayout cl){
        iv.setOnClickListener(v -> {
            if(cl.getVisibility() == View.GONE){
                cl.setVisibility(View.VISIBLE);
                iv.setImageResource(R.drawable.ic_arrow_up);

            }
            else{
                cl.setVisibility(View.GONE);
                iv.setImageResource(R.drawable.ic_arrow_down);
            }
        });
    }
}