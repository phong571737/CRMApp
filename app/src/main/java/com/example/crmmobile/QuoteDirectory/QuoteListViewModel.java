package com.example.crmmobile.QuoteDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crmmobile.DataBase.QuoteRepository;

import java.util.List;

public class QuoteListViewModel extends AndroidViewModel {
    private final QuoteRepository quoteRepository;
    private final MutableLiveData<List<Quote>> quotes = new MutableLiveData<>();

    public QuoteListViewModel(@NonNull Application application) {
        super(application);
        this.quoteRepository = new QuoteRepository(application);
    }

    private void loadQuotes() {
        quotes.setValue(quoteRepository.getAllQuote());
    }

    public LiveData<List<Quote>> getQuotes(){
        return quotes;
    }
    public void refresh(){
        loadQuotes();
    }
    public void deleteQuote(int quoteID){ //delete quote
        quoteRepository.DeleteQuote(quoteID);
        loadQuotes();
    }
}
