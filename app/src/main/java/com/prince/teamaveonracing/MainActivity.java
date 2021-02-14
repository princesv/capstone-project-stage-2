package com.prince.teamaveonracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference temperatureReference = firebaseDatabase.getReference("Temperature Value");
    DatabaseReference locationReference = firebaseDatabase.getReference("Location");
    DatabaseReference membersReference = firebaseDatabase.getReference("Team Members");
    DatabaseReference activityDetailReference = firebaseDatabase.getReference("Main Activity");

    TextView clubDescription;
    MainScreenDetails details;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clubDescription = findViewById(R.id.article_body);
        activityDetailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                details = new MainScreenDetails();
                details = snapshot.getValue(MainScreenDetails.class);
                clubDescription.setText(details.getContent());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.logout:
            //add the function to perform here
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    public void openTemperatureActivity(View view){
        Intent intent = new Intent(MainActivity.this,TemperatureActivity.class);
        startActivity(intent);
    }
    public void openContactActivity(View view){
        Intent intent= new Intent(MainActivity.this,ContactActivity.class);
        startActivity(intent);
    }
    public void openMapstActivity(View view){
        Intent intent= new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}
