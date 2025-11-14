package com.example.crmmobile;

import java.util.ArrayList;
import java.util.List;

public class OpportunityRepository {
    private static OpportunityRepository instance;
    private final List<Opportunity> opportunities;

    private OpportunityRepository() {
        opportunities = new ArrayList<>();
        seedData();
    }

    public static synchronized OpportunityRepository getInstance() {
        if (instance == null) {
            instance = new OpportunityRepository();
        }
        return instance;
    }

    // trả về bản copy (an toàn)
    public List<Opportunity> getAll() {
        return new ArrayList<>(opportunities);
    }

    public Opportunity getByPosition(int position) {
        if (position >= 0 && position < opportunities.size()) {
            return opportunities.get(position);
        }
        return null;
    }

    public void add(Opportunity opportunity) {
        opportunities.add(0, opportunity); // Thêm vào đầu danh sách
    }

    public void update(int position, Opportunity updated) {
        if (position >= 0 && position < opportunities.size()) {
            opportunities.set(position, updated);
        }
    }

    public void delete(int position) {
        if (position >= 0 && position < opportunities.size()) {
            opportunities.remove(position);
        }
    }

    private void seedData() {
        opportunities.add(new Opportunity(
                "Phần mềm CloudWork",
                "14.875.000 đ",
                "17/07/2024",
                "Thương lượng đàm phán",
                2, 5, "Trao đổi (1)"
        ));
        opportunities.add(new Opportunity(
                "Ứng dụng CRM Mobile",
                "22.300.000 đ",
                "12/08/2024",
                "Đã ký hợp đồng",
                1, 3, "Trao đổi (3)"
        ));
        opportunities.add(new Opportunity(
                "Website Quản lý Dự án",
                "9.700.000 đ",
                "05/09/2024",
                "Đang chờ phản hồi",
                0, 2, "Trao đổi (3)"
        ));
    }
}
