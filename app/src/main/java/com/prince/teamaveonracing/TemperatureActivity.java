package com.prince.teamaveonracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TemperatureActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    float mYCoordinate;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    LineChart lineChart;
    boolean flag_run_task = true;
    private Handler mHandler = new Handler();
    ArrayList<Entry> dynamicDataEntry = new ArrayList<>();
    TextView temperatureValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
       lineChart = findViewById(R.id.line_chart_view);
       temperatureValue = findViewById(R.id.temperature_value);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("Temperature Value");
        performStuff();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataPoint dataPoint = dataSnapshot.getValue(DataPoint.class);
                    mYCoordinate = dataPoint.getyCoordinate();
                    temperatureValue.setText(String.valueOf(mYCoordinate)+"C");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    void showChart(ArrayList<Entry> dataVals){
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("Battery Temperature");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
    private void performStuff(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(flag_run_task){
                    final int xCoordinate = i;
                    if(!flag_run_task) break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            dynamicDataEntry.add(new Entry(xCoordinate,mYCoordinate));
                            if(xCoordinate>10){
                                dynamicDataEntry.remove(0);
                            }
                            showChart(dynamicDataEntry);

                        }
                    });
                    i++;
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag_run_task = false;
    }
}
//implementation 'com.github.PhilJay:MPAndroidChart:v3.0.2'
