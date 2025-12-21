package com.example.crmmobile.HoatDongDirectory;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crmmobile.DataBase.HoatDongRepository;

import java.util.List;

public class ViewModelHoatDong extends ViewModel {
    private final MutableLiveData<List<HoatDong>> hoatdongLiveData = new MutableLiveData<>();
    private HoatDongRepository hoatDongRepository;

    public void init(Context context){
        if (hoatDongRepository == null){
            hoatDongRepository = new HoatDongRepository(context);
        }
    }

    public LiveData<List<HoatDong>> getHoatDongLiveData(){
        return hoatdongLiveData;
    }

    public void loadHoatDongByID(int leadId){
        if (hoatDongRepository == null) return;
        List<HoatDong> list = hoatDongRepository.getHoatDongByNguoiLienHe(leadId);
        hoatdongLiveData.setValue(list);
    }
}
