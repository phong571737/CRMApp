package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    RecyclerView rv_order;
    List<Order> orderList;

    AdapterOrder adapterOrder;

    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        View view =  inflater.inflate(R.layout.fragment_order, container, false);
        rv_order = view.findViewById(R.id.recyclerOrders);

        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList = new ArrayList<>();

        orderList.add(new Order("SO00109", "Công ty TNHH CloudGO", "2.139.000 đ", "30/07/2024", "Chưa thanh toán", "Mới"));
        orderList.add(new Order("SO00110", "Công ty ABC", "5.000.000 đ", "01/08/2024", "Đã thanh toán", "Đã giao"));
        orderList.add(new Order("SO00111", "Công ty XYZ", "3.500.000 đ", "02/08/2024", "Đang xử lý", "ĐH B2C"));
        orderList.add(new Order("SO00111", "Công ty XYZ", "3.500.000 đ", "02/08/2024", "Đang xử lý", "ĐH B2C"));

        adapterOrder = new AdapterOrder(requireContext(),orderList,order -> {
            Intent intent = new Intent(getContext(), OrderDetailActivity.class);

            // Gửi thông tin đơn hàng qua (nếu cần hiển thị ở tab Tổng quan)
            intent.putExtra("orderCode", order.getOrderCode());
            intent.putExtra("company", order.getCompany());
            intent.putExtra("date", order.getDate());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        rv_order.setAdapter(adapterOrder);
        return view;
    }
}