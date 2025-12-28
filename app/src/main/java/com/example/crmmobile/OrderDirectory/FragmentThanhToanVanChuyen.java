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

import org.json.JSONObject;

public class FragmentThanhToanVanChuyen extends Fragment {

    private TextView tvPaymentMethod, tvPaymentStatus;

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

        DonHang dh = null;
        if (getActivity() instanceof OrderDetailActivity) {
            dh = ((OrderDetailActivity) getActivity()).getCurrentDonHang(); // bạn cần có getter này
        }

        String method = "Thanh toán trực tiếp";
        String status = "Chưa thanh toán";

        try {
            if (dh != null) {
                String extraStr = dh.getExtraJson();
                JSONObject extra = (extraStr == null || extraStr.trim().isEmpty())
                        ? new JSONObject()
                        : new JSONObject(extraStr);

                method = extra.optString("paymentMethod", method);
                status = extra.optString("paymentStatus", status);
            }
        } catch (Exception ignored) {}

        tvPaymentMethod.setText(method);
        tvPaymentStatus.setText(status);
    }
}
