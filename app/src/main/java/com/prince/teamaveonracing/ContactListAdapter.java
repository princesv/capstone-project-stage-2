package com.prince.teamaveonracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    List<TeamMember> teamMembers;
    Context context;

    public ContactListAdapter(List<TeamMember> teamMembers, Context context){
        this.context = context;
        this.teamMembers = teamMembers;
    }

    @Override
    public int getCount() {
        return teamMembers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.contact_card_layout,parent,false);
            ((TextView)convertView.findViewById(R.id.name)).setText(teamMembers.get(position).getName());
            ((TextView)convertView.findViewById(R.id.year)).setText(String.valueOf(teamMembers.get(position).getYear()));
            ((TextView)convertView.findViewById(R.id.subteam)).setText(teamMembers.get(position).getSubteam());
        return convertView;
    }

}
