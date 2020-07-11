package com.example.drawer5.Calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.drawer5.MainActivity;
import com.example.drawer5.Helper.FragmentNavigationManager;
import com.example.drawer5.R;

import java.util.zip.Inflater;

public class EngineeringCal extends Fragment implements View.OnClickListener {
    private int REQUEST_TEST=1;

    private Boolean isResult = false;
    private EditText e1,e2;
    private int count=0;
    private String expression="";
    private String text="";
    private Double result=0.0;
    private DBHelper dbHelper;
    private Button mode,inverse,square,xpowy,log,sin,cos,tan,sqrt,fact,num0,num1,num2,num3,num4,num5,num6,num7,num8,num9,
    dot,clear,backspace,plus,minus,multiply,divide,ob,cb,posneg,pi,equal;
    private ImageButton camera_button;
    private int angleMode=1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_engineering_cal);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Context context = getActivity();
        View view = inflater.inflate(R.layout.activity_engineering_cal,container,false);


        camera_button = (ImageButton) view.findViewById(R.id.camera_button);
        e1 = (EditText) view.findViewById(R.id.editText);
        e2 = (EditText) view.findViewById(R.id.editText2);
        e1.setShowSoftInputOnFocus(false);
        e2.setShowSoftInputOnFocus(false);
        mode = (Button) view.findViewById(R.id.mode);
        inverse = (Button) view.findViewById(R.id.inverse);
        square = (Button) view.findViewById(R.id.square);
        xpowy = (Button) view.findViewById(R.id.xpowy);
        log = (Button) view.findViewById(R.id.log);
        sin = (Button) view.findViewById(R.id.sin);
        cos = (Button) view.findViewById(R.id.cos);
        tan = (Button) view.findViewById(R.id.tan);
        sqrt= (Button) view.findViewById(R.id.sqrt);
        fact = (Button) view.findViewById(R.id.factorial);
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
        plus=(Button) view.findViewById(R.id.plus);
        minus=(Button) view.findViewById(R.id.minus);
        multiply=(Button) view.findViewById(R.id.multiply);
        divide=(Button) view.findViewById(R.id.divide);
        ob=(Button) view.findViewById(R.id.openBracket);
        cb=(Button) view.findViewById(R.id.closeBracket);
        posneg=(Button) view.findViewById(R.id.posneg);
        pi=(Button) view.findViewById(R.id.pi);
        equal=(Button) view.findViewById(R.id.equal);




        dbHelper=new DBHelper(getContext());


        camera_button.setOnClickListener(this);
        e1.setOnClickListener(this);
        e2.setOnClickListener(this);
        mode.setOnClickListener(this);
        inverse.setOnClickListener(this);
        square.setOnClickListener(this);
        xpowy.setOnClickListener(this);
        log.setOnClickListener(this);
        sin.setOnClickListener(this);
        cos.setOnClickListener(this);
        tan.setOnClickListener(this);
        sqrt.setOnClickListener(this);
        fact.setOnClickListener(this);
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
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        ob.setOnClickListener(this);
        cb.setOnClickListener(this);
        posneg.setOnClickListener(this);
        pi.setOnClickListener(this);
        equal.setOnClickListener(this);


        e2.requestFocus();
        e2.setText("");
        mode.setTag(1);

        return view;
    }
    public void onClick(View v)
    {
        angleMode=((int)mode.getTag());
        switch (v.getId()) {

            case R.id.camera_button:
                Intent intent=new Intent(getContext(),CameraActivity.class);
                e2.setText("");
                e1.setText("");
                startActivityForResult(intent,REQUEST_TEST);
                //String item=getActivity().getIntent().getExtras().getString("result");
               // e1.setText(item);
                break;
            case R.id.inverse:
                text = e2.getText().toString();
                e2.setText("1/(" + text + ")");
                e2.setSelection(e2.getText().length());

            case R.id.mode:
                //change the angle property for trignometric operations if mode button is clicked
                if(angleMode==1)
                {
                    mode.setTag(2);
                    mode.setText(R.string.mode2);
                }
                else
                {
                    mode.setTag(1);
                    mode.setText(R.string.mode1);
                }
                break;

            case R.id.num0:
                if(isResult==false){
                    e2.setText(e2.getText() + "0");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("0");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num1:
                if(isResult==false){
                    e2.setText(e2.getText() + "1");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("1");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num2:
                if(isResult==false){
                    e2.setText(e2.getText() + "2");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("2");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num3:
                if(isResult==false){
                    e2.setText(e2.getText() + "3");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("3");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;


            case R.id.num4:
                if(isResult==false){
                    e2.setText(e2.getText() + "4");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("4");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num5:
                if(isResult==false){
                    e2.setText(e2.getText() + "5");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("5");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num6:
                if(isResult==false){
                    e2.setText(e2.getText() + "6");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("6");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num7:
                if(isResult==false){
                    e2.setText(e2.getText() + "7");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("7");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num8:
                if(isResult==false){
                    e2.setText(e2.getText() + "8");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("8");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.num9:
                if(isResult==false){
                    e2.setText(e2.getText() + "9");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("9");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.pi:
                if(isResult==false){
                    e2.setText(e2.getText() + "pi");
                    e2.setSelection(e2.getText().length());
                }else {
                    e2.setText("pi");
                    e2.setSelection(e2.getText().length());
                    isResult=false;
                }
                break;

            case R.id.dot:
                if(isResult==false){
                    if (count == 0 && e2.length() != 0) {
                        e2.setText(e2.getText() + ".");
                        e2.setSelection(e2.getText().length());
                        count++;
                    }
                }else {

                }

                break;

            case R.id.clear:
                e1.setText("");
                e2.setText("");
                e2.setSelection(e2.getText().length());
                count = 0;
                expression = "";
                break;

            case R.id.backSpace:
                text=e2.getText().toString();
                int p= e2.getSelectionStart();
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
                    if(text.endsWith(")"))
                    {
                        char []a=text.toCharArray();
                        int pos=a.length-2;
                        int counter=1;
                        //to find the opening bracket position
                        for(int i=a.length-2;i>=0;i--)
                        {
                            if(a[i]==')')
                            {
                                counter++;
                            }
                            else if(a[i]=='(')
                            {
                                counter--;
                            }
                            //if decimal is deleted b/w brackets then count should be zero
                            else if(a[i]=='.')
                            {
                                count=0;
                            }
                            //if opening bracket pair for the last bracket is found
                            if(counter==0)
                            {
                                pos=i;
                                break;
                            }
                        }
                        newText=text.substring(0,pos);
                    }
                    //if e2 edit text contains only - sign or sqrt or any other text functions
                    // at last then clear the edit text e2
                    if(newText.equals("-")||newText.endsWith("sqrt")||newText.endsWith("log")||newText.endsWith("ln")
                            ||newText.endsWith("sin")
                            ||newText.endsWith("cos")
                            ||newText.endsWith("tan"))
                    {
                        newText="";
                    }
                    //if pow sign is left at the last or divide sign
                    else if(newText.endsWith("^")||newText.endsWith("/"))
                        newText=newText.substring(0,newText.length()-1);
                    else if(newText.endsWith("pi")||newText.endsWith("e^"))
                        newText=newText.substring(0,newText.length()-2);
                    e2.setText(newText);
                    e2.setSelection(p-1);
                }
                break;

            case R.id.plus:
                operationClicked("+");
                break;

            case R.id.minus:
                operationClicked("-");
                break;

            case R.id.divide:
                operationClicked("/");
                break;

            case R.id.multiply:
                operationClicked("*");
                break;

            case R.id.sqrt:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    e2.setText("sqrt(" + text + ")");
                    e2.setSelection(e2.getText().length());
                }
                break;

            case R.id.square:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    e2.setText("(" + text + ")^2");
                    e2.setSelection(e2.getText().length());
                }
                break;

            case R.id.xpowy:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    e2.setText("(" + text + ")^");
                    e2.setSelection(e2.getText().length());
                }
                break;

            case R.id.log:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    e2.setText("log(" + text + ")");
                    e2.setSelection(e2.getText().length());

                }
                break;

            case R.id.factorial:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    String res="";
                    try
                    {
                        CalculateFactorial cf=new CalculateFactorial();
                        int []arr=cf.factorial((int)Double.parseDouble(String.valueOf(new ExtendedDoubleEvaluator().evaluate(text))));
                        int res_size=cf.getRes();
                        if(res_size>20)
                        {
                            for (int i=res_size-1; i>=res_size-20; i--)
                            {
                                if(i==res_size-2)
                                    res+=".";
                                res+=arr[i];
                            }
                            res+="E"+(res_size-1);
                        }
                        else
                        {
                            for (int i=res_size-1; i>=0; i--)
                            {
                                res+=arr[i];
                            }
                        }
                        e2.setText(res);
                        e2.setSelection(e2.getText().length());
                    }
                    catch (Exception e) {
                        if (e.toString().contains("ArrayIndexOutOfBoundsException")) {
                            e2.setText("Result too big!");
                            e2.setSelection(e2.getText().length());
                        } else
                            e2.setText("Invalid!!");
                        e2.setSelection(e2.getText().length());
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.sin:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(text));
                        e2.setText("sin(" + angle + ")");
                        e2.setSelection(e2.getText().length());
                    }
                    else
                    {
                        e2.setText("sin(" + text + ")");
                        e2.setSelection(e2.getText().length());
                    }
                }
                break;

            case R.id.cos:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(text));
                        e2.setText("cos(" + angle + ")");
                        e2.setSelection(e2.getText().length());
                    }
                    else
                    {
                        e2.setText("cos(" + text + ")");
                        e2.setSelection(e2.getText().length());
                    }
                }
                break;

            case R.id.tan:
                if (e2.length() != 0) {
                    text = e2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(text));
                        e2.setText("tan(" + angle + ")");
                        e2.setSelection(e2.getText().length());
                    }
                    else
                    {
                        e2.setText("tan(" + text + ")");
                        e2.setSelection(e2.getText().length());
                    }
                }
                break;

            case R.id.posneg:
                if (e2.length() != 0) {
                    String s = e2.getText().toString();
                    char arr[] = s.toCharArray();
                    if (arr[0] == '-'){
                        e2.setText(s.substring(1, s.length()));
                    e2.setSelection(e2.getText().length());}
                    else{
                        e2.setText("-" + s);
                    e2.setSelection(e2.getText().length());}
                }
                break;

            case R.id.equal:
                /*for more knowledge on DoubleEvaluator and its tutorial go to the below link
                http://javaluator.sourceforge.net/en/home/*/
                if (e2.length() != 0) {
                    // 아무것도 안넣고 = 넣었을때
                    text = e2.getText().toString();
                    Log.d(getClass().getName(), text);
                    expression = e1.getText().toString() + text;
                }
                e1.setText("");
                isResult=true;

                // 계산할 수식이 없을때 (바로 = 넣은 경우)
                if (expression.length() == 0)
                    expression = "0.0";
                try {
                    //evaluate the expression
                    result = new ExtendedDoubleEvaluator().evaluate(expression);
                    //insert expression and result in sqlite database if expression is valid and not 0.0
                    if (String.valueOf(result).equals("6.123233995736766E-17"))
                    {
                        result=0.0;
                        e2.setText(result + "");
                        e2.setSelection(e2.getText().length());
                    }
                    else if(String.valueOf(result).equals("1.633123935319537E16")){
                        e2.setText("infinity");
                        e2.setSelection(e2.getText().length());
                    }
                    else{
                        e2.setText(result + "");
                    e2.setSelection(e2.getText().length());}
                    if (!expression.equals("0.0"))
                        dbHelper.insert("ENGINEERING", expression + " = " + result);
                } catch (Exception e) {
                    e2.setText("Invalid Expression");
                    e2.setSelection(e2.getText().length());
                    e1.setText("");
                    expression = "";
                    e.printStackTrace();
                }
                break;

            case R.id.openBracket:
                e2.setText(e2.getText() + "(");
                e2.setSelection(e2.getText().length());
                break;

            case R.id.closeBracket:
                e2.setText(e2.getText() + ")");
                e2.setSelection(e2.getText().length());
                break;


        }
    }
    private void operationClicked(String op) {
        if (e2.length() != 0) {
            String text = e2.getText().toString();
            e1.setText(e1.getText() + text + op);
            e1.setSelection(e1.getText().length());
            e2.setText("");
            count = 0;
        } else {
            String text = e1.getText().toString();
            if (text.length() > 0) {
                String newText = text.substring(0, text.length() - 1) + op;
                e1.setText(newText);
                e1.setSelection(e1.getText().length());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TEST){

        }if(resultCode==123){
            String value=data.getExtras().getString("result");
            expression = data.getExtras().getString("expression");
            e1.setText(value);



            Log.d(getClass().getName(), "@@@@@@@@@@@@@@@@@@@@@"+expression);

            /*if(value.contains("°")){
                expression = value.replace("°","/57.29577951308232");
                Log.d(getClass().getName(), "@@@@@@@@@@@@@degree 들어왓다"+expression);

            }
            if(value.contains("pi")){
                String [] arr = value.split("pi");
                value ="";
                for(int i=0; i<arr.length;i++){
                    char c = arr[i].charAt(arr[i].indexOf("p")-1);
                    switch(c){
                        case '0': case '1' : case '2': case'3': case'4':case '5': case'6': case'7': case '8': case'9':

                            arr[i]=arr[i].replace("pi","*3.141592653589793");
                            break;
                        default:
                            arr[i]=arr[i].replace("pi","1*3.141592653589793");
                            break;
                    }
                    value = value + arr[i];
                }
                expression= value.replace("pi", "/57.29577951308232");
            }*/

            //expression = value;

        }
    }
}
