package com.example.irprogramtest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {
    public ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    public void setup(DrawerLayout dl){
        mDrawerLayout = dl;
        mToggle = new ActionBarDrawerToggle(getActivity(),dl,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
                        mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    else
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                    return true;
                }
                return false;
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mToggle.syncState();
            }
        });
    }
}
