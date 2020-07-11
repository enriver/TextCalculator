package com.example.drawer5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.drawer5.Adapter.CustomExpandableListAdapter;
import com.example.drawer5.Calculator.EngineeringCal;
import com.example.drawer5.Calculator.History;
import com.example.drawer5.Helper.FragmentNavigationManager;
import com.example.drawer5.Interface.NavigationManager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class    MainActivity extends AppCompatActivity {
    private int REQUEST_TEST=1;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstchild;
    private NavigationManager navigationManager;

    private ArrayList<String> arrList = new ArrayList();

    private  int selector = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView) findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getmInstance(this);

        initItems();

       View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setupDrawer();


        if(savedInstanceState==null)
            selectFirstItemAsDefault();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Engineering Calculator");

        //리스트 채우기
        arrList.add("Engineering Calculator");
        arrList.add("Engineering History");
        arrList.add("Statistic Calculator");
        arrList.add("Statistic History");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if(navigationManager != null){
            String firstItem=lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setupDrawer() {
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
       mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawersItem() {
        adapter = new CustomExpandableListAdapter(this,lstTitle,lstchild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition));
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long l) {

                String selectedItem1=((lstTitle.get(groupPosition)));
                String selectedItem2=((List)(lstchild.get(lstTitle.get(groupPosition)))).get(childPosition).toString();

                getSupportActionBar().setTitle(selectedItem1+ " "+selectedItem2);

                String selected = (selectedItem1+" "+selectedItem2);

                switch(selected){
                    case "Engineering Calculator":
                        int position = arrList.indexOf("Engineering Calculator");
                        if(selector != position){
                            navigationManager.showFragment("Engineering Calculator");
                            selector = position;
                        }
                        break;
                       case "Engineering History":
                        int position2 = arrList.indexOf("Engineering History");
                        if(selector != position2){
                            navigationManager.showFragment("Engineering History");
                            selector = position2;
                        }
                        break;
                    case "Statistic Calculator":
                        int position3 = arrList.indexOf("Statistic Calculator");
                        if(selector != position3){
                            navigationManager.showFragment("Statistic Calculator");
                            selector = position3;
                        }
                        break;
                    case "Statistic History":
                        int position4 = arrList.indexOf("Statistic History");
                        if(selector != position4){
                            navigationManager.showFragment("Statistic History");
                            selector = position4;
                        }
                        break;
                }

                return true;
            }
        });
    }



    private void genData(){
        List<String> title= Arrays.asList("Engineering","Statistic");
        List<String> childItem=Arrays.asList("Calculator", "History");

        lstchild= new TreeMap<>();
        lstchild.put(title.get(0),childItem);
        lstchild.put(title.get(1),childItem);


        lstTitle=new ArrayList<>(lstchild.keySet());


    }

    private void initItems(){
        items=new String[]{"Engineering","Statistic"};

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
