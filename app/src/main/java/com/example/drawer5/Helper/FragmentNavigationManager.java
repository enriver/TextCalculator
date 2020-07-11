package com.example.drawer5.Helper;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drawer5.BuildConfig;
import com.example.drawer5.Calculator.EngineeringCal;
import com.example.drawer5.Calculator.History;
import com.example.drawer5.Calculator.StatHistory;
import com.example.drawer5.Calculator.StatisticCal;
import com.example.drawer5.Fragment.FragmentContent;
import com.example.drawer5.Interface.NavigationManager;
import com.example.drawer5.MainActivity;
import com.example.drawer5.R;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private FragmentTransaction ft ;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity){
        if(mInstance==null)
            mInstance=new FragmentNavigationManager();
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity){
        mainActivity=mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }


    @Override
    public void showFragment(String title){

        if(title.equals("Engineering Calculator")){
            ft = mFragmentManager.beginTransaction().replace(R.id.container, new EngineeringCal());
            ft.addToBackStack(null);
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }else if(title.equals("Engineering History")){
            ft=mFragmentManager.beginTransaction().replace(R.id.container, new History());
            ft.addToBackStack(null);
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }else if(title.equals("Statistic Calculator")){
            ft=mFragmentManager.beginTransaction().replace(R.id.container, new StatisticCal());
            ft.addToBackStack(null);
            ft.commit();
            Log.d(getClass().getName(),"hihi");
            mFragmentManager.executePendingTransactions();
        }
        else if(title.equals("Statistic History")){
            ft=mFragmentManager.beginTransaction().replace(R.id.container, new StatHistory());
            ft.addToBackStack(null);
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
        else{
            Log.d(getClass().getName(),"봄 사랑 벚꽃 말고");
            showFragment(FragmentContent.newInstance(title),false);
        }


    }

    private void showFragment(Fragment fragmentContent, boolean b) {
        ft = mFragmentManager.beginTransaction().replace(R.id.container,new EngineeringCal());
        //ft.addToBackStack(null);
        if(b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        mFragmentManager.executePendingTransactions();
    }
}
