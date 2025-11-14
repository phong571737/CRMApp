package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate fragment_home.xml (đã include layout_home_header)
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // -----------------------------
        // 1️⃣ ACHIEVEMENTS AREA
        // -----------------------------
        RecyclerView recyclerAchievements = view.findViewById(R.id.recycler_achievements);
        recyclerAchievements.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        List<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement("Doanh số", "12,8 Tr", "★ Top 4"));
        achievements.add(new Achievement("Lead", "9,2 Tr", "★ Top 6"));
        achievements.add(new Achievement("Cơ hội", "5,1 Tr", "★ Top 8"));

        AchievementAdapter achievementAdapter = new AchievementAdapter(achievements, () -> {
            showAchievementDetailView();
        });
        recyclerAchievements.setAdapter(achievementAdapter);


        // -----------------------------
        // 2️⃣ QUICK MENU AREA
        // -----------------------------
        RecyclerView recyclerQuickMenu = view.findViewById(R.id.recycler_quick_menu);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerQuickMenu);

        recyclerQuickMenu.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        List<QuickMenuItem> quickMenus = Arrays.asList(
                new QuickMenuItem(R.drawable.ic_search, "Tìm kiếm"),
                new QuickMenuItem(R.drawable.ic_scanner, "Scan Card"),
                new QuickMenuItem(R.drawable.ic_bar_chart, "Báo cáo"),
                new QuickMenuItem(R.drawable.ic_list, "Đơn hàng"),
                new QuickMenuItem(R.drawable.ic_arrow_forward, "Thêm")
        );

        QuickMenuAdapter quickMenuAdapter = new QuickMenuAdapter(getContext(), quickMenus, item -> {
            switch (item.getTitle()) {
                case "Tìm kiếm":
                    break;
                case "Scan Card":
                    break;
                case "Báo cáo":
                    break;
                case "Đơn hàng":
                    break;
                case "Thêm":
                    break;
            }
        });

        recyclerQuickMenu.setAdapter(quickMenuAdapter);

        // -----------------------------
        // 3️⃣ GOALS AREA
        // -----------------------------
        RecyclerView recyclerGoals = view.findViewById(R.id.recyclerGoals);
        recyclerGoals.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal("Tăng doanh số", "45%"));
        goals.add(new Goal("Thêm khách hàng", "60%"));
        goals.add(new Goal("Hoàn thành dự án", "80%"));

        GoalAdapter goalAdapter = new GoalAdapter(goals);
        recyclerGoals.setAdapter(goalAdapter);

        // Nút thêm goal
        View btnAddGoal = view.findViewById(R.id.btnAddGoal);
        btnAddGoal.setOnClickListener(v -> {
            goalAdapter.addGoal(new Goal("Mục tiêu mới", "0%"));
        });

        // -----------------------------
        // 4️⃣ TAB SWITCH LOGIC
        // -----------------------------
        Button btnCompany = view.findViewById(R.id.btnCompany);
        Button btnDepartment = view.findViewById(R.id.btnDepartment);
        Button btnGroup = view.findViewById(R.id.btnGroup);
        Button btnPersonal = view.findViewById(R.id.btnPersonal);

        View.OnClickListener tabClickListener = v -> {
            Button[] tabs = {btnCompany, btnDepartment, btnGroup, btnPersonal};

            // Reset màu tất cả tab
            for (Button b : tabs) {
                b.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.darker_gray));
                b.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
            }

            // Đổi màu tab đang chọn
            ((Button) v).setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue));
            ((Button) v).setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            // Cập nhật danh sách mục tiêu tương ứng
            goals.clear();

            int id = v.getId();
            if (id == R.id.btnCompany) {
                goals.add(new Goal("Doanh thu quý", "45%"));
                goals.add(new Goal("Phát triển sản phẩm", "70%"));
            } else if (id == R.id.btnDepartment) {
                goals.add(new Goal("Chốt deal tháng 10", "55%"));
                goals.add(new Goal("Tuyển thêm nhân sự", "30%"));
            } else if (id == R.id.btnGroup) {
                goals.add(new Goal("Tăng tương tác nhóm", "65%"));
                goals.add(new Goal("Hoàn thành báo cáo tuần", "90%"));
            } else if (id == R.id.btnPersonal) {
                goals.add(new Goal("Học khóa mới", "20%"));
                goals.add(new Goal("Đạt KPI cá nhân", "75%"));
            }

            goalAdapter.notifyDataSetChanged();
        };

        btnCompany.setOnClickListener(tabClickListener);
        btnDepartment.setOnClickListener(tabClickListener);
        btnGroup.setOnClickListener(tabClickListener);
        btnPersonal.setOnClickListener(tabClickListener);
    }



    // Hàm riêng cho xử lý click achievement
    private void showAchievementDetailView() {
        // TODO: chuyển sang fragment chi tiết
    }

}


