package com.beayeah.gnb.gnbbershka.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beayeah.gnb.gnbbershka.MainActivity;
import com.beayeah.gnb.gnbbershka.R;
import com.beayeah.gnb.gnbbershka.SumFragment;
import com.beayeah.gnb.gnbbershka.pojos.Product;
import com.massivedisaster.activitymanager.ActivityFragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private MainActivity context;
    private List<Product> mValues;

    public ProductAdapter(Context context, TreeSet<Product> items) {
        this.context = (MainActivity) context;
        this.mValues = new ArrayList<Product>();
        this.mValues.addAll(items);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mProduct = mValues.get(holder.getAdapterPosition());
        holder.tv_sku.setText(mValues.get(holder.getAdapterPosition()).getSku());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sku", mValues.get(holder.getAdapterPosition()).getSku());
                ActivityFragmentManager.replace(context, SumFragment.class).addToBackStack(null)
                        .setBundle(bundle)
                        .commit();
            }
        });
    }

    public void addAll(TreeSet<Product> items) {
        mValues.addAll(items);
    }

    public void clear() {
        mValues.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sku;
        public Product mProduct;
        View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_sku = (TextView) view.findViewById(R.id.layo_main_row_sku);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
