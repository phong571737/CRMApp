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

    private onClickDotsListener dotsListener;
    private onClickMenuListener onClickMenuListener;

    public interface onClickDotsListener{
        void onDotsListener(Quote quote, int position);
    }

    public interface onClickMenuListener{
        void onMenuListener(Quote quote);
    }

    public AdapterQuote(List<Quote> listquote,onClickDotsListener dotsListener, onClickMenuListener onMenulistener){
        this.listquote = listquote;
        this.dotsListener = dotsListener;
        this.onClickMenuListener = onMenulistener;
    }
    public static class quoteViewHolder extends RecyclerView.ViewHolder{

        TextView tv_code, tv_company,tv_date;
        ImageView iv_back, iv_dots;
        public quoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_code = itemView.findViewById(R.id.quote_code);
            tv_company = itemView.findViewById(R.id.tv_Company);
            tv_date = itemView.findViewById(R.id.tv_job);
            iv_back = itemView.findViewById(R.id.iv_back);
            iv_dots = itemView.findViewById(R.id.iv_dots);
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
        viewHolder.iv_dots.setOnClickListener(v -> {
            if(dotsListener != null){
                dotsListener.onDotsListener(quote, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listquote.size();
    }
}
