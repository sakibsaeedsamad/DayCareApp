package com.example.daycareapp.listeners;

import android.widget.TextView;

import com.example.daycareapp.models.Order;

public interface OrderClickListener {
    void onClick(Order project);
    void onLongClick(Order project, TextView textView);
}
