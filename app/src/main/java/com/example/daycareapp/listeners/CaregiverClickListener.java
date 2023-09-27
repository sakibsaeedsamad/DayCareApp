package com.example.daycareapp.listeners;

import android.widget.TextView;

import com.example.daycareapp.models.Caregiver;

public interface CaregiverClickListener {
    void onClick(Caregiver project);
    void onLongClick(Caregiver project, TextView textView);
}
