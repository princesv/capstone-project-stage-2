package com.prince.teamaveonracing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity{

    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREF="sharedPrefs";
    public static final String UID="userId";
    public static final String PASSWORD="password";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference membersReference = firebaseDatabase.getReference("Team Members");
    List<TeamMember> teamMembers;
    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        contactList = findViewById(R.id.contact_list);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sharedPreferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        mAuth =FirebaseAuth.getInstance();
        teamMembers = new ArrayList<>();
       membersReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
             //  teamMembers.clear();
               for(DataSnapshot memberSnapshot: snapshot.getChildren()){
                   TeamMember member = memberSnapshot.getValue(TeamMember.class);
                   teamMembers.add(member);
               }
               ContactListAdapter adapter = new ContactListAdapter(teamMembers,ContactActivity.this);
               contactList.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
       contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(teamMembers.get(position).getContact()));
               startActivity(intent);
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
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                editor.putString(UID," ");
                editor.putString(PASSWORD," ");
                editor.commit();
                Intent intent=new Intent(ContactActivity.this,SigninActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return (true);

        }
        return true;
    }
}
