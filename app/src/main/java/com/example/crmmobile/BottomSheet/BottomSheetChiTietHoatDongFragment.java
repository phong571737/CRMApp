package com.example.crmmobile.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityRepository;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetChiTietHoatDongFragment extends BottomSheetDialogFragment {

    private static final String ARG_HOAT_DONG = "hoat_dong";

    private HoatDong hoatDong;
    private TextView tvTenHoatDong, tvTieuDe, tvMoTa, tvNguoiDungDuocMoi;
    private TextView tvCongTy, tvCaNhan, tvCoHoi, tvNguoiPhuTrach, tvTinhTrang;
    private Button btnHoanThanh;
    private ImageView icClose, icMore;

    public static BottomSheetChiTietHoatDongFragment newInstance(HoatDong hoatDong) {
        BottomSheetChiTietHoatDongFragment fragment = new BottomSheetChiTietHoatDongFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOAT_DONG, hoatDong);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_chitiet_hoatdong, container, false);

        // Lấy HoatDong từ arguments
        if (getArguments() != null) {
            hoatDong = (HoatDong) getArguments().getSerializable(ARG_HOAT_DONG);
        }

        initViews(view);
        loadData();

        return view;
    }

    private void initViews(View view) {
        icClose = view.findViewById(R.id.ic_close);
//        icMore = view.findViewById(R.id.ic_more);
        tvTenHoatDong = view.findViewById(R.id.tv_tenhoatdong);
       // tvTieuDe = view.findViewById(R.id.tv_tieude);
        tvMoTa = view.findViewById(R.id.tv_mota);
        tvNguoiDungDuocMoi = view.findViewById(R.id.tv_nguoidungduocmoi);
        tvCongTy = view.findViewById(R.id.tv_congty);
        tvCaNhan = view.findViewById(R.id.tv_canhan);
        tvCoHoi = view.findViewById(R.id.tv_cohoi);
        tvNguoiPhuTrach = view.findViewById(R.id.tv_nguoiphutrach);
        tvTinhTrang = view.findViewById(R.id.tv_tinhtrang);
        btnHoanThanh = view.findViewById(R.id.btn_hoanthanh);

        icClose.setOnClickListener(v -> dismiss());

        // TODO: Xử lý icMore click nếu cần
//        icMore.setOnClickListener(v -> {
//            // Có thể hiển thị menu thêm
//        });

        btnHoanThanh.setOnClickListener(v -> {
            // TODO: Xử lý logic hoàn thành hoạt động
            Toast.makeText(requireContext(), "Đã hoàn thành hoạt động", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    private void loadData() {
        if (hoatDong == null) {
            return;
        }

        // Tên hoạt động
        if (hoatDong.getTenHoatDong() != null && !hoatDong.getTenHoatDong().isEmpty()) {
            tvTenHoatDong.setText(hoatDong.getTenHoatDong());
        } else {
            tvTenHoatDong.setText("Không xác định");
        }

//        // Tiêu đề (cùng với tên hoạt động)
//        if (hoatDong.getTenHoatDong() != null && !hoatDong.getTenHoatDong().isEmpty()) {
//            tvTieuDe.setText(hoatDong.getTenHoatDong());
//        } else {
//            tvTieuDe.setText("Không xác định");
//        }

        // Mô tả
        if (hoatDong.getMoTa() != null && !hoatDong.getMoTa().isEmpty()) {
            tvMoTa.setText(hoatDong.getMoTa());
        } else {
            tvMoTa.setText("Không xác định");
        }

        // Người dùng được mời - query từ NhanVienRepository bằng giaoCho ID
        if (hoatDong.getGiaoCho() > 0) {
            NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
            String tenNguoiDuocMoi = nhanVienRepository.getNameByID(hoatDong.getGiaoCho());
            if (tenNguoiDuocMoi != null && !tenNguoiDuocMoi.isEmpty()) {
                tvNguoiDungDuocMoi.setText(tenNguoiDuocMoi);
            } else {
                tvNguoiDungDuocMoi.setText("Không xác định");
            }
        } else {
            tvNguoiDungDuocMoi.setText("Không xác định");
        }

        // Công ty - query từ CompanyRepository bằng toChuc ID
        if (hoatDong.getToChuc() > 0) {
            CompanyRepository companyRepository = new CompanyRepository(requireContext());
            ToChuc toChuc = companyRepository.getCompanyByID(hoatDong.getToChuc());
            if (toChuc != null && toChuc.getCompanyName() != null) {
                tvCongTy.setText(toChuc.getCompanyName());
            } else {
                tvCongTy.setText("Không xác định");
            }
        } else {
            tvCongTy.setText("Không xác định");
        }

        // Cá nhân - query từ CaNhanRepository bằng nguoiLienHe ID
        if (hoatDong.getNguoiLienHe() > 0) {
            CaNhanRepository caNhanRepository = new CaNhanRepository(requireContext());
            CaNhan caNhan = caNhanRepository.getById(hoatDong.getNguoiLienHe());
            if (caNhan != null) {
                String tenCaNhan = "";
                if (caNhan.getHoVaTen() != null && !caNhan.getHoVaTen().isEmpty()) {
                    tenCaNhan = caNhan.getHoVaTen();
                }
                if (caNhan.getTen() != null && !caNhan.getTen().isEmpty()) {
                    if (!tenCaNhan.isEmpty()) {
                        tenCaNhan += " " + caNhan.getTen();
                    } else {
                        tenCaNhan = caNhan.getTen();
                    }
                }
                if (!tenCaNhan.isEmpty()) {
                    tvCaNhan.setText(tenCaNhan);
                } else {
                    tvCaNhan.setText("Không xác định");
                }
            } else {
                tvCaNhan.setText("Không xác định");
            }
        } else {
            tvCaNhan.setText("Không xác định");
        }

        // Cơ hội - query từ OpportunityRepository bằng coHoi ID
        if (hoatDong.getCoHoi() > 0) {
            OpportunityRepository opportunityRepository = OpportunityRepository.getInstance(requireContext());
            Opportunity opportunity = opportunityRepository.getById(hoatDong.getCoHoi());
            if (opportunity != null && opportunity.getTitle() != null) {
                tvCoHoi.setText(opportunity.getTitle());
            } else {
                tvCoHoi.setText("Không xác định");
            }
        } else {
            tvCoHoi.setText("Không xác định");
        }

        // Người phụ trách - query từ NhanVienRepository bằng nhanVien ID
        if (hoatDong.getNhanVien() > 0) {
            NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
            String tenNhanVien = nhanVienRepository.getNameByID(hoatDong.getNhanVien());
            if (tenNhanVien != null && !tenNhanVien.isEmpty()) {
                tvNguoiPhuTrach.setText(tenNhanVien);
            } else {
                tvNguoiPhuTrach.setText("Không xác định");
            }
        } else {
            tvNguoiPhuTrach.setText("Không xác định");
        }

        // Tình trạng
        if (hoatDong.getTinhTrang() != null && !hoatDong.getTinhTrang().isEmpty()) {
            tvTinhTrang.setText(hoatDong.getTinhTrang());
        } else {
            tvTinhTrang.setText("Không xác định");
        }
    }

}

