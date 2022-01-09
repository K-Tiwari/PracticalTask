package com.app.practicaltask;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.practicaltask.databinding.ItemCricketBinding;

import java.util.ArrayList;

public class CricketAdapter extends RecyclerView.Adapter<CricketAdapter.MyViewHolder> {
    Context context;
    private ArrayList<CricketSubCategory> mCricketList;

    public CricketAdapter(Context context) {
        this.context = context;
    }

    public void setmCricketList(ArrayList<CricketSubCategory> mList) {
        this.mCricketList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCricketBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cricket, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CricketSubCategory data = mCricketList.get(position);
        holder.binding.tvTitle.setText(data.getTitle());
        holder.binding.tvContent.setText(data.getContent());

        holder.binding.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.foldingCell.toggle(false);
            }
        });

        holder.binding.foldingCell.initialize(30, 1000, Color.DKGRAY, 1);
    }

    @Override
    public int getItemCount() {
        if (mCricketList == null){
            return 0;
        } else {
            return mCricketList.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCricketBinding binding;

        public MyViewHolder(@NonNull ItemCricketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
