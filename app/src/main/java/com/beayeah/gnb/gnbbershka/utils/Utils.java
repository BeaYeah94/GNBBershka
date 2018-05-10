package com.beayeah.gnb.gnbbershka.utils;


import android.os.AsyncTask;
import android.util.Log;

import com.beayeah.gnb.gnbbershka.adapters.ProductAdapter;
import com.beayeah.gnb.gnbbershka.adapters.SumAdapter;
import com.beayeah.gnb.gnbbershka.pojos.Conversion;
import com.beayeah.gnb.gnbbershka.pojos.Product;
import com.beayeah.gnb.gnbbershka.pojos.ProductSum;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

public class Utils {

    public static ArrayList<Product> products = new ArrayList<Product>();
    public static ArrayList<Product> singleproducts;
    public static ProductAdapter adapter;
    public static ArrayList<Conversion> conversions;
    private static Stack<String> stack = new Stack<String>();

    public static void LoadProducts() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            HttpClient client;
            HttpGet httpGet;
            StringBuilder builder;

            @Override
            protected void onPreExecute() {
                builder = new StringBuilder();
                client = new DefaultHttpClient();
                httpGet = new HttpGet(
                        "http://quiet-stone-2094.herokuapp.com/transactions");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("Accept", "application/json");
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } else {
                        Log.d("ERROR", "Failed to download file");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                try {
                    TreeSet<Product> auxSetProd = new TreeSet<Product>();

                    JSONArray jsonArray = new JSONArray(builder.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        auxSetProd.add(new Product(jsonObject.getString("sku"),
                                jsonObject.getDouble("amount"), jsonObject
                                .getString("currency")));
                        products.add(new Product(jsonObject.getString("sku"),
                                jsonObject.getDouble("amount"), jsonObject
                                .getString("currency")));
                    }

                    adapter.addAll(auxSetProd);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        task.execute((Void[]) null);
    }

    public static void LoadConversions() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            HttpClient client;
            HttpGet httpGet;
            StringBuilder builder;

            @Override
            protected void onPreExecute() {
                builder = new StringBuilder();
                client = new DefaultHttpClient();
                httpGet = new HttpGet(
                        "http://quiet-stone-2094.herokuapp.com/rates");
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("Accept", "application/json");
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } else {
                        Log.d("ERROR", "Failed to download file");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                try {
                    JSONArray jsonArray = new JSONArray(builder.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        conversions.add(new Conversion(jsonObject
                                .getString("from"), jsonObject.getString("to"),
                                jsonObject.getDouble("rate")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        task.execute((Void[]) null);
    }

    public static void GetSumOfProduct(String sku, ProductSum ps) {
        for (Product p : Utils.products) {
            if (sku.compareTo(p.getSku()) == 0) {
                Double newSum = ps.getSum() + convToEUR(p.getCurrency(), p.getAmount());
                ps.setSum(newSum);
                ps.getProduct().add(p);
            }
        }
    }

    public static Double convToEUR(String currency, Double amount) {
        if (currency.compareTo("EUR") == 0) return amount;
        stack.clear();
        stack.push(currency);

        ArrayList<Conversion> RatesToConversion = new ArrayList<Conversion>();
        GetRatesToConversion(listOfCurrFROM(currency), RatesToConversion);
        return toConv(amount, RatesToConversion);
    }

    private static Double toConv(Double amount, ArrayList<Conversion> ratiosToAppli) {
        Double sum = amount;
        for (Conversion cc : ratiosToAppli)
            sum *= cc.getRate();

        return sum;
    }

    private static void GetRatesToConversion(
            ArrayList<Conversion> occurences_list,
            ArrayList<Conversion> ratiosToAppli) {

        if (existCurrencyInTO("EUR", occurences_list)) {
            ratiosToAppli.add(searchByTO("EUR", occurences_list));
            return;
        } else {
            for (Conversion cc : occurences_list) {
                ratiosToAppli.add(cc);
                if (!stack.contains(cc.getTo())) {
                    stack.push(cc.getTo());
                    ArrayList<Conversion> listTemp = listOfCurrFROM(cc.getTo());
                    if (existCurrencyInTO("EUR", listTemp)) {
                        ratiosToAppli.add(searchByTO("EUR", listTemp));
                        return;
                    } else
                        GetRatesToConversion(listTemp, ratiosToAppli);
                }
            }
        }

        return;
    }

    private static boolean existCurrencyInTO(String currency,
                                      ArrayList<Conversion> l_conversions) {
        for (Conversion cc : l_conversions)
            if (cc.getTo().compareTo(currency) == 0)
                return true;

        return false;
    }

    private static Conversion searchByTO(String to, ArrayList<Conversion> l_convs) {
        for (Conversion cc : l_convs)
            if (cc.getTo().compareTo(to) == 0)
                return cc;

        return null;
    }

    private static ArrayList<Conversion> listOfCurrFROM(String from) {
        ArrayList<Conversion> ret = new ArrayList<Conversion>();
        for (Conversion cc : Utils.conversions)
            if (cc.getFrom().compareTo(from) == 0)
                ret.add(cc);

        return ret;
    }

}
