package com.example.daycareapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daycareapp.R;
import com.example.daycareapp.configs.Config;
import com.example.daycareapp.listeners.CaregiverClickListener;
import com.example.daycareapp.listeners.DeleteCaregiverListener;
import com.example.daycareapp.models.Caregiver;
import com.example.daycareapp.models.UserRole;
import com.example.daycareapp.util.SharedRefs;

import java.util.List;

public class CaregiverListAdapter extends RecyclerView.Adapter<CaregiverListViewHolder> {
    private final Context context;
    private final List<Caregiver> caregiverList;
    private final CaregiverClickListener caregiverClickListener;
    private final DeleteCaregiverListener deleteCaregiverListener;
    SharedRefs sharedRefs;

    public CaregiverListAdapter(Context context, List<Caregiver> caregiverList, CaregiverClickListener caregiverClickListener, DeleteCaregiverListener deleteCaregiverListener) {
        this.context = context;
        this.caregiverList = caregiverList;
        this.caregiverClickListener = caregiverClickListener;
        this.deleteCaregiverListener = deleteCaregiverListener;
    }

    @NonNull
    @Override
    public CaregiverListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caregiver_list_layout, parent, false);
        sharedRefs = new SharedRefs(context);

        return new CaregiverListViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CaregiverListViewHolder holder, int position) {
        Caregiver caregiver = caregiverList.get(position);
        holder.setData(caregiver);

        if(sharedRefs.getString(SharedRefs.USER_ROLE, UserRole.ROLE_USER.toString()).equals(UserRole.ADMIN.toString())){
            holder.deleteCaregiver.setVisibility(View.VISIBLE);
        }
        holder.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caregiverClickListener.onClick(caregiver);
            }
        });

        holder.deleteCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCaregiverListener.onClick(caregiver);
            }
        });

        holder.caregiverNameTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                caregiverClickListener.onLongClick(caregiver, holder.caregiverNameTv);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return caregiverList.size();
    }

}

class CaregiverListViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    public TextView caregiverNameTv;
    public TextView caregiverLocationTv;
    public ImageView caregiverImageView;
    public Button deleteCaregiver;
    public Button bookNow;
    public CaregiverListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        caregiverNameTv = itemView.findViewById(R.id.caregiverName);
        caregiverLocationTv = itemView.findViewById(R.id.caregiverLocation);
        caregiverImageView = itemView.findViewById(R.id.caregiverImage);
        bookNow = itemView.findViewById(R.id.bookNowBtId);
        deleteCaregiver = itemView.findViewById(R.id.deleteCaregiverBtId);
    }

    public void setData(Caregiver caregiver) {
        caregiverNameTv.setText(caregiver.getCaregiverMotherName());
        caregiverLocationTv.setText(caregiver.getAddress());
        String PACKAGE_NAME = context.getPackageName();
        int imageId = context.getResources().getIdentifier(PACKAGE_NAME + ":drawable/img" + caregiver.getId(), null, null);
        caregiverImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), imageId));
    }
}