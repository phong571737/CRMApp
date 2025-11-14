package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ConvertLead extends Fragment {

    private onItemBackClick itemBackClick;

    public interface onItemBackClick{
        void onBackClick(int position);
    }
    public ConvertLead() {}

    public static ConvertLead newInstance(String param1, String param2) {
        ConvertLead fragment = new ConvertLead();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_convert_lead, container, false);

        ImageView iv_back = view.findViewById(R.id.iv_back);
        EditText et_chance = view.findViewById(R.id.ed_chance);
        EditText et_sale_step = view.findViewById(R.id.et_sale_step);
        EditText et_opportrate = view.findViewById(R.id.ed_opportrate);
        EditText et_predicted = view.findViewById(R.id.ed_predicted);
        EditText et_opportvalue = view.findViewById(R.id.et_opportvalue);
        EditText et_companyname = view.findViewById(R.id.ed_company);
        EditText et_job = view.findViewById(R.id.ed_job);
        EditText et_source = view.findViewById(R.id.ed_source);
        EditText et_firstname = view.findViewById(R.id.ed_firstname);
        EditText et_phonenumber = view.findViewById(R.id.ed_sdt);
        EditText et_ship = view.findViewById(R.id.ed_ship);

        setRequiredLabel(et_chance, R.string.chance);
        setRequiredLabel(et_sale_step, R.string.sales_step);
        setRequiredLabel(et_opportrate, R.string.success_rate);
        setRequiredLabel(et_predicted, R.string.predicted_date);
        setRequiredLabel(et_opportvalue, R.string.opportunaty_value);
        setRequiredLabel(et_companyname, R.string.name_company);
        setRequiredLabel(et_job, R.string.job_infor);
        setRequiredLabel(et_source, R.string.source);
        setRequiredLabel(et_firstname, R.string.first_name);
        setRequiredLabel(et_phonenumber, R.string.SDT);
        setRequiredLabel(et_ship, R.string.shipto);

        iv_back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void setRequiredLabel(EditText et, int stringid){
        String hint = getString(stringid) + " <font color='#FF0000'> * </font>";
        et.setHint(Html.fromHtml(hint));
    }
}