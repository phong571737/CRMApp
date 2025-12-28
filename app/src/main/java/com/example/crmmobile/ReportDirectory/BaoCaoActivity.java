package com.example.crmmobile.ReportDirectory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaoCaoActivity extends AppCompatActivity {

    private BarChart barChartView;
    private TextView tvEmpty;
    private BarChart revenueChartView;
    private TextView tvEmptyRevenue;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);

        initViews();
        setupEvents();
        loadChartData();
        loadRevenueChartData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        barChartView = findViewById(R.id.barChartView);
        tvEmpty = findViewById(R.id.tvEmpty);
        revenueChartView = findViewById(R.id.revenueChartView);
        tvEmptyRevenue = findViewById(R.id.tvEmptyRevenue);
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadChartData() {
        CaNhanRepository caNhanRepository = new CaNhanRepository(this);
        NhanVienRepository nhanVienRepository = new NhanVienRepository(this);

        // Lấy số lượng CaNhan theo GIAOCHO
        Map<Integer, Integer> countByGiaoCho = caNhanRepository.getCountByGiaoCho();

        if (countByGiaoCho.isEmpty()) {
            barChartView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        // Tạo danh sách BarEntry và labels
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : countByGiaoCho.entrySet()) {
            int nhanVienId = entry.getKey();
            int count = entry.getValue();
            
            // Lấy tên nhân viên từ ID
            String tenNhanVien = nhanVienRepository.getNameByID(nhanVienId);
            if (tenNhanVien == null || tenNhanVien.isEmpty()) {
                tenNhanVien = "NV #" + nhanVienId;
            }
            
            entries.add(new BarEntry(index, count));
            labels.add(tenNhanVien);
            index++;
        }

        // Tạo BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Số lượng cá nhân");
        dataSet.setColor(Color.parseColor("#1E63F3"));
        dataSet.setValueTextColor(Color.parseColor("#111827"));
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true);

        // Tạo BarData
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.6f);

        // Cấu hình biểu đồ
        barChartView.setData(barData);
        barChartView.getDescription().setEnabled(false);
        barChartView.setFitBars(true);
        barChartView.animateY(1000);

        // Cấu hình trục X
        XAxis xAxis = barChartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setLabelCount(labels.size());
        xAxis.setTextColor(Color.parseColor("#6B7280"));
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);

        // Cấu hình trục Y trái
        YAxis leftAxis = barChartView.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f); // Chỉ hiển thị số nguyên
        leftAxis.setTextColor(Color.parseColor("#6B7280"));
        leftAxis.setTextSize(10f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.parseColor("#E5E7EB"));
        // Format trục Y thành số nguyên
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        // Tắt trục Y phải
        YAxis rightAxis = barChartView.getAxisRight();
        rightAxis.setEnabled(false);

        // Tắt legend
        barChartView.getLegend().setEnabled(false);

        barChartView.invalidate();
        barChartView.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
    }

    private void loadRevenueChartData() {
        DonHangRepository donHangRepository = new DonHangRepository(this);

        // Lấy doanh thu theo tháng
        Map<String, Long> revenueByMonth = donHangRepository.getRevenueByMonth();

        if (revenueByMonth.isEmpty()) {
            revenueChartView.setVisibility(View.GONE);
            tvEmptyRevenue.setVisibility(View.VISIBLE);
            return;
        }

        // Tạo danh sách BarEntry và labels
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Long> entry : revenueByMonth.entrySet()) {
            String monthKey = entry.getKey();
            long revenue = entry.getValue();

            entries.add(new BarEntry(index, revenue));
            labels.add(monthKey);
            index++;
        }

        // Tạo BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu (đồng)");
        dataSet.setColor(Color.parseColor("#10B981")); // Màu xanh lá để phân biệt với biểu đồ trên
        dataSet.setValueTextColor(Color.parseColor("#111827"));
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true);

        // Format giá trị với dấu phẩy ngăn cách hàng nghìn
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatCurrency((long) value);
            }
        });

        // Tạo BarData
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.6f);

        // Cấu hình biểu đồ
        revenueChartView.setData(barData);
        revenueChartView.getDescription().setEnabled(false);
        revenueChartView.setFitBars(true);
        revenueChartView.animateY(1000);

        // Cấu hình trục X
        XAxis xAxis = revenueChartView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setLabelCount(labels.size());
        xAxis.setTextColor(Color.parseColor("#6B7280"));
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);

        // Cấu hình trục Y trái
        YAxis leftAxis = revenueChartView.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(Color.parseColor("#6B7280"));
        leftAxis.setTextSize(10f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.parseColor("#E5E7EB"));
        // Format trục Y thành định dạng tiền tệ
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatCurrency((long) value);
            }
        });

        // Tắt trục Y phải
        YAxis rightAxis = revenueChartView.getAxisRight();
        rightAxis.setEnabled(false);

        // Tắt legend
        revenueChartView.getLegend().setEnabled(false);

        revenueChartView.invalidate();
        revenueChartView.setVisibility(View.VISIBLE);
        tvEmptyRevenue.setVisibility(View.GONE);
    }

    private String formatCurrency(long value) {
        if (value >= 1000000) {
            return String.format("%.1fM", value / 1000000.0);
        } else if (value >= 1000) {
            return String.format("%.1fK", value / 1000.0);
        }
        return String.valueOf(value);
    }
}

