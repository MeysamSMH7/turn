package com.example.turn;

import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Model.ModAlerts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAlertDialog extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    Context context;
    View view;
    AlertDialog alert;
    AdRecycPopUp123 adRecycPopUp123;

    public SearchAlertDialog(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void SearchAlertDialog_ArrayList(ArrayList<ModAlerts123> arrayList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LinearLayout layout = new LinearLayout(view.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        Button button = new Button(view.getContext());
        button.setText("My Button");
        button.setWidth(100);
        button.setHeight(50);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });




        layout.addView(button);

        builder.setView(layout);
        alert = builder.create();
        alert.show();

    }

    public static class ModAlerts123 {
        private String titleName;
        private String id;


        public ModAlerts123(String titleName, String id) {
            this.titleName = titleName;
            this.id = id;
        }

        public String getTitle() {
            return this.titleName;
        }

        public String getId() {
            return this.id;
        }

    }

    public interface onClickInterface123 {
        void setClick(int position, boolean canUse, View view);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adRecycPopUp123.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    class AdRecycPopUp123 extends RecyclerView.Adapter<AdRecycPopUp123.ViewHolder> {

        private Context context;
        private int lastPosition = -1;
        private SearchAlertDialog.onClickInterface123 onClickInterface;
        private ArrayList<ModAlerts> arraylist;
        private List<ModAlerts> data;

        public AdRecycPopUp123(Context context, List<ModAlerts> data, SearchAlertDialog.onClickInterface123 onClickInterface) {
            this.context = context;
            this.data = data;
            this.onClickInterface = onClickInterface;
            this.arraylist = new ArrayList<ModAlerts>();
            this.arraylist.addAll(data);
        }

        @NonNull
        @Override
        public AdRecycPopUp123.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_listview, parent, false);


//            android.R.layout.simple_list_item_1

            return new AdRecycPopUp123.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final AdRecycPopUp123.ViewHolder holder, final int position) {
            try {

                Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
                holder.itemView.startAnimation(animation);
                lastPosition = position;

                holder.txtTitle.setText(data.get(position).getTitle() + "");
                holder.txtId.setText(data.get(position).getId() + "");

                holder.txtTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

}
