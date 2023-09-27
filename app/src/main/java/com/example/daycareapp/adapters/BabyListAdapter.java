package com.example.daycareapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daycareapp.R;
import com.example.daycareapp.listeners.BabyClickListener;
import com.example.daycareapp.listeners.DeleteBabyClickListener;
import com.example.daycareapp.models.Baby;

import java.util.List;

public class BabyListAdapter extends RecyclerView.Adapter<BabyListViewHolder> {
    private final Context context;
    private final List<Baby> babyList;
    private final BabyClickListener babyClickListener;
    private final DeleteBabyClickListener deleteBabyListener;

    public BabyListAdapter(Context context, List<Baby> babyList, BabyClickListener babyClickListener, DeleteBabyClickListener deleteBabyListener) {
        this.context = context;
        this.babyList = babyList;
        this.babyClickListener = babyClickListener;
        this.deleteBabyListener = deleteBabyListener;
    }

    @NonNull
    @Override
    public BabyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.baby_list_layout, parent, false);
        return new BabyListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BabyListViewHolder holder, int position) {
        Baby baby = babyList.get(position);
        holder.setData(baby);
        holder.babyListLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                babyClickListener.onClick(baby);
            }
        });

        holder.babyListLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                babyClickListener.onLongClick(baby, holder.babyNameTv);
                return true;
            }
        });

        holder.deleteBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBabyListener.onClick(baby);
            }
        });
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

}

class BabyListViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    public TextView babyNameTv;
    public TextView babyLocationTv;
    public ImageView babyImageView;
    public Button deleteBaby;
    public LinearLayout babyListLinearLayout;
    public TextView babyMotherName;
    public TextView babyFatherName;
    public TextView babyAge;
    public TextView babyFeforiteFood;
    public TextView babyIsChecked;
    public  TextView babyContact;
    public BabyListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        babyNameTv = itemView.findViewById(R.id.babyName);
        babyLocationTv = itemView.findViewById(R.id.babyLocation);
        babyImageView = itemView.findViewById(R.id.babyImage);
        deleteBaby = itemView.findViewById(R.id.deleteBaby);
        babyListLinearLayout = itemView.findViewById(R.id.babyListLinearLayoutId);
        babyMotherName = itemView.findViewById(R.id.babyMotherName);
        babyFatherName = itemView.findViewById(R.id.babyFatherName);
        babyAge = itemView.findViewById(R.id.babyAge);
        babyFeforiteFood = itemView.findViewById(R.id.babyFevoriteFood);
        babyIsChecked = itemView.findViewById(R.id.babyIsChecked);
        babyContact = itemView.findViewById(R.id.babyContactNumber);
    }

    public void setData(Baby baby) {
        babyNameTv.setText("Baby Name: "+baby.getBabyName());
        babyLocationTv.setText("Address: " + baby.getAddress());
        babyMotherName.setText("Mother Name: " + baby.getMotherName());
        babyFatherName.setText("Father Name: " + baby.getFatherName());
        babyAge.setText("Age: " + baby.getBabyAge());
        babyFeforiteFood.setText("Fevorite Food: " + baby.getFevoriteFood());
        if(baby.getIsSicked()){
            babyIsChecked.setText("Sicked");
        }else{
            babyIsChecked.setText("Not Sicked");
        }
        babyContact.setText("Contact Number: " + baby.getContactNumber());

        String PACKAGE_NAME = context.getPackageName();
        int imageId = context.getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + "img4", null, null);
        babyImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), imageId));
    }
}