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

import com.example.turn.Activity.Main.Model.ModResTime;
import com.example.turn.R;

import java.util.List;

public class AdRecycResTimes extends RecyclerView.Adapter<AdRecycResTimes.ViewHolder> {

    private Context context;
    private List<ModResTime> data;
    private int lastPosition = -1;

    public AdRecycResTimes(Context context, List<ModResTime> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcyc_restimes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {

            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;

            String hospitalName = data.get(position).hsp_title;
            hospitalName.replace("بیمارستان", "");
            hospitalName.replace("بیمارستان", "");
            holder.txtRcycRT_hospital.setText("بیمارستان " + hospitalName);
            holder.txtRcycRT_shift.setText("شیفت " + data.get(position).shift_title);
            holder.txtRcycRT_doctorName.setText("دکتر " + data.get(position).dr_name);
            holder.txtRcycRT_takhasos.setText("تخصص " + data.get(position).spc_title);
            holder.txtRcycRT_num.setText("اینترنتی: " + data.get(position).web_turn);



            if ( data.get(position).web_turn.equals("0")) {
                holder.linearRcycRT_status.setBackgroundColor(context.getResources().getColor(R.color.colorFinish));
                holder.txtRcycRT_status.setText("وضعیت: " + data.get(position).status_type);
            }else {
                holder.linearRcycRT_status.setBackgroundColor(context.getResources().getColor(R.color.colorNoFinish));
                holder.txtRcycRT_status.setText("وضعیت: " + data.get(position).status_type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearMain;
        TextView txtRcycRT_hospital;
        TextView txtRcycRT_shift;
        TextView txtRcycRT_doctorName;
        TextView txtRcycRT_takhasos;
        TextView txtRcycRT_num;
        LinearLayout linearRcycRT_status;
        TextView txtRcycRT_status;

        ViewHolder(View view) {
            super(view);

            linearMain = view.findViewById(R.id.linearMain);
            txtRcycRT_hospital = view.findViewById(R.id.txtRcycRT_hospital);
            txtRcycRT_shift = view.findViewById(R.id.txtRcycRT_shift);
            txtRcycRT_doctorName = view.findViewById(R.id.txtRcycRT_doctorName);
            txtRcycRT_takhasos = view.findViewById(R.id.txtRcycRT_takhasos);
            txtRcycRT_num = view.findViewById(R.id.txtRcycRT_num);
            linearRcycRT_status = view.findViewById(R.id.linearRcycRT_status);
            txtRcycRT_status = view.findViewById(R.id.txtRcycRT_status);

        }
    }
}