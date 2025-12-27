package com.example.crmmobile.QuoteDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crmmobile.DataBase.QuoteRepository;

public class CreateQuoteViewModel extends AndroidViewModel {
    private final QuoteRepository quoteRepository;
    private final MutableLiveData<Boolean> quoteCreatedEvent = new MutableLiveData<>();
    public LiveData<Boolean> getQuoteCreateEvent(){
        return quoteCreatedEvent;
    }
    public MutableLiveData<Integer> QuoteID = new MutableLiveData<>();
    public MutableLiveData<String> QuoteName = new MutableLiveData<>("");
    public MutableLiveData<String> CompanyName = new MutableLiveData<>("");
    public MutableLiveData<String> ContactPerson = new MutableLiveData<>("");
    public MutableLiveData<String> OpportunityName = new MutableLiveData<String>("");
    public MutableLiveData<String> State = new MutableLiveData<>("");
    public MutableLiveData<String> Product = new MutableLiveData<>("");
    public MutableLiveData<Integer> Quantity = new MutableLiveData<>();
    public MutableLiveData<Integer> Price = new MutableLiveData<>();
    public MutableLiveData<Double> TotalAmount = new MutableLiveData<>();
    public MutableLiveData<Integer> SendtoID = new MutableLiveData<>();
    public MutableLiveData<String> SendtoName = new MutableLiveData<>();

    public CreateQuoteViewModel(@NonNull Application app){
        super(app);
        quoteRepository = new QuoteRepository(app);
    }

    public void saveQuote(){
        Quote quote = new Quote();

        quote.setOrderCode(QuoteName.getValue());
        quote.setState(State.getValue());
        quote.setOpportunityName(OpportunityName.getValue());
        quote.setPrice(Price.getValue());
        quote.setCompany(CompanyName.getValue());
        quoteRepository.addQuote(quote);
        quoteCreatedEvent.setValue(true);
    }

    public void clearCreatedEvent(){
        quoteCreatedEvent.setValue(false);
    }

}
