package com.example.crmmobile.QuoteDirectory;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.QuoteRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class CreateQuoteViewModel extends AndroidViewModel {
    private final QuoteRepository quoteRepository;
    private final MutableLiveData<Boolean> quoteCreatedEvent = new MutableLiveData<>();
    public LiveData<Boolean> getQuoteCreateEvent(){
        return quoteCreatedEvent;
    }
    public MutableLiveData<Integer> QuoteID = new MutableLiveData<>();
    public MutableLiveData<String> QuoteName = new MutableLiveData<>("");
    public MutableLiveData<String> CompanyName = new MutableLiveData<>("");
    public MutableLiveData<String> ContactPersonName = new MutableLiveData<>("");
    public MutableLiveData<Integer> ContactPersonID = new MutableLiveData<>();
    public MutableLiveData<String> OpportunityName = new MutableLiveData<String>("");
    public MutableLiveData<String> State = new MutableLiveData<>("");
    public MutableLiveData<String> Product = new MutableLiveData<>("");
    public MutableLiveData<Integer> Quantity = new MutableLiveData<>();
    public MutableLiveData<Integer> Price = new MutableLiveData<>();
    public MutableLiveData<Long> TotalAmount = new MutableLiveData<>(0L);
    public MutableLiveData<Integer> SendtoID = new MutableLiveData<>();
    public MutableLiveData<String> SendtoName = new MutableLiveData<>();

    public CreateQuoteViewModel(@NonNull Application app){
        super(app);
        quoteRepository = new QuoteRepository(app);
    }

    public void saveQuote(){
        Quote quote = new Quote();
        String today = new SimpleDateFormat(
                "dd/MM/yyyy",
                new Locale("vi", "VN")
        ).format(new Date());

        if (TextUtils.isEmpty(QuoteName.getValue())){
            Toast.makeText(getApplication(), "Vui lòng nhập tên báo giá", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(State.getValue())){
            Toast.makeText(getApplication(), "Vui lòng nhập tình trạng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(CompanyName.getValue())){
            Toast.makeText(getApplication(), "Vui lòng nhập công ty", Toast.LENGTH_SHORT).show();
            return;
        }

        quote.setOrderCode(QuoteName.getValue());
        quote.setState(State.getValue());
        quote.setOpportunityName(OpportunityName.getValue());
        quote.setPrice(Price.getValue());
        Long total = TotalAmount.getValue();
        quote.setTotalAmount(total != null ? total : 0L);
        quote.setCompany(CompanyName.getValue());
        quote.setContactPersonID(ContactPersonID.getValue());
        quote.setDate(today);
        quoteRepository.addQuote(quote);
        quoteCreatedEvent.setValue(true);
    }

    public void clearCreatedEvent(){
        quoteCreatedEvent.setValue(false);
    }

}
