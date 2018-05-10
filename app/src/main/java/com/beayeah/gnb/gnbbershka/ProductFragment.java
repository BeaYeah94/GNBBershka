package com.beayeah.gnb.gnbbershka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beayeah.gnb.gnbbershka.adapters.ProductAdapter;
import com.beayeah.gnb.gnbbershka.pojos.Conversion;
import com.beayeah.gnb.gnbbershka.pojos.Product;
import com.beayeah.gnb.gnbbershka.utils.Utils;

import java.util.ArrayList;
import java.util.TreeSet;

public class ProductFragment extends Fragment {

    RecyclerView recycler;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        recycler = v.findViewById(R.id.product_list_fragment);
        Utils.conversions = new ArrayList<Conversion>();

        Utils.adapter = new ProductAdapter(getContext(), new TreeSet<Product>());

        RecyclerView recyclerView = (RecyclerView) recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(Utils.adapter);

        Utils.adapter.clear();
        Utils.adapter.notifyDataSetChanged();


        Utils.LoadProducts();
        Utils.LoadConversions();


        Toast.makeText(getActivity(), "Loading Products, Please wait...",
                Toast.LENGTH_SHORT).show();
        return v;
    }


    @Override
    public String toString() {
        return "ProductFragment";
    }

}
