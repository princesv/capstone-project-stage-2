package com.prince.teamaveonracing;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference membersReference = firebaseDatabase.getReference("Team Members");
        List<TeamMember> teamMembers = new ArrayList<>();
        Context mContext;
        @Override
        public void onCreate() {
        }



        public ListRemoteViewsFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public void onDataSetChanged() {
            membersReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot memberSnapshot: snapshot.getChildren()){
                        TeamMember member = memberSnapshot.getValue(TeamMember.class);
                        teamMembers.add(member);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return  teamMembers.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.contact_card_layout);
            views.setTextViewText(R.id.name,teamMembers.get(position).getName());
            views.setTextViewText(R.id.year,teamMembers.get(position).getYear());
            views.setTextViewText(R.id.subteam,teamMembers.get(position).getSubteam());
            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}


