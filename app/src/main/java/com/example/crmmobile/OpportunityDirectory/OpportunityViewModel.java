package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class OpportunityViewModel extends AndroidViewModel {
    private OpportunityRepository repository;
    private MutableLiveData<List<Opportunity>> opportunities = new MutableLiveData<>();

    public OpportunityViewModel(@NonNull Application app) {
        super(app);
        repository = OpportunityRepository.getInstance(app);
//        loadOpportunities();
        loadData();
    }

    public LiveData<List<Opportunity>> getOpportunities() {
        return opportunities;
    }

    public void loadData() {
        List<Opportunity> list = repository.getAll();
        opportunities.setValue(list);
    }

    public void loadOpportunities() {
        // postValue để thread-safe
        opportunities.postValue(repository.getAll());
    }

    public void add(Opportunity opp) {
        repository.add(opp);
//        loadOpportunities();
        loadData();
    }

    public void update(Opportunity opp) {
        repository.update(opp);
//        loadOpportunities();
        loadData();
    }

    public void delete(int id) {
        repository.delete(id);
//        loadOpportunities();
        loadData();
    }

    public Opportunity getById(int id) {
        return repository.getById(id);
    }

    public void reloadData() {
        List<Opportunity> list = repository.getAll();
        opportunities.setValue(list);
    }

    public void search(String keyword) {
        List<Opportunity> result = repository.search(keyword);
        opportunities.setValue(result);
    }


}
