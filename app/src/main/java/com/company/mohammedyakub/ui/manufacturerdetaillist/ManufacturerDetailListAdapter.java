package com.company.mohammedyakub.ui.manufacturerdetaillist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mohammedyakub.R;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;
import com.company.mohammedyakub.databinding.CardManufacturerDetailItemBinding;
import com.company.mohammedyakub.ui.builddates.BuiltDatesListActivity;
import com.company.mohammedyakub.ui.manufacturerlist.ManufacturerListActivity;


import java.util.ArrayList;

public class ManufacturerDetailListAdapter extends RecyclerView.Adapter<ManufacturerDetailListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    public ArrayList<ManufacturerItems> manufacturerItems;

    public ManufacturerDetailListAdapter(ArrayList<ManufacturerItems> manufacturerItems) {
        this.manufacturerItems = manufacturerItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CardManufacturerDetailItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.card_manufacturer_detail_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.binding.setManufacturerItem(manufacturerItems.get(position));
        viewHolder.binding.setPosition(position);
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manufacturerCode = manufacturerItems.get(position).getManufacturerCode();
                String typeCode = manufacturerItems.get(position).getCode();
                String typeName = manufacturerItems.get(position).getName();
                Intent intent = BuiltDatesListActivity.getStartIntent(v.getContext(),
                        manufacturerCode, typeCode, typeName);
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((ManufacturerDetailListActivity)
                            v.getContext());
                    v.getContext().startActivity(intent, options.toBundle());

                } else {
                    // Swap without transition
                    v.getContext().startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return manufacturerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardManufacturerDetailItemBinding binding;

        public ViewHolder(CardManufacturerDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void addItems(ArrayList<ManufacturerItems> manufacturerItems){
        this.manufacturerItems.clear();
        this.manufacturerItems.addAll(manufacturerItems);
        notifyDataSetChanged();
    }
}
