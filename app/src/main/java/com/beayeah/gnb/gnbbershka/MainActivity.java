package com.beayeah.gnb.gnbbershka;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.massivedisaster.activitymanager.ActivityFragmentManager;
import com.massivedisaster.activitymanager.activity.AbstractFragmentActivity;

public class MainActivity extends AbstractFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityFragmentManager.add(this, ProductFragment.class).addToBackStack(null)
                .commit();
    }

    // The layout resource you want to find the FrameLayout.
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    // The FrameLayout id you want to inject the fragments.
    @Override
    public int getContainerViewId() {
        return R.id.principal_fragment_container;
    }

    /*@Override
    protected Class<? extends Fragment> getDefaultFragment() {
        return ProductFragment.class;
    }*/

}
