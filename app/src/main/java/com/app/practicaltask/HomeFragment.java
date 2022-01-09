package com.app.practicaltask;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.practicaltask.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    CricketAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        adapter = new CricketAdapter(getContext());
        binding.recyclerview.setAdapter(adapter);

        adapter.setmCricketList(getData());

        binding.BtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.recyclerview.getVisibility() == View.VISIBLE){
                    binding.recyclerview.setVisibility(View.GONE);
                    binding.BtnCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_right_arrow, 0);
                } else {
                    binding.recyclerview.setVisibility(View.VISIBLE);
                    binding.BtnCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                }
            }
        });

        return binding.getRoot();
    }

    private ArrayList<CricketSubCategory> getData(){
        ArrayList<CricketSubCategory> list = new ArrayList<>();
        list.add(new CricketSubCategory("India", "Sachin \n Sehwag \n Dravid \n Dhoni \n Virat"));
        list.add(new CricketSubCategory("Australia", "Ponting \n Gilcrist \n Hadden \n Wan"));

        return list;
    }
}