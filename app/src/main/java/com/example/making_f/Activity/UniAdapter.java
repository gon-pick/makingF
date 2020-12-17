package com.example.making_f.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.making_f.R;
import java.util.ArrayList;


public class UniAdapter extends RecyclerView.Adapter<UniAdapter.CustomViewHolder>{

    private ArrayList<UniSample> UniSamples;

    public UniAdapter(ArrayList<UniSample> uniSamples) {
        this.UniSamples = uniSamples;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.Uniseparato.setText(UniSamples.get(position).getUniSeparators());
        holder.Location.setText(UniSamples.get(position).getLocation());
        holder.University.setText(UniSamples.get(position).getUniversity());
        holder.Department.setText(UniSamples.get(position).getDepartment());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String University = holder.University.getText().toString();
                String Deppart = holder.Department.getText().toString();
                Toast.makeText(v.getContext(),"대학:"+University+", 학과:"+Deppart,Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != UniSamples ? UniSamples.size() : 0);
    }

    public void remove(int position){
        try{
            UniSamples.remove(position);
            notifyItemRemoved(position); //새로 고침
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView Uniseparato;
        protected TextView Location;
        protected TextView University;
        protected TextView Department;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.Uniseparato = (TextView) itemView.findViewById(R.id.Uniseparator);
            this.Location= (TextView) itemView.findViewById(R.id.Location);
            this.University= (TextView) itemView.findViewById(R.id.University);
            this.Department= (TextView) itemView.findViewById(R.id.Department);
        }
    }
}
