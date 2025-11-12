package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterQuote extends RecyclerView.Adapter<AdapterQuote.quoteViewHolder> {

    private List<Quote> listquote;

    private onClickBackListener backListener;
    private onClickMenuListener onClickMenuListener;

    public interface onClickBackListener{
        void onBackListener(Quote quote);
    }

    public interface onClickMenuListener{
        void onMenuListener(Quote quote);
    }

    public AdapterQuote(List<Quote> listquote, onClickMenuListener onMenulistener){
        this.listquote = listquote;
        this.onClickMenuListener = onMenulistener;
    }
    public static class quoteViewHolder extends RecyclerView.ViewHolder{

        TextView tv_code, tv_company,tv_date;
        ImageView iv_back;
        public quoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_code = itemView.findViewById(R.id.quote_code);
            tv_company = itemView.findViewById(R.id.tv_Company);
            tv_date = itemView.findViewById(R.id.tv_job);
            iv_back = itemView.findViewById(R.id.iv_back);
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

        viewHolder.tv_code.setText(quote.getCode());
        viewHolder.tv_company.setText(quote.getCompany());
        viewHolder.tv_date.setText(quote.getDate());

        viewHolder.itemView.setOnClickListener(v -> {
            if(onClickMenuListener != null){
                onClickMenuListener.onMenuListener(quote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listquote.size();
    }
}
