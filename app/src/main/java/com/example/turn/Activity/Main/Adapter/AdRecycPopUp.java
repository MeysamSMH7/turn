package com.example.turn.Activity.Main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdRecycPopUp extends RecyclerView.Adapter<AdRecycPopUp.ViewHolder> {

    private Context context;
    private List<ModAlerts> data;
    private int lastPosition = -1;
    private com.example.turn.Activity.Main.Adapter.onClickInterface onClickInterface;
    private ArrayList<ModAlerts> arraylist;

    public AdRecycPopUp(Context context, List<ModAlerts> data, onClickInterface onClickInterface) {
        this.context = context;
        this.data = data;
        this.onClickInterface = onClickInterface;
        this.arraylist = new ArrayList<ModAlerts>();
        this.arraylist.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {

            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;

            holder.txtTitle.setText(data.get(position).getTitle() + "");
            holder.txtId.setText(data.get(position).getId() + "");

            holder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickInterface.setClick(position, true,holder.itemView);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutMain;
        TextView txtTitle;
        TextView txtId;

        ViewHolder(View view) {
            super(view);

            layoutMain = view.findViewById(R.id.linearMain);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtId = view.findViewById(R.id.txtId);

        }
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(arraylist);
        } else {
            for (ModAlerts wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}