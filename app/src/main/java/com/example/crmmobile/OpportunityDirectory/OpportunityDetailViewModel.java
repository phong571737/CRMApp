package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

public class OpportunityDetailViewModel extends AndroidViewModel {

//    private OpportunityRepository oppRepo;
//    private MutableLiveData<Opportunity> editing = new MutableLiveData<>();
//    public OpportunityDetailViewModel(@NonNull Application app) {
//        super(app);
//        oppRepo = OpportunityRepository.getInstance(app);
//    }
//    public void loadOpportunityById(int id) {
//        Log.d("DETAIL_VM", "loadOpportunityById(" + id + ")");
//        Opportunity o = oppRepo.getById(id);
//        Opportunity copy = new Opportunity(
//                o.getId(),
//                o.getTitle(),
//                o.getCompany(),
//                o.getContact(),
//                o.getPrice(),
//                o.getStatus(),
//                o.getDate(),
//                o.getExpectedDate2(),
//                o.getDescription(),
//                o.getManagement(),
//                o.getCallCount(),
//                o.getMessageCount()
//        );
//
//        Log.d("DETAIL_VM",
//                "emit status=" + copy.getStatus() +
//                        " hash=" + System.identityHashCode(copy)
//        );
//
//        editing.setValue(copy);
//    }
//    public LiveData<Opportunity> getOpportunity() {
//        return editing;
//    }

//    sau khi cho contact link opportunity

    private OpportunityRepository oppRepo;
    private CompanyRepository companyRepo;
    private CaNhanRepository caNhanRepo;
    private NhanVienRepository employeeRepo;

    private final MediatorLiveData<OpportunityDetailUI> ui = new MediatorLiveData<>();
    private final MutableLiveData<Opportunity> opportunityLD = new MutableLiveData<>();
    private Opportunity current;

    public OpportunityDetailViewModel(@NonNull Application app) {
        super(app);
        oppRepo = OpportunityRepository.getInstance(app);
        companyRepo = new CompanyRepository(null);
        caNhanRepo = new CaNhanRepository(app);
        employeeRepo = new NhanVienRepository(null);
    }

    public void loadOpportunityById(int id) {
        Opportunity o = oppRepo.getById(id);
        current = o;

        opportunityLD.setValue(o); // PIPELINE NHẬN OBJECT

        tryEmit(); // emit UI
    }


    private void tryEmit() {
        if (current == null) return;

        String companyName = findCompanyName(current.getCompany());
        String contactName = findContactName(current.getContact());
        String managerName = findEmployeeName(current.getManagement());

        OpportunityDetailUI uiModel = new OpportunityDetailUI(
                current.getId(),
                current.getTitle(),
                current.getCompany(), companyName,
                current.getContact(), contactName,
                current.getManagement(), managerName,
                current.getPrice(),
                current.getStatus(),
                current.getDate(),
                current.getExpectedDate2(),
                current.getDescription()
        );

        ui.setValue(uiModel);
    }

    public LiveData<Opportunity> getOpportunity() {
        return opportunityLD;
    }

    public LiveData<OpportunityDetailUI> getUI() {
        return ui;
    }

    // ===== mapping helpers =====

    private String findCompanyName(int id) {
        for (ToChuc c : companyRepo.getAllIdName())
            if (c.getId() == id) return c.getCompanyName();
        return "";
    }

    private String findContactName(int id) {
        for (CaNhan c : caNhanRepo.getAllIdName())
            if (c.getId() == id) return c.getHoVaTen();
        return "";
    }

    private String findEmployeeName(int id) {
        for (Nhanvien e : employeeRepo.getAllIdName())
            if (e.getId() == id) return e.getHoten();
        return "";
    }


}
