package com.example.turn.Activity.Main.Fragment.FragmentEstelam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Fragment.FragmentEstelam.Model.ModEstelam;
import com.example.turn.R;

import java.util.List;

public class AdRecycEstelam extends RecyclerView.Adapter<AdRecycEstelam.ViewHolder> {

    private Context context;
    private List<ModEstelam> data;
    private int lastPosition = -1;
    private com.example.turn.Activity.Main.Adapter.onClickInterface onClickInterface;

    public AdRecycEstelam(Context context, List<ModEstelam> data, onClickInterface onClickInterface) {
        this.context = context;
        this.data = data;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcyc_estelam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {

            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;

            holder.txtEstRecy_drname.setText("دکتر " + data.get(position).dr_name);
            holder.txtEstRecy_takhasos.setText("تخصص: " + data.get(position).spc_title);
            holder.txtEstRecy_date.setText("تاریخ: " + data.get(position).date_string);
            holder.txtEstRecy_typeRes.setText("نحوه ی دریافت نوبت: " + data.get(position).status_title);
            holder.txtEstRecy_typeRes.setText("وضعیت نوبت: " + data.get(position).status_detail);

            holder.btnEstRecy_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickInterface.setClick(position, true);
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
        TextView txtEstRecy_date;
        TextView txtEstRecy_drname;
        TextView txtEstRecy_takhasos;
        TextView txtEstRecy_typeRes;
        Button btnEstRecy_status;

        ViewHolder(View view) {
            super(view);

            linearMain = view.findViewById(R.id.linearMain);
            txtEstRecy_drname = view.findViewById(R.id.txtEstRecy_drname);
            txtEstRecy_takhasos = view.findViewById(R.id.txtEstRecy_takhasos);
            txtEstRecy_date = view.findViewById(R.id.txtEstRecy_date);
            txtEstRecy_typeRes = view.findViewById(R.id.txtEstRecy_typeRes);
            btnEstRecy_status = view.findViewById(R.id.btnEstRecy_status);

        }
    }


}