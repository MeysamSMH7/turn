package com.example.turn.Activity.Main.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<ModAlerts> modAlertsList = null;
    private ArrayList<ModAlerts> arraylist;

    public ListViewAdapter(Context context, List<ModAlerts> modAlertsList) {
        mContext = context;
        this.modAlertsList = modAlertsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ModAlerts>();
        this.arraylist.addAll(modAlertsList);
    }

    public class ViewHolder {
        TextView txtTitle;
        TextView txtId;
    }

    @Override
    public int getCount() {
        return modAlertsList.size();
    }

    @Override
    public ModAlerts getItem(int position) {
        return modAlertsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_list_listview, null);
            // Locate the TextViews in listview_item.xml
            holder.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            holder.txtId = (TextView) view.findViewById(R.id.txtId);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.txtTitle.setText(modAlertsList.get(position).getTitle());
        holder.txtId.setText(modAlertsList.get(position).getId());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modAlertsList.clear();
        if (charText.length() == 0) {
            modAlertsList.addAll(arraylist);
        } else {
            for (ModAlerts wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    modAlertsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
