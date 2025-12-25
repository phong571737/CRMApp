package com.example.crmmobile.MainDirectory;

import android.graphics.Color;
import android.text.TextUtils;

import com.example.crmmobile.R;

public class InitClass {
    public static int getIconNhanVien(int nhanvien){
        int result;
        if(nhanvien == 1){
            result = 0;
        }
        else if (nhanvien == 2){
            result  = 1;
        }
        else if (nhanvien == 3){
            result  = 2;
        }
        else if (nhanvien == 4){
            result  = 3;
        }
        else {
            result = 4;
        }
        return result;
    }

    public static int getIconRecent(String type){
        int result = R.drawable.ic_user;
        switch (type){
            case "LEAD":
                result =  R.drawable.ic_user;
                break;
            case "OPPORTUNITY":
                result = R.drawable.ic_target;
                break;
            case "CANHAN":
                result = R.drawable.ic_individual;
                break;
            case "TOCHUC":
                result = R.drawable.ic_company;
                break;
            case "BAOGIA":
                result = R.drawable.ic_quote;
                break;
            case "DONHANG":
                result = R.drawable.ic_cart;
                break;
        }
        return result;
    }

    public static String getTimeAgo(long timeMillis){
        long now = System.currentTimeMillis();
        long diff = now - timeMillis;

        // dưới 1 phút
        if(diff < 60000) return "Mới truy cập";

        long second = diff / 1000;
        long minute = (second / 60) % 60;
        long hour = (second / (60 * 60)) % 24;
        long day = second / (24 * 60 * 60);

        StringBuilder sb = new StringBuilder();

        if (day > 0){
            sb.append(day).append("d");
            if (hour > 0 ) sb.append(hour).append("h");
            return  sb.append("trước").toString();
        } else if (hour > 0) {
            sb.append(hour).append("h");
            if (minute > 0) sb.append(minute).append("m ");
            return sb.append("trước").toString();
        } else if (minute > 0) {
            return minute + " phút trước";
        }
        else
            return "Mới truy cập";
    }

    public static String getName(String hovaten){
        if (TextUtils.isEmpty(hovaten)) return "";
        String[] parts = hovaten.trim().split("\\s+");

        if (parts.length == 1) return parts[0];
        if (parts.length == 2) return hovaten.trim();

        return parts[parts.length - 2] + " " + parts[parts.length - 1];
    }

    public static int getActivityColor(int index){
        int[] colors = {
                Color.parseColor("#89CFF0"),
                Color.parseColor("#FA8C16"),
                Color.parseColor("#FFEB3B"),
                Color.parseColor("#4CAF50")
        };
        return colors[index % colors.length];
    }
}
