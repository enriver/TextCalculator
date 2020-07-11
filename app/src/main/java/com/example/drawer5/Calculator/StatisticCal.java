package com.example.drawer5.Calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drawer5.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatisticCal extends Fragment implements View.OnClickListener{
    private int REQUEST_TEST=1;

    private Boolean isResult=false;
    private String input="";
    private String output="";
    private int count=0;
    private String text="";
    private int list_value;
    private EditText e1;
    private String expression="";
    private DBHelper dbHelper;
    private Button button_result,num0,num1,num2,num3,num4,num5,num6,num7,num8,num9,
            dot,clear,backspace,posneg,button_input;
    private ImageButton camera_button;

    private double max_v=0;
    private double qt3_v=0;
    private double median_v=0;
    private double qt1_v=0;
    private double min_v=0;
    private double mean_v=0;
    private double var_v=0;
    private double stdv_v=0;
    private double n=0;
    private double sum=0;
    private double sum2=0;

    private TextView max;
    private TextView qt3;
    private TextView median;
    private TextView qt1;
    private TextView min;
    private TextView mean;
    private TextView var;
    private TextView stdv;

    static final List LIST=new ArrayList();
    private ListView listview;
    private ArrayAdapter adapter;
    private AlertDialog.Builder alertDialogBuilder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_engineering_cal);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Context context = getActivity();
        View view = inflater.inflate(R.layout.activity_statistic_cal, container, false);
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("ALERT");

        adapter=new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,LIST);
        listview= (ListView) view.findViewById(R.id.store);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list_value=(int)adapterView.getItemIdAtPosition(i);
                alertDialogBuilder.setMessage("Do you want to remove this value?").setCancelable(false)
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                LIST.remove(list_value);
                                adapter.notifyDataSetChanged();
                            }
                        }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();

            }
        });


        camera_button = (ImageButton) view.findViewById(R.id.camera_button);
        e1 = (EditText) view.findViewById(R.id.input);

        num0=(Button) view.findViewById(R.id.num0);
        num1=(Button) view.findViewById(R.id.num1);
        num2=(Button) view.findViewById(R.id.num2);
        num3=(Button) view.findViewById(R.id.num3);
        num4=(Button) view.findViewById(R.id.num4);
        num5=(Button) view.findViewById(R.id.num5);
        num6=(Button) view.findViewById(R.id.num6);
        num7=(Button) view.findViewById(R.id.num7);
        num8=(Button) view.findViewById(R.id.num8);
        num9=(Button) view.findViewById(R.id.num9);
        dot=(Button) view.findViewById(R.id.dot);
        clear=(Button) view.findViewById(R.id.clear);
        backspace=(Button) view.findViewById(R.id.backSpace);
        posneg=(Button) view.findViewById(R.id.posneg);
        button_result=(Button) view.findViewById(R.id.button_result);
        button_input=(Button) view.findViewById(R.id.button_input);

        max=(TextView) view.findViewById(R.id.max);
        qt3=(TextView) view.findViewById(R.id.qt3);
        median=(TextView) view.findViewById(R.id.median);
        qt1=(TextView) view.findViewById(R.id.qt1);
        min=(TextView) view.findViewById(R.id.min);
        mean=(TextView) view.findViewById(R.id.mean);
        var=(TextView) view.findViewById(R.id.var);
        stdv=(TextView) view.findViewById(R.id.stdv);


        dbHelper=new DBHelper(getContext());

        camera_button.setOnClickListener(this);
        e1.setOnClickListener(this);

        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);

        dot.setOnClickListener(this);
        clear.setOnClickListener(this);
        backspace.setOnClickListener(this);

        posneg.setOnClickListener(this);

        button_input.setOnClickListener(this);
        button_result.setOnClickListener(this);
        e1.requestFocus();
        e1.setText("");


        return view;
    }
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.camera_button:
                max.setText("");
                qt3.setText("");
                median.setText("");
                qt1.setText("");
                min.setText("");
                mean.setText("");
                var.setText("");
                stdv.setText("");

                Intent intent=new Intent(getContext(), CameraActivity2.class);
                startActivityForResult(intent,REQUEST_TEST);
                break;
            case R.id.num0:
                if(isResult==false){
                    e1.setText(e1.getText() + "0");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("0");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;
            case R.id.num1:
                if(isResult==false){
                    e1.setText(e1.getText() + "1");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("1");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num2:
                if(isResult==false){
                    e1.setText(e1.getText() + "2");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("2");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num3:
                if(isResult==false){
                    e1.setText(e1.getText() + "3");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("3");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;


            case R.id.num4:
                if(isResult==false){
                    e1.setText(e1.getText() + "4");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("4");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num5:
                if(isResult==false){
                    e1.setText(e1.getText() + "5");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("5");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num6:
                if(isResult==false){
                    e1.setText(e1.getText() + "6");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("6");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num7:
                if(isResult==false){
                    e1.setText(e1.getText() + "7");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("7");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num8:
                if(isResult==false){
                    e1.setText(e1.getText() + "8");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("8");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num9:
                if(isResult==false){
                    e1.setText(e1.getText() + "9");
                    e1.setSelection(e1.getText().length());
                }else {
                    e1.setText("9");
                    e1.setSelection(e1.getText().length());
                    isResult=false;
                }
                break;

            case R.id.dot:
                if(isResult==false){
                    if (count == 0 && e1.length() != 0) {
                        e1.setText(e1.getText() + ".");
                        e1.setSelection(e1.getText().length());
                        count++;
                    }
                }else {

                }

                break;

            case R.id.clear:
                max.setText("");
                qt3.setText("");
                mean.setText("");
                qt1.setText("");
                min.setText("");
                median.setText("");
                var.setText("");
                stdv.setText("");

                e1.setText("");
                e1.setSelection(e1.getText().length());
                count = 0;
                LIST.clear();
                adapter.notifyDataSetChanged();
                break;


            case R.id.backSpace:
                text=e1.getText().toString();
                int p= e1.getSelectionStart();
                if(p==0){
                    break;
                }
                Log.d(getClass().getName(), "position : " + p);
                if(text.length()>0)
                {
                    if(text.endsWith("."))
                    {
                        count=0;
                    }
                    String sc = text;
                    String sa = text.substring(0,p-1);
                    String sb = sc.substring(p);
                    String newText= sa + sb;
                    //to delete the data contained in the brackets at once
                    e1.setText(newText);
                    e1.setSelection(p-1);
                }
                break;
            case R.id.posneg:
                if (e1.length() != 0) {
                    String s = e1.getText().toString();
                    char arr[] = s.toCharArray();
                    if (arr[0] == '-'){
                        e1.setText(s.substring(1, s.length()));
                        e1.setSelection(e1.getText().length());}
                    else{
                        e1.setText("-" + s);
                        e1.setSelection(e1.getText().length());}
                }
                break;
            case R.id.button_input:
                if(e1.length()!=0){
                    text=e1.getText().toString();
                    double num=Double.parseDouble(text);
                    LIST.add(num);
                    Collections.sort(LIST);
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(getContext(),num+"이 과연 뽑혔는지",Toast.LENGTH_LONG).show();
                    e1.setText("");
                    count=0;
                }

                break;
            case R.id.button_result:
                try {
                    sum=0;
                    sum2=0;
                    n=LIST.size();
                    if(n%2==0){ // n이 짝수개일때
                        for(int i=0; i<n; i++){
                            sum+=(double)LIST.get(i);
                            max_v=(double)LIST.get(i);
                        }
                        median_v=((double)((LIST.get((int)Math.round(n/2)-1)))+(double)(LIST.get((int)(Math.round(n/2)))))/2;
                        mean_v= sum/n;
                        for(int i=0; i<n;i++){
                            sum2+=Math.pow(((double)LIST.get(i)-mean_v),2);
                        }
                        var_v=(double)sum2/(n-1);
                        stdv_v=(double)Math.sqrt(var_v);
                    }else{// n이 홀수개일때
                        for(int i=0; i<n; i++){
                            sum+=(double)LIST.get(i);
                            max_v=(double)LIST.get(i);
                        }
                        median_v=(double)LIST.get((int)Math.round(n/2)-1);
                        mean_v= sum/n;

                        for(int i=0; i<n;i++){

                            sum2+=Math.pow(((double)LIST.get(i)-mean_v),2);
                            Log.d(getClass().getName(),"@@@@@@@@@@@@@@@@"+sum2);
                        }
                        var_v=sum2/(n-1);
                        stdv_v=Math.sqrt(var_v);
                    }

                    min_v=(double)LIST.get(0);
                    qt1_v=quantile(n,1);
                    qt3_v=quantile(n,3);

                    max.setText(Math.round(max_v*100.0)/100.0+"");
                    qt3.setText(Math.round(qt3_v*100.0)/100.0+"");
                    median.setText(Math.round(median_v*100.0)/100.0+"");
                    qt1.setText(Math.round(qt1_v*100.0)/100.0+"");
                    min.setText(Math.round(min_v*100.0)/100.0+"");
                    mean.setText(Math.round(mean_v*100.0)/100.0+"");
                    var.setText(Math.round(var_v*100.0)/100.0+"");
                    stdv.setText(Math.round(stdv_v*100.0)/100.0+"");

                    for(int i=0; i<LIST.size(); i++){
                        input=input+LIST.get(i)+"/";
                    };
                    output="Max : "+Math.round(max_v*100.0)/100.0+" / qt3 : "+Math.round(qt3_v*100.0)/100.0+"  /  median : "+Math.round(median_v*100.0)/100.0+"   / qt1 : "+Math.round(qt1_v*100.0)/100.0+"   / min : "+Math.round(min_v*100.0)/100.0+" /  mean : "+Math.round(mean_v*100.0)/100.0+"  /  var : "+Math.round(var_v*100.0)/100.0+"  /  stdv : "+Math.round(stdv_v*100.0)/100;


                    dbHelper.insert("STATISTIC", "Input - "+input);
                    dbHelper.insert("STATISTIC", "Output - "+output);

                    LIST.clear();
                    adapter.notifyDataSetChanged();


                } catch (Exception e) {
                    Toast.makeText(getContext(),"Invalid Expression",Toast.LENGTH_LONG).show();
                    e1.setSelection(e1.getText().length());
                    e1.setText("");
                    ;
                    Log.d(getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+e.getMessage());
                }
                break;
        }
    }
    public double quantile (double n, double q){
        double i= (q/4)*(n-1)+1;
        Log.d(getClass().getName(), "i : "+ i);
        double floor =(double) LIST.get((int) Math.floor(i)-1);
        Log.d(getClass().getName(), "floor : "+ floor);
        double ceil =(double) LIST.get((int) Math.ceil(i)-1);
        Log.d(getClass().getName(), "ceil : "+ ceil);
        double result = floor + (ceil-floor)*(i-Math.floor(i));
        double temp = i-Math.floor(i);
        Log.d(getClass().getName(), "i-Math.floor(i) : "+ temp);
        return result;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TEST){
            //Toast.makeText(getContext(),"봄 사랑 벚꽃 말고",Toast.LENGTH_LONG).show();
        }if(resultCode==123){
            String value=data.getExtras().getString("result");
            String [] arr = value.split("\n");
            for(int i=0; i<arr.length; i++) {
                double d=0;
                try {
                    d=Integer.parseInt(arr[i]);
                    //d = Double.parseDouble(arr[i]);
                    LIST.add(d);
                } catch (Exception e) {
                    Log.d(getClass().getName(), "string value is inserted! : "+arr[i]);
                    Log.d(getClass().getName(), "error : "+e.getMessage());
                }
            }
            adapter.notifyDataSetChanged();
            //e1.setText(value);
        }
    }
}