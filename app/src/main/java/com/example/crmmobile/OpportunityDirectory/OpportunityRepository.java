package com.example.crmmobile.OpportunityDirectory;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class OpportunityRepository {
    private static OpportunityRepository instance;
    private OpportunityDAO dao;

    OpportunityRepository(Context context) {
        dao = new OpportunityDAO(context.getApplicationContext());
    }

    public static synchronized OpportunityRepository getInstance(Context context) {
        if (instance == null) {
            instance = new OpportunityRepository(context);
        }
        return instance;
    }

    public List<Opportunity> getAll() {
        return dao.getAll();
    }

    public Opportunity getById(int id) {

//        return dao.getById(id);

        Opportunity o = dao.getById(id);
        Log.d("REPO_DEBUG",
                "getById() id=" + id +
                        " status=" + o.getStatus() +
                        " hash=" + System.identityHashCode(o)
        );
        return o;
    }
    public long add(Opportunity opportunity) {
        long now = System.currentTimeMillis();

        opportunity.setCreatedAt(now);
        opportunity.setUpdatedAt(now);

        return dao.add(opportunity);
    }
    public void update(Opportunity opportunity) {
        Log.d("BUG_TEST", "REPO.update()");
        Log.d("BUG_TEST", "id = " + opportunity.getId());
        Log.d("BUG_TEST", "company = " + opportunity.getCompany());
        Log.d("BUG_TEST", "contact = " + opportunity.getContact());
        Log.d("BUG_TEST", "management = " + opportunity.getManagement());

        long now = System.currentTimeMillis();
        opportunity.setUpdatedAt(now);

        dao.update(opportunity);
    }
    public void delete(int id) {
        dao.delete(id);
    }

    public List<Opportunity> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.getAll();   // search rỗng → trả full list
        }
        return dao.searchByKeyword(keyword);
    }


    public interface Callback { void onComplete(boolean success); }

    public void updateStage(Opportunity opportunity, String newStage, String note, Callback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Opportunity updated = new Opportunity(
                    opportunity.getId(),
                    opportunity.getTitle(),
                    opportunity.getCompany(),
                    opportunity.getContact(),
                    opportunity.getPrice(),
                    newStage,
                    opportunity.getDate(),
                    opportunity.getExpectedDate2(),
                    opportunity.getDescription(),
                    opportunity.getManagement(),
                    opportunity.getCallCount(),
                    opportunity.getMessageCount()
            );

            dao.update(updated);   // ghi xuống DB
            callback.onComplete(true);
        }, 1000);
    }

}



