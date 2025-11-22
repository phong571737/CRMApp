package com.example.crmmobile.feature.salesorder.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.feature.salesorder.model.Order;
import com.example.crmmobile.feature.salesorder.ui.create.SOCreate1Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dùng đúng layout fragment_order bạn gửi
        setContentView(R.layout.fragment_order);

        // ===== RecyclerView danh sách đơn =====
        RecyclerView recyclerView = findViewById(R.id.recyclerOrders); // id trùng XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("SO00109", "Công ty TNHH CloudGO",
                "2.139.000 đ", "30/07/2024", "Chưa thanh toán", "Mới"));
        orderList.add(new Order("SO00110", "Công ty ABC",
                "5.000.000 đ", "01/08/2024", "Đã thanh toán", "Đã giao"));
        orderList.add(new Order("SO00111", "Công ty XYZ",
                "3.500.000 đ", "02/08/2024", "Đang xử lý", "ĐH B2C"));

        recyclerView.setAdapter(new OrderAdapter(orderList, this));

        // ===== FAB tạo đơn mới (nếu sau này bạn thêm vào XML) =====
        // Trong fragment_order hiện chưa có id fabAddOrder, nên đoạn này
        // sẽ được bỏ qua nếu View không tồn tại (findViewById trả về null).
        FloatingActionButton fab = findViewById(R.id.fabAddOrder);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                Intent i = new Intent(this, SOCreate1Activity.class);
                startActivity(i);
            });
        }

        // KHÔNG dùng R.id.main nữa vì trong fragment_order không có id đó
        // => xoá EdgeToEdge và ViewCompat để tránh NPE.
    }

    // ===== BottomSheet hành động cho từng đơn =====
    public void showBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        View view = getLayoutInflater()
                .inflate(R.layout.bottom_sheet_actions, null, false);
        LinearLayout layoutActions = view.findViewById(R.id.layoutActions);

        addActionItem(layoutActions, R.drawable.keep, "Ghim",
                () -> Toast.makeText(this, "Đã ghim đơn hàng", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.cached, "Chuyển thành hóa đơn",
                () -> Toast.makeText(this, "Chuyển hóa đơn thành công", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.files, "Xuất file PDF",
                () -> Toast.makeText(this, "Xuất file PDF...", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.outgoingmail, "Gửi email kèm file PDF",
                () -> Toast.makeText(this, "Gửi thành công", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.copy, "Nhân đôi",
                () -> Toast.makeText(this, "Nhân đôi thành công", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.cancel, "Hủy đơn hàng",
                () -> Toast.makeText(this, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    private void addActionItem(LinearLayout parent, int iconRes,
                               String text, Runnable onClick) {
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_action, parent, false);
        ImageView icon = itemView.findViewById(R.id.actionIcon);
        TextView label = itemView.findViewById(R.id.actionText);
        icon.setImageResource(iconRes);
        label.setText(text);
        itemView.setOnClickListener(v -> onClick.run());
        parent.addView(itemView);
    }
}
