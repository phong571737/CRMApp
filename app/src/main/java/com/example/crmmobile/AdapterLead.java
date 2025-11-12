package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterLead extends RecyclerView.Adapter<AdapterLead.LeadViewHolder> {
    private List<Lead> dataList;
    private onItemDotsClickListener dotsClickListener;
    private  onMenuClickListener menuClickListener;

    public interface onItemDotsClickListener {
        void onDotsClick(Lead item, int position);
    }

    public interface onMenuClickListener{
        void onMenuClick(Lead lead);
    }

    public AdapterLead(List<Lead> dataList, onItemDotsClickListener dotsClickListener, onMenuClickListener menuClickListener){
        this.dataList = dataList;
        this.dotsClickListener = dotsClickListener;
        this.menuClickListener = menuClickListener;
    }

    public static class LeadViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_Company, tv_day;
        ImageView ivDots;

        public LeadViewHolder(View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_Company = itemView.findViewById(R.id.tv_Company);
            tv_day = itemView.findViewById(R.id.tv_Day);
            ivDots = itemView.findViewById(R.id.iv_dots);
        }

    }

    //Crete new view
    @Override
    public LeadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lead, viewGroup, false);
        return new LeadViewHolder(view);
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(LeadViewHolder viewHolder, final int position){
        Lead lead = dataList.get(position);
        viewHolder.tv_name.setText(lead.getName());
        viewHolder.tv_Company.setText(lead.getCompany());
        viewHolder.tv_day.setText(lead.getDaycontact());

        viewHolder.ivDots.setOnClickListener(v -> {
            if(dotsClickListener != null){
                dotsClickListener.onDotsClick(lead, position);
            }
        });

        viewHolder.itemView.setOnClickListener(v -> {
            if(menuClickListener != null){
                menuClickListener.onMenuClick(lead);
            }
        });
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}
