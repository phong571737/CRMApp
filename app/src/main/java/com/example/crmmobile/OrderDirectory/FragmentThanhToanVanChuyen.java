package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.DonHangRepository;

public class FragmentThanhToanVanChuyen extends Fragment {

    private TextView tvPaymentMethod, tvPaymentStatus;
    private DonHangRepository donHangRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_toan_van_chuyen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);
        tvPaymentStatus = view.findViewById(R.id.tvPaymentStatus);

        donHangRepo = new DonHangRepository(requireContext());

        getParentFragmentManager().setFragmentResultListener(
                "PAYMENT_UPDATED",
                this,
                (key, bundle) -> {
                    String status = bundle.getString("paymentStatus", "Chưa thanh toán");
                    String method = bundle.getString("paymentMethod", "Thanh toán trực tiếp");

                    tvPaymentStatus.setText(status);
                    tvPaymentMethod.setText(method);
                }
        );


        render();
    }

    @Override
    public void onResume() {
        super.onResume();
        render();
    }

    private void render() {
        int orderId = -1;
        if (getActivity() != null) {
            orderId = getActivity().getIntent().getIntExtra(OrderDetailActivity.EXTRA_ORDER_ID, -1);
        }

        String method = "Thanh toán trực tiếp";
        String status = "Chưa thanh toán";

        if (orderId > 0) {
            String m = donHangRepo.getPaymentMethod(orderId);
            String s = donHangRepo.getPaymentStatus(orderId);

            if (m != null && !m.trim().isEmpty()) method = m;
            if (s != null && !s.trim().isEmpty()) status = s;
        }

        if (tvPaymentMethod != null) tvPaymentMethod.setText(method);
        if (tvPaymentStatus != null) tvPaymentStatus.setText(status);
    }
}
