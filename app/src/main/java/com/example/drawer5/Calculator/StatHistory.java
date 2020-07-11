package com.example.drawer5.Calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.drawer5.R;

import java.util.ArrayList;

public class StatHistory extends Fragment implements View.OnClickListener {
    private ListView lv;
    private DBHelper dbHelper;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private String calcName="";
    private String []EmptyList={"There is  no history yet"};
    private Button deleteHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_history);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_history,container,false);

        lv=(ListView)view.findViewById(R.id.listView);
        dbHelper=new DBHelper(getContext());
        //calcName=getIntent().getStringExtra("calcName");
        //calcName=getArguments().getString("calcName");
        list=dbHelper.showHistory("STATISTIC");
        if(!list.isEmpty())
            adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        else
            adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,EmptyList);
        lv.setAdapter(adapter);

        deleteHistory=(Button) view.findViewById(R.id.deleteHistory);

        deleteHistory.setOnClickListener(this);

        return view;

    }

    public void onClick(View v)
    {
        dbHelper.deleteRecords("STATISTIC");
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,EmptyList);
        lv.setAdapter(adapter);
    }
}
