package com.example.turn.Activity.Main.Fragment.FragmentReserve.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Model.ModResTime;
import com.example.turn.R;

import java.util.List;

public class AdRecycResTimes extends RecyclerView.Adapter<AdRecycResTimes.ViewHolder> {

    private Context context;
    private List<ModResTime> data;
    private int lastPosition = -1;
    com.example.turn.Activity.Main.Adapter.onClickInterface onClickInterface;

    public AdRecycResTimes(Context context, List<ModResTime> data, onClickInterface onClickInterface) {
        this.context = context;
        this.data = data;
        this.onClickInterface = onClickInterface;
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
            hospitalName = hospitalName.replace("بیمارستان", "");
            holder.txtRcycRT_hospital.setText("بیمارستان " + hospitalName);
            holder.txtRcycRT_shift.setText("شیفت " + data.get(position).shift_title);
            holder.txtRcycRT_doctorName.setText("دکتر " + data.get(position).dr_name);
            holder.txtRcycRT_takhasos.setText("تخصص: " + data.get(position).spc_title);
            holder.txtRcycRT_date.setText("تاریخ: " + data.get(position).prg_date);
            //  holder.txtRcycRT_num.setText("اینترنتی: " + data.get(position).web_turn);

            if (data.get(position).web_turn.equals("0"))
                holder.btnRcycRT_status.setBackground(context.getResources().getDrawable(R.drawable.button_background_red));
            else
                holder.btnRcycRT_status.setBackground(context.getResources().getDrawable(R.drawable.button_background_green));
            holder.btnRcycRT_status.setText("" + data.get(position).status_type);


            holder.btnRcycRT_status.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));


            holder.linearMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.get(position).web_turn.equals("0"))
                        onClickInterface.setClick(position, false, holder.itemView);
                    else
                        onClickInterface.setClick(position, true, holder.itemView);
                }
            });

            holder.btnRcycRT_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.get(position).web_turn.equals("0"))
                        onClickInterface.setClick(position, false, holder.itemView);
                    else
                        onClickInterface.setClick(position, true, holder.itemView);
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

        LinearLayout linearMain;
        TextView txtRcycRT_hospital;
        TextView txtRcycRT_shift;
        TextView txtRcycRT_date;
        TextView txtRcycRT_doctorName;
        TextView txtRcycRT_takhasos;
        // TextView txtRcycRT_num;
        LinearLayout linearRcycRT_status;
        Button btnRcycRT_status;

        ViewHolder(View view) {
            super(view);

            linearMain = view.findViewById(R.id.linearMain);
            txtRcycRT_hospital = view.findViewById(R.id.txtRcycRT_hospital);
            txtRcycRT_shift = view.findViewById(R.id.txtRcycRT_shift);
            txtRcycRT_doctorName = view.findViewById(R.id.txtRcycRT_doctorName);
            txtRcycRT_takhasos = view.findViewById(R.id.txtRcycRT_takhasos);
            txtRcycRT_date = view.findViewById(R.id.txtRcycRT_date);
            //   txtRcycRT_num = view.findViewById(R.id.txtRcycRT_num);
            //linearRcycRT_status = view.findViewById(R.id.linearRcycRT_status);
            btnRcycRT_status = view.findViewById(R.id.btnRcycRT_status);

        }
    }


}