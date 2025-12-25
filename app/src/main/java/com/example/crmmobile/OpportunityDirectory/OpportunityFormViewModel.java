package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class OpportunityFormViewModel extends AndroidViewModel {

    private CaNhanRepository caNhanRepo;
    private CompanyRepository companyRepo;
    private NhanVienRepository nhanVienRepo;
    private OpportunityRepository oppRepo;

    private OpportunityFormHandler handler = new OpportunityFormHandler();

    private final MutableLiveData<List<ToChuc>> companies = new MutableLiveData<>();
    private final MutableLiveData<List<CaNhan>> contacts = new MutableLiveData<>();
    private final MutableLiveData<List<Nhanvien>> employees = new MutableLiveData<>();

    private MutableLiveData<Opportunity> editing = new MutableLiveData<>();


    private int selectedCompanyId = 0;
    private int selectedContactId = 0;
    private int selectedManagementId = 0;

    public OpportunityFormViewModel(@NonNull Application app) {
        super(app);

        companyRepo  = new CompanyRepository(app);
        caNhanRepo = new CaNhanRepository(app);
        nhanVienRepo = new NhanVienRepository(app);

//        app crash khi open edit form vi quen khoi tao repository
        oppRepo      = new OpportunityRepository(app);

        loadDropdownData();
    }

    private void loadDropdownData() {
        companies.setValue(companyRepo.getAllIdName());
        contacts.setValue(caNhanRepo.getAllIdName());
        employees.setValue(nhanVienRepo.getAllIdName());
    }

    public LiveData<List<ToChuc>> getCompanies() { return companies; }
    public LiveData<List<CaNhan>> getContacts() { return contacts; }

    public LiveData<List<Nhanvien>> getEmployees() { return employees; }
    public LiveData<Opportunity> getEditingOpportunity() {
        return editing;
    }

    public void setSelectedCompanyId(int id) { selectedCompanyId = id; }
    public void setSelectedContactId(int id) { selectedContactId = id; }
    public void setSelectedManagementId(int id) { selectedManagementId = id; }

    public int getSelectedCompanyId() { return selectedCompanyId; }
    public int getSelectedContactId() { return selectedContactId; }
    public int getSelectedManagementId() { return selectedManagementId; }


    // Business: tạo Opportunity từ form
    public Opportunity createOpportunity(
            String mode,
            Opportunity existing,
            String title,
            String priceStr,
            String stage,
            String date1,
            String date2,
            String desc
    ) throws ParseException {
        return handler.createFromForm(
                mode,
                existing,
                title,
                priceStr,
                stage,
                date1,
                date2,
                desc,
                selectedCompanyId,
                selectedContactId,
                selectedManagementId
        );
    }

    public void saveOpportunity(String mode, Opportunity o) {
        if ("create".equals(mode)) {
            oppRepo.add(o);
        } else if ("update".equals(mode)) {
            oppRepo.update(o);
        }
    }


    public OpportunityFormHandler getHandler() {
        return handler;
    }

    public void loadOpportunityById(int id) {
        // giả sử repo có hàm getById()
        Opportunity o = oppRepo.getById(id);
        editing.setValue(o);
    }
}
