package com.example.crmmobile.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.QuoteDirectory.Quote;
import com.example.crmmobile.R;

import java.util.List;

public class AdapterQuote extends RecyclerView.Adapter<AdapterQuote.quoteViewHolder> {
    private static final String TAG = "ADAPTER_QUOTE";
    private  List<Quote> listquote;
    private final onItemClickListener listener;

    public interface onItemClickListener{
        void onDotsListener(Quote quote, int position);
        void onMenuListener(Quote quote);
    }

    public AdapterQuote(Context context, List<Quote> listquote, onItemClickListener listener){
        this.listquote = listquote;
        this.listener = listener;
    }
    public static class quoteViewHolder extends RecyclerView.ViewHolder{

        TextView tv_code, tv_company,tv_date,tv_money;
        ImageView iv_back, iv_dots;
        public quoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_code = itemView.findViewById(R.id.quote_code);
            tv_company = itemView.findViewById(R.id.tv_Company);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_back = itemView.findViewById(R.id.iv_back);
            iv_dots = itemView.findViewById(R.id.iv_dots);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }

    @NonNull
    @Override
    public quoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);

        return new quoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterQuote.quoteViewHolder viewHolder, final int position){
        Quote quote = listquote.get(position);

        viewHolder.tv_code.setText(quote.getOrderCode());
        viewHolder.tv_company.setText(quote.getCompany());
        viewHolder.tv_date.setText(quote.getDate());
        viewHolder.tv_money.setText((quote.getPrice() == null) ? "0 đ" : String.valueOf(quote.getPrice() + " đ"));
        Log.e(TAG, "Company name: " + quote.getCompany());

        viewHolder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onMenuListener(quote);
            }
        });
        viewHolder.iv_dots.setOnClickListener(v -> {
            if(listener != null){
                listener.onDotsListener(quote, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listquote.size();
    }

    public void setData(List<Quote> quotes){
        this.listquote.clear();
        this.listquote.addAll(quotes);
        notifyDataSetChanged(); //reload
    }
}
