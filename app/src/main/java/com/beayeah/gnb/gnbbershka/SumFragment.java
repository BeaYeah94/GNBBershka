package com.beayeah.gnb.gnbbershka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beayeah.gnb.gnbbershka.adapters.SumAdapter;
import com.beayeah.gnb.gnbbershka.pojos.Product;
import com.beayeah.gnb.gnbbershka.pojos.ProductSum;
import com.beayeah.gnb.gnbbershka.utils.Utils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SumFragment extends Fragment {

    final static String ARG_PARAM1 = "sku";
    RecyclerView recycler;
    SumAdapter adapter;
    TextView sum;
    private String productSku;

    public SumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productSku = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sum, container, false);
        recycler = v.findViewById(R.id.sum_list);
        sum = v.findViewById(R.id.total_sum);

        Utils.singleproducts = new ArrayList<Product>();

        ProductSum productSum = new ProductSum();
        Utils.GetSumOfProduct(productSku, productSum);
        sum.setText(getString(R.string.euro, productSum.getSumRoudedAsBankers().toString()));

        adapter = new SumAdapter(productSum.getProduct(), Utils.conversions);

        RecyclerView recyclerView = (RecyclerView) recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        return v;
    }

}
