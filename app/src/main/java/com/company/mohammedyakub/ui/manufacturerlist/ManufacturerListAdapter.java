package com.company.mohammedyakub.ui.manufacturerlist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mohammedyakub.R;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.databinding.CardManufacturerItemBinding;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailListActivity;
import com.company.mohammedyakub.utils.AppConstants;


import java.util.ArrayList;

public class ManufacturerListAdapter extends RecyclerView.Adapter<ManufacturerListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    public ArrayList<Manufacturer> manufacturers;

    public ManufacturerListAdapter(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CardManufacturerItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.card_manufacturer_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.binding.setManufacturer(manufacturers.get(position));
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mCode = manufacturers.get(position).getCode();
                Intent intent = ManufacturerDetailListActivity.getStartIntent(v.getContext(), mCode);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return manufacturers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardManufacturerItemBinding binding;

        public ViewHolder(CardManufacturerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void addItems(ArrayList<Manufacturer> manufacturers){
        this.manufacturers.clear();
        this.manufacturers.addAll(manufacturers);
        notifyDataSetChanged();
    }
}
