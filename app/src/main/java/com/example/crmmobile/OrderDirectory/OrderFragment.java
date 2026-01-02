package com.example.crmmobile.OrderDirectory;

import com.example.crmmobile.R;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.crmmobile.DataBase.DonHangRepository;

import java.util.List;
import android.widget.Toast;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.ArrayList;
import java.util.List;



public class OrderFragment extends Fragment {

    private DonHangRepository donHangRepository;
    private List<Order> allOrders;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private EditText edtSearch;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        //Fragment inflate layout tại đây
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context ctx = requireContext();
        initViews(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        // Lấy dữ liệu từ DB qua DonHangRepository
        donHangRepository = new DonHangRepository(ctx);
        orderList = donHangRepository.getOrdersForList();
        //allOrders: toàn bộ dữ liệu
        allOrders = donHangRepository.getOrdersForList();
        orderAdapter = new OrderAdapter(orderList, ctx, this::showBottomSheet);

        recyclerView.setAdapter(orderAdapter);
        // ===== Tìm kiếm đơn hàng =====
        if (edtSearch != null) {
            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterOrders(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }

        // ===== FAB dấu cộng: mở SOCreate1Activity =====
        if (fab != null) {
            fab.setOnClickListener(v -> {
                //Intent cần Context (không dùng this(Fragment))
                Intent i = new Intent(ctx, SOCreate1Activity.class);
                startActivity(i);
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, 0);
            return insets;
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerOrders);
        fab = view.findViewById(R.id.fabAddOrder);
        edtSearch = view.findViewById(R.id.editTextText);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (donHangRepository != null && orderList != null && orderAdapter != null) {
            List<Order> fresh = donHangRepository.getOrdersForList();

            allOrders.clear();
            allOrders.addAll(fresh);

            orderList.clear();
            orderList.addAll(fresh);

            orderAdapter.notifyDataSetChanged();
        }
    }
    private void reloadOrders() {
        if (donHangRepository == null) return;

        List<Order> fresh = donHangRepository.getOrdersForList();
        allOrders.clear();
        allOrders.addAll(fresh);

        orderList.clear();
        orderList.addAll(fresh);

        if (orderAdapter != null) orderAdapter.notifyDataSetChanged();
    }

    //BottomSheet hành động cho từng đơn
    public void showBottomSheet(Order order) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_actions, null, false);
        LinearLayout layoutActions = view.findViewById(R.id.layoutActions);

        //GHIM ĐƠN HÀNG
        addActionItem(layoutActions, R.drawable.keep, "Ghim đơn hàng", () -> {

            Toast.makeText(requireContext(), "Đã ghim đơn hàng", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // HỦY ĐƠN HÀNG
        addActionItem(layoutActions, R.drawable.ic_escape, "Hủy đơn hàng", () -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Hủy đơn hàng")
                    .setMessage("Bạn có chắc muốn hủy đơn " + order.getOrderCode() + " không?")
                    .setPositiveButton("Hủy đơn", (dialogInterface, which) -> {
                        int rows = donHangRepository.delete(order.getId());
                        if (rows > 0) {
                            Toast.makeText(requireContext(), "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();

                            orderList.clear();
                            orderList.addAll(donHangRepository.getOrdersForList());
                            orderAdapter.notifyDataSetChanged();

                            dialog.dismiss();
                        } else {
                            Toast.makeText(requireContext(), "Không thể hủy đơn hàng", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Đóng", null)
                    .show();
        });

        view.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    private void filterOrders(String query) {
        if (allOrders == null || orderList == null || orderAdapter == null) return;

        String key = query.toLowerCase().trim();

        orderList.clear();
        if (key.isEmpty()) {
            orderList.addAll(allOrders);
        } else {
            for (Order o : allOrders) {
                if (o.getOrderCode().toLowerCase().contains(key)
                        || o.getCompany().toLowerCase().contains(key)
                        || o.getPaymentStatus().toLowerCase().contains(key)) {
                    orderList.add(o);
                }
            }
        }
        orderAdapter.notifyDataSetChanged();
    }


    private void addActionItem(LinearLayout parent, int iconRes, String text, Runnable onClick) {
        View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_action, parent, false);
        ImageView icon = itemView.findViewById(R.id.actionIcon);
        TextView label = itemView.findViewById(R.id.actionText);
        icon.setImageResource(iconRes);
        label.setText(text);
        itemView.setOnClickListener(v -> onClick.run());
        parent.addView(itemView);
    }
}
