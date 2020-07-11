package com.example.drawer5.Calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drawer5.MainActivity;
import com.example.drawer5.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction ft ;


    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;
    Button button;
    String expression="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_camera);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.capture);

        /*button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                intent.putExtra("result",textView.getText().toString());
                intent.putExtra("expression",expression);
                setResult(123,intent);

                finish();
            }
        });*/



        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational()) {
            Toast.makeText(this, "Not Available!", Toast.LENGTH_SHORT).show();
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setAutoFocusEnabled(true)
                    .build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });
        }
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if(items.size() !=0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for(int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                //stringBuilder.append("\n");
                            }
                            String value=stringBuilder.toString();
                            String newValue = "";

                            value=value.replaceAll("7t","pi");
                            value=value.replaceAll("tt","pi");
                            value=value.replaceAll("A","^");
                            value=value.replaceAll("x","*");
                            value=value.replaceAll("X","*");
                            expression=value;
                            if(value.toLowerCase().contains("cos") || value.toLowerCase().contains("sin") || value.toLowerCase().contains("tan")){
                                value = value.toLowerCase();
                                value = value.replaceAll(" ","");
                                value = value.replaceAll("\\+", " + ");
                                value = value.replaceAll("-", " - ");
                                value = value.replaceAll("x", " * ");
                                value = value.replaceAll("÷", " / ");
                                value = value.replaceAll("\\(", " ( ");
                                value = value.replaceAll("\\)", " ) ");

                                String [] arr = value.split(" ");

                                for(int i=0; i<arr.length; i++){
                                    if((arr[i].contains("cos")) || (arr[i].contains("tan")) || (arr[i].contains("sin"))){
                                        String temp = arr[i];
                                        temp = temp.substring(3);
                                        arr[i]=arr[i].substring(0,3);
                                        arr[i]= arr[i]+"("+temp+")";
                                    }
                                        newValue = newValue+arr[i];

                                }
                                value=newValue;
                                expression = value;
                            }
                            textView.setText(value);
                        }
                    });
                }
            }

        });
    }

    public void onClick(View v){
        String value =textView.getText().toString();

        if(value.toLowerCase().contains("cos") || value.toLowerCase().contains("sin") || value.toLowerCase().contains("tan")){
            Log.d(getClass().getName(), "@@@@@@@@@@@@@@!코사인 잇기는 한데");
            value = value.toLowerCase();
            value = value.replaceAll(" ","");
            value = value.replaceAll("\\+", " + ");
            value = value.replaceAll("-", " - ");
            value = value.replaceAll("x", " * ");
            value = value.replaceAll("÷", " / ");


            expression ="";
            String [] arr = value.split(" ");

            for(int i=0; i<arr.length; i++){
                Log.d(getClass().getName(), "1st for  arr[i] : "+arr[i]);

                String temp2="";
                if((arr[i].contains("cos")) || (arr[i].contains("tan")) || (arr[i].contains("sin"))){
                    String temp = arr[i];
                    temp = temp.substring(3);
                    arr[i]=arr[i].substring(0,3);
                    Log.d(getClass().getName(), "2nd if arr[i] : "+arr[i]);
                    Log.d(getClass().getName(), "2nd if temp : "+temp);

                    if(temp.contains("°")){
                        temp2=temp;
                        temp2 =  Double.parseDouble(temp2.substring(1,temp2.indexOf('°')))/57.29577951308232 +"";
                        temp2 = arr[i]+"("+temp2+")";
                    }else if(temp.contains("pi")){
                        temp2=temp;
                        temp2 = Integer.parseInt(temp2.substring(1,temp2.indexOf('p')))*3.141592653589793 +"";
                        temp2 = arr[i] + "("+temp2+")";
                    }else{
                        arr[i]=arr[i]+"("+temp+")";
                        Log.d(getClass().getName(), temp);
                    }
                }
                if(!temp2.equals("")){
                    expression = expression + temp2;
                    Log.d(getClass().getName(), "@@@@@@@@@@@@@@!코사인 잇기는 한데~~~~~~~" + expression);
                }else{
                    expression = expression +arr[i];
                    Log.d(getClass().getName(), "@@@@@@@@@@@@@@!" + expression);
                }

            }
        }


        Intent intent=new Intent();
        intent.putExtra("result",textView.getText().toString());
        intent.putExtra("expression",expression);
        setResult(123,intent);

        finish();
    }
}
