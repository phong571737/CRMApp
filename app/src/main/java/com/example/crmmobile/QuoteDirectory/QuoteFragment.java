package com.example.crmmobile.QuoteDirectory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.crmmobile.Adapter.AdapterQuote;
import com.example.crmmobile.BottomSheet.BottomSheetActionQuote;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.DataBase.QuoteRepository;
import com.example.crmmobile.OrderDirectory.DonHang;
import com.example.crmmobile.OrderDirectory.Order;
import com.example.crmmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuoteFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterQuote adapterQuote;
    private List<Quote> listquote;
    private QuoteRepository quoteRepository;
    private CreateQuoteViewModel createQuoteViewModel;
    private QuoteListViewModel quoteListViewModel;
    private ImageView iv_back;

    FloatingActionButton btnaddQuote;

    public QuoteFragment() {

    }

    public static QuoteFragment newInstance(String param1, String param2) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_quote, container, false);
        quoteListViewModel = new ViewModelProvider(requireActivity()).get(QuoteListViewModel.class);
        createQuoteViewModel = new ViewModelProvider(requireActivity()).get(CreateQuoteViewModel.class);
        quoteRepository = new QuoteRepository(requireContext());
        initViews(view);

        setupRecyclerView();
        setupViewModel();
        setupActions();

        quoteListViewModel.refresh();
        createQuoteViewModel.getQuoteCreateEvent().observe(
                getViewLifecycleOwner(),
                created->{
                    if (Boolean.TRUE.equals(created)){
                        quoteListViewModel.refresh();
                        createQuoteViewModel.clearCreatedEvent();
                    }
                }
        );

        return view;
    }

    private void setupViewModel() {
        quoteListViewModel.getQuotes().observe(
                getViewLifecycleOwner(), quotes -> {
                    adapterQuote.setData(quotes);
                }
        );
    }

    private void setupRecyclerView() {

        adapterQuote = new AdapterQuote(requireContext(),
                new ArrayList<>(),
                new AdapterQuote.onItemClickListener() {
            @Override
            public void onDotsListener(Quote quote, int position) {
                BottomSheetActionQuote.ShowBottomSheetQuote(getContext(), quote, position, new BottomSheetActionQuote.OnActionListenerQuote() {
                    @Override
                    public void onDeleteQuote(Quote quote) {
                        quoteListViewModel.deleteQuote(quote.getID());
                    }

                    @Override
                    public void onConvertOrder(Quote quote) {
                        DonHang donHang = new DonHang();

                        int id = quote.getID();
                        String code = String.format("BG-%04d", id);
                        donHang.setTenDonHang(quote.getTitle());
                        donHang.setNgayNhanHang(quote.getDate());
                        donHang.setNguoiLienHeId(quote.getContactPersonID());
                        donHang.setDonGia(quote.getPrice());
                        donHang.setSoLuong(quote.getQuantity());
                        donHang.setTongTien(quote.getTotalAmount());
//                        donHang.setCongTyId(qu);

                        DonHangRepository donHangRepository = new DonHangRepository(requireContext());
                        long id = donHangRepository.insert(donHang);
                        if (id > 0){
                            quoteRepository.updateQuote(quote);
                            Toast.makeText(getContext(), "Chuyển đổi thành công đơn hàng", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Chuyển đổi thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onMenuListener(Quote quote, int id) {
                Intent intent = new Intent(getContext(), QuoteDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapterQuote);
    }

    private void setupActions() {
        //tạo mới báo giá
        btnaddQuote.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaoBaoGiaActivity.class);
            startActivity(intent);
        });
        iv_back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.QuoteRecycler);
        btnaddQuote = view.findViewById(R.id.btn_add_quote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iv_back = view.findViewById(R.id.iv_back);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (quoteListViewModel != null){
            quoteListViewModel.refresh();
        }
    }
}