package com.company.mohammedyakub.ui.manufacturerlist;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
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

//public class ManufacturerListAdapter extends RecyclerView.Adapter<ManufacturerListAdapter.ViewHolder> {
public class ManufacturerListAdapter extends PagedListAdapter<Manufacturer, ManufacturerListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;


    public ManufacturerListAdapter() {
        super(DIFF_CALLBACK);
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
        Manufacturer manufacturer = getItem(position);
        viewHolder.binding.setManufacturer(manufacturer);
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mCode = manufacturer.getCode();
                String name = manufacturer.getName();
                Intent intent = ManufacturerDetailListActivity.getStartIntent(v.getContext(),
                        mCode, name);
                v.getContext().startActivity(intent);

            }
        });
    }

//    @Override
//    public int getItemCount() {
//        return manufacturers.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardManufacturerItemBinding binding;

        public ViewHolder(CardManufacturerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
//
//    public void addItems(ArrayList<Manufacturer> manufacturers){
//        this.manufacturers.clear();
//        this.manufacturers.addAll(manufacturers);
//        notifyDataSetChanged();
//    }

    private static DiffUtil.ItemCallback<Manufacturer> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Manufacturer>() {
                @Override
                public boolean areItemsTheSame(Manufacturer oldItem, Manufacturer newItem) {
                    return oldItem.getCode() == newItem.getCode();
                }

                @Override
                public boolean areContentsTheSame(Manufacturer oldItem, Manufacturer newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
