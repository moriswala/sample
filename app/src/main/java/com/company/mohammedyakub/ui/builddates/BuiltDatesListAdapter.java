package com.company.mohammedyakub.ui.builddates;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mohammedyakub.R;

import com.company.mohammedyakub.data.model.BuiltDate;
import com.company.mohammedyakub.databinding.CardManufacturerBuiltDateItemBinding;

import java.util.ArrayList;

public class BuiltDatesListAdapter extends RecyclerView.Adapter<BuiltDatesListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    public ArrayList<BuiltDate> builtDates;

    public BuiltDatesListAdapter(ArrayList<BuiltDate> builtDates) {
        this.builtDates = builtDates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CardManufacturerBuiltDateItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.card_manufacturer_built_date_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.binding.setBuiltDate(builtDates.get(position));
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return builtDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardManufacturerBuiltDateItemBinding binding;

        public ViewHolder(CardManufacturerBuiltDateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void addItems(ArrayList<BuiltDate> manufacturerItems){
        this.builtDates.clear();
        this.builtDates.addAll(manufacturerItems);
        notifyDataSetChanged();
    }
}
