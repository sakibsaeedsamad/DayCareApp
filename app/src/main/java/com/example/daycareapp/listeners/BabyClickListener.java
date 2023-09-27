package com.example.daycareapp.listeners;

import android.widget.TextView;

import com.example.daycareapp.models.Baby;

public interface BabyClickListener {
    void onClick(Baby project);
    void onLongClick(Baby project, TextView textView);
}
