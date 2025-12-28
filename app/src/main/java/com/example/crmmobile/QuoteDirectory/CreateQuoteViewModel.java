package com.example.crmmobile.QuoteDirectory;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.DataBase.QuoteRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateQuoteViewModel extends AndroidViewModel {
    private static final String TAG = "QUOTE_VIEWMODEL";
    private final QuoteRepository quoteRepository;
    private final MutableLiveData<Boolean> quoteCreatedEvent = new MutableLiveData<>();
    public LiveData<Boolean> getQuoteCreateEvent(){
        return quoteCreatedEvent;
    }
    public MutableLiveData<Integer> quoteID = new MutableLiveData<>();
    public MutableLiveData<String> QuoteName = new MutableLiveData<>("");
    public MutableLiveData<String> CompanyName = new MutableLiveData<>("");
    public MutableLiveData<Integer> companyID = new MutableLiveData<>();
    public MutableLiveData<String> ContactPersonName = new MutableLiveData<>("");
    public MutableLiveData<Integer> ContactPersonID = new MutableLiveData<>();
    public MutableLiveData<String> OpportunityName = new MutableLiveData<String>("");
    public MutableLiveData<Integer> OpportunityID = new MutableLiveData<>();
    public MutableLiveData<String> State = new MutableLiveData<>("");
    public MutableLiveData<String> Product = new MutableLiveData<>("");
    public MutableLiveData<Integer> Quantity = new MutableLiveData<>();
    public MutableLiveData<Integer> Price = new MutableLiveData<>();
    public MutableLiveData<Long> TotalAmount = new MutableLiveData<>(0L);
    public MutableLiveData<Integer> SendtoID = new MutableLiveData<>();
    public MutableLiveData<String> SendtoName = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> address_Ship = new MutableLiveData<>();
    public MutableLiveData<String> district_ship = new MutableLiveData<>();
    public MutableLiveData<String> province_ship = new MutableLiveData<>();
    public MutableLiveData<String> nation_ship = new MutableLiveData<>();

    public MutableLiveData<String> quoteNameError = new MutableLiveData<>();
    public MutableLiveData<String> stateError = new MutableLiveData<>();
    public MutableLiveData<String> companyError = new MutableLiveData<>();
    public MutableLiveData<String> addressError = new MutableLiveData<>();

    public CreateQuoteViewModel(@NonNull Application app){
        super(app);
        quoteRepository = new QuoteRepository(app);
    }

    public void saveQuote(){
        Quote quote = new Quote();
        quoteNameError.setValue(null);
        stateError.setValue(null);
        companyError.setValue(null);
        addressError.setValue(null);
        boolean isValid = true;

        if (TextUtils.isEmpty(QuoteName.getValue())){
            quoteNameError.setValue("Vui lòng nhập tên báo giá");
            isValid = false;
        }
        if (TextUtils.isEmpty(State.getValue())){
            stateError.setValue("Vui lòng nhập tình trạng");
            isValid = false;
        }
        if (TextUtils.isEmpty(CompanyName.getValue())){
            companyError.setValue("Vui lòng nhập công ty");
            isValid = false;
        }
        if (TextUtils.isEmpty(address_Ship.getValue())){
            addressError.setValue("Vui lòng nhập địa chỉ");
            isValid = false;
        }
        if (!isValid) return;

        String today = new SimpleDateFormat(
                "dd/MM/yyyy",
                new Locale("vi", "VN")
        ).format(new Date());

        quote.setOrderCode(QuoteName.getValue());
        quote.setState(State.getValue());
        quote.setOpportunityName(OpportunityName.getValue());
        quote.setOpportunityID(OpportunityID.getValue());
        quote.setPrice(Price.getValue());
        quote.setDescription(description.getValue());
        quote.setAddress_Ship(address_Ship.getValue());
        quote.setDistrict_Ship(district_ship.getValue());
        quote.setProvince_Ship(province_ship.getValue());
        quote.setNation_Ship(nation_ship.getValue());
        Long total = TotalAmount.getValue();
        quote.setTotalAmount(total != null ? total : 0L);
        quote.setCompany(CompanyName.getValue());
        quote.setCompanyID(companyID.getValue());
        quote.setContactPersonID(ContactPersonID.getValue());
        quote.setDate(today);
        Log.e(TAG, "Cơ hội ID: " + quote.getOpportunityID());
        Log.e(TAG, "Người lien hệ ID: " + quote.getContactPersonID());
        quoteRepository.addQuote(quote);
        quoteCreatedEvent.setValue(true);
    }

    public void clearCreatedEvent(){
        quoteCreatedEvent.setValue(false);
    }

}
