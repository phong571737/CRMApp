package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailQuote extends Fragment {

    private LinearLayout ll_root;
    private LayoutInflater inflater;
    private View section;
    private TextView tv_title;
    private ImageView iv_add_information;
    private LinearLayout ll_container;
    private List<section_quotedetail> sectionQuotedetails;
    public DetailQuote() {
        // Required empty public constructor
    }

    public static DetailQuote newInstance(String param1, String param2) {
        DetailQuote fragment = new DetailQuote();
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
        View view = inflater.inflate(R.layout.fragment_detail_quote, container, false);
        this.inflater = inflater;

        ll_root = view.findViewById(R.id.ll_root);

        sectionQuotedetails = createAllSection();

        for(section_quotedetail sectiondata: sectionQuotedetails){
            addSection(sectiondata);
        }

        return view;
    }

    private List<section_quotedetail> createAllSection(){
        List<section_quotedetail> list = new ArrayList<>();

        list.add(new section_quotedetail(
                "Thông tin báo giá",
                Arrays.asList(
                        new item_quote("Tiêu đề", "Báo giá triển khai CloundPro"),
                        new item_quote("Công ty", "Công ty TNHH Hoàng Việt Nam"),
                        new item_quote("Ngày liên hệ", "23/08/2024"),
                        new item_quote("Người liên hệ", "Đinh Phương Nam")
                )
        ));

        list.add(new section_quotedetail(
                "Thông tin liên quan ",
                Arrays.asList(
                        new item_quote("Cơ hội", "Phần mềm CloudPro")
                )
        ));

        list.add(new section_quotedetail(
                "Thông tin địa chỉ ",
                Arrays.asList(
                        new item_quote("Địa chỉ giao hàng", "13 Đường số 17-Vạn Phúc City"),
                        new item_quote("Quận/huyện giao hàng", "TP Thủ Đức"),
                        new item_quote("Tỉnh thành giao hàng", "TP Hồ Chí Minh"),
                        new item_quote("Quốc gia giao hàng", "Việt Nam")
                )
        ));

        return list;
    }

    private void addSection(section_quotedetail data){
        section = inflater.inflate(R.layout.section_quote_item, ll_root, false);

        tv_title = section.findViewById(R.id.tv_section_title);
        iv_add_information = section.findViewById(R.id.iv_addinfor);
        ll_container = section.findViewById(R.id.ll_section_container);

        tv_title.setText(data.getTitle());

        //add items
        for(item_quote item: data.getItems()){
            addRowtoSection(ll_container, item.getLabel(), item.getContent());
        }

        ll_root.addView(section);
    }

    private void addRowtoSection(LinearLayout parent, String label, String content){
        View row = inflater.inflate(R.layout.quote_items_row, parent, false);
        ((TextView) row.findViewById(R.id.tv_label)).setText(label);
        ((TextView) row.findViewById(R.id.tv_content)).setText(content);
        parent.addView(row);
    }
}