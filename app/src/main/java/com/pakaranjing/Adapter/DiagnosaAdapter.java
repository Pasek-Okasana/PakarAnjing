package com.pakaranjing.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pakaranjing.Model.Gejala;
import com.pakaranjing.R;

import java.util.ArrayList;
import java.util.List;

public class DiagnosaAdapter extends RecyclerView.Adapter<DiagnosaAdapter.DiagnosaViewHolder> {
    private ArrayList<Gejala> mList;

    public DiagnosaAdapter(ArrayList<Gejala> mList) {
        this.mList = new ArrayList<Gejala>();
        this.mList.addAll(mList);
    }

    @NonNull
    @Override
    public DiagnosaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnosa_list, parent, false);
        return new DiagnosaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosaViewHolder holder, int position) {
        Gejala gejala = mList.get(position);
        holder.name.setText(gejala.getName());
//        holder.name.setChecked(gejala.isSelected());
        holder.name.setTag(gejala);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Gejala getItemPosition(int position){
        return mList.get(position);
    }

    public class DiagnosaViewHolder extends RecyclerView.ViewHolder {
        CheckBox name;
        public DiagnosaViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chk_gejala);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        CheckBox cb = (CheckBox) v;
                        Gejala gejala = (Gejala) cb.getTag();
                        gejala.setSelected(cb.isChecked());
                        name.setChecked(gejala.isSelected());
                    }
                }
            });
        }
    }
}
