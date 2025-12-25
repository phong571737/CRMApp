package com.example.crmmobile.LeadDirectory;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class lead_information extends Fragment {
    private ViewModelLead viewModelLead;
    private Lead lead;
    private TextInputLayout birthday_layout,day_contact_layout;
    private TextInputEditText edt_birthday, edt_family_name,
            edt_first_name, edt_phone_number, edt_email, edt_contact_day;
    private MaterialAutoCompleteTextView edtTitle, edt_sex, edt_state,edt_created_by;

    public interface StringUpdater{
        void update(String s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_information,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);
        initVariables(view);

        bindViewModeltoEditext(viewModelLead.title, edtTitle);
        bindViewModeltoEditext(viewModelLead.hovatendem, edt_family_name);
        bindViewModeltoEditext(viewModelLead.first_name, edt_first_name);
        bindViewModeltoEditext(viewModelLead.phonenumber, edt_phone_number);
        bindViewModeltoEditext(viewModelLead.Email, edt_email);
        bindViewModeltoEditext(viewModelLead.Sex, edt_sex);
        bindViewModeltoEditext(viewModelLead.Birthday, edt_birthday);
        bindViewModeltoEditext(viewModelLead.state, edt_state);
        bindViewModeltoEditext(viewModelLead.contact_day, edt_contact_day);
        bindViewModeltoEditext(viewModelLead.CreatedByName, edt_created_by);

        //lưu giá trị mỗi khi user nhập
        bindEditTexttoViewModel(edtTitle, s -> viewModelLead.title.setValue(s));
        bindEditTexttoViewModel(edt_family_name, s -> viewModelLead.hovatendem.setValue(s));
        bindEditTexttoViewModel(edt_first_name, s -> viewModelLead.first_name.setValue(s));
        bindEditTexttoViewModel(edt_phone_number, s->viewModelLead.phonenumber.setValue(s));
        bindEditTexttoViewModel(edt_email, s -> viewModelLead.Email.setValue(s));
        bindEditTexttoViewModel(edt_sex, s -> viewModelLead.Sex.setValue(s));
        bindEditTexttoViewModel(edt_birthday, s -> viewModelLead.Birthday.setValue(s));
        bindEditTexttoViewModel(edt_state, s-> viewModelLead.state.setValue(s));
        bindEditTexttoViewModel(edt_contact_day, s -> viewModelLead.contact_day.setValue(s));
        bindEditTexttoViewModel(edt_created_by, s -> viewModelLead.CreatedByName.setValue(s));
    }

    private void bindViewModeltoEditext(MutableLiveData<String> title, EditText editText) {
        title.observe(getViewLifecycleOwner(), v->{
            String value = (v == null) ? "" : v;
            String curr = editText.getText() != null ? editText.getText().toString() : "";
            if (!value.equals(curr)){
                if(editText instanceof MaterialAutoCompleteTextView){
                    ((MaterialAutoCompleteTextView)editText).setText(v,false);
                }else{
                    if (!editText.hasFocus()){
                        editText.setText(v);
                    }
                }
            }
        });
    }

    private void initVariables(View view) {
        edtTitle = view.findViewById(R.id.edt_title);
        String[] title = {"Ông", "Bà", "Anh", "Chị"};
        ArrayAdapter<String> AdapterTitle = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, title);
        edtTitle.setAdapter(AdapterTitle);

        edt_family_name = view.findViewById(R.id.edt_family_name);
        edt_first_name = view.findViewById(R.id.edt_first_name);
        edt_phone_number = view.findViewById(R.id.edt_phone_number);
        edt_email = view.findViewById(R.id.edt_email);
        edt_sex = view.findViewById(R.id.edt_sex);
        String[] gender = {"Nam", "Nữ"};
        ArrayAdapter<String> AdapterGender = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, gender);
        edt_sex.setAdapter(AdapterGender);

        edt_birthday = view.findViewById(R.id.edt_birthday);
        birthday_layout = view.findViewById(R.id.birthday_layout);
        //show calendar
        birthday_layout.setEndIconOnClickListener(v -> {
            showBirthDayPicker(edt_birthday);
        });
        edt_state = view.findViewById(R.id.edt_state);

        String[] states = {"Mới", "Chưa liên hệ được", "Liên hệ sau", "Đã liên hệ", "Ngừng chăm sóc", "Đã chuyển đổi", ""};
        ArrayAdapter<String> AdapterStates = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, states);
        edt_state.setAdapter(AdapterStates);

        edt_contact_day = view.findViewById(R.id.edt_contact_day);
        day_contact_layout = view.findViewById(R.id.day_contact_layout);
        day_contact_layout.setEndIconOnClickListener(v -> {
            showDatePicker(edt_contact_day);
        });
        edt_created_by = view.findViewById(R.id.edt_created_by);
        initCreateBy();
    }

    private void initCreateBy() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
            nhanVienRepository.AddNhanVien();
            List<Nhanvien> Employees_list = nhanVienRepository.getAllNhanVien();
            mainHandler.post(()->{
                ArrayAdapter<Nhanvien> AdapterEmployer =
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                Employees_list);
                edt_created_by.setAdapter(AdapterEmployer);
                edt_created_by.setOnItemClickListener(((parent, view1, position, id) -> {
                    Nhanvien nv = (Nhanvien) parent.getItemAtPosition(position);
                    viewModelLead.CreatedByID.setValue(nv.getId());
                    viewModelLead.CreatedByName.setValue(nv.getHoten());
                }));
            });
        });
    }

    private void showDatePicker(TextInputEditText edtBirthday) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay)->{
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtBirthday.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showBirthDayPicker(TextInputEditText edtBirthday) {
        final Calendar calendar = Calendar.getInstance();

        int year = 2004;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay)->{
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtBirthday.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void bindEditTexttoViewModel(EditText editText, StringUpdater updater) {
        editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updater.update(s.toString());
                }
            }
        );
    }
}
