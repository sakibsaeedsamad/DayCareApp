package com.example.daycareapp.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.daycareapp.R;
import com.example.daycareapp.models.Review;

import java.util.List;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ViewHolder>{
    private List<Review> listdata;

    // RecyclerView recyclerView;
    public ReviewsListAdapter(List<Review> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.review_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Review myListData = listdata.get(position);
        holder.reviewerNameTextView.setText(listdata.get(position).getTitle());
//        holder.imageView.setImageResource(listdata.get(position).getImgId());
        holder.reviewTextView.setText(listdata.get(position).getBody());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Hey, you clicked on : "+myListData.getTitle() + ",  " + getItemCount(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if(listdata==null){
            return 0;
        }
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView reviewerNameTextView;
        public TextView reviewTextView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.reviewerImageId);
            this.reviewerNameTextView = (TextView) itemView.findViewById(R.id.reviewersNameId);
            this.reviewTextView = itemView.findViewById(R.id.reviewId);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.review_list_layout_id);
        }
    }
}

