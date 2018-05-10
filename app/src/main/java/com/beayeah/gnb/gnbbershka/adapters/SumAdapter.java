package com.beayeah.gnb.gnbbershka.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beayeah.gnb.gnbbershka.R;
import com.beayeah.gnb.gnbbershka.pojos.Conversion;
import com.beayeah.gnb.gnbbershka.pojos.Product;
import com.beayeah.gnb.gnbbershka.pojos.ProductSum;
import com.beayeah.gnb.gnbbershka.utils.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SumAdapter extends RecyclerView.Adapter<SumAdapter.ViewHolder> {

    private ArrayList<Conversion> conversions;
    private DecimalFormat twoDForm = new DecimalFormat("#.00");
    private List<Product> mValues;

    public SumAdapter(List<Product> items, ArrayList<Conversion> conversions) {
        this.mValues = items;
        this.conversions = conversions;
    }

    @Override
    public SumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sum, parent, false);
        return new SumAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onBindViewHolder(SumAdapter.ViewHolder holder, int position) {
        holder.mProduct = mValues.get(position);

        holder.tv_sku.setText(String.valueOf(mValues.get(position).getSku()));

        mValues.get(position).setAmount(Utils.convToEUR(mValues.get(position).getCurrency(),mValues.get(position).getAmount()));

        holder.tv_amount.setText(holder.mView.getResources().getString(R.string.euro, mValues.get(position).getAmountRoudedAsBankers().toString()));
    }

    public void addItem(Product item) {
        mValues.add(item);
    }

    public void removeItem(Product item) {
        mValues.remove(item);
    }

    public void addAll(List<Product> items) {
        mValues.addAll(items);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView tv_sku;
        private TextView tv_amount;
        private Product mProduct;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            tv_sku = (TextView) view.findViewById(R.id.layo_list_sum_row_sku);
            tv_amount = (TextView) view.findViewById(R.id.layo_list_sum_row_amount);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
