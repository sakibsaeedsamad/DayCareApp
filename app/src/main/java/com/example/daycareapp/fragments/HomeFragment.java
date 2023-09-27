package com.example.daycareapp.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daycareapp.R;
import com.example.daycareapp.activities.AddCaregiverActivity;
import com.example.daycareapp.listeners.DeleteCaregiverListener;
import com.example.daycareapp.models.UserRole;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.activities.FeeActivity;
import com.example.daycareapp.adapters.CaregiverListAdapter;
import com.example.daycareapp.listeners.CaregiverClickListener;
import com.example.daycareapp.models.Caregiver;
import com.example.daycareapp.network.response.AllCaregiverResponse;
import com.example.daycareapp.network.response.DefaultResponse;
import com.example.daycareapp.util.SharedRefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CaregiverListAdapter caregiverListAdapter;
    List<Caregiver> caregiverList;
    private SharedRefs sharedRefs;
    private Dialog dialog;
    private Button filterButton, addCaregiver;



    public HomeFragment(Context context) {
        sharedRefs = new SharedRefs(context);
    }

    public static HomeFragment newInstance(Context context) {
        return new HomeFragment(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        caregiverList = new ArrayList<>();

        getCareGiverList();

        dialog = new Dialog(getContext());
//        caregiverList.add(new Caregiver("Rahima Khatun", "Mirpur", "img1"));
//        caregiverList.add(new Caregiver("Hafsa Banu", "Savar", "img2"));
//        caregiverList.add(new Caregiver("Mahmuda Akter", "Dhanmondi", "img3"));
//        caregiverList.add(new Caregiver("Fariha Akter", "New Market", "img4"));
//        caregiverList.add(new Caregiver("Kulsum Akter", "New Market", "img2"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        filterButton = view.findViewById(R.id.filterButtonId);
        addCaregiver = view.findViewById(R.id.addCaregiver);
        sharedRefs = new SharedRefs(getContext());
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForFilterDialog();
            }
        });
        if(sharedRefs.getString(SharedRefs.USER_ROLE, UserRole.ROLE_USER.toString()).equals(UserRole.ADMIN.toString())){
            addCaregiver.setVisibility(View.VISIBLE);
        }
        addCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCaregiverActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        updateProjectsRecycler();
    }

    private final CaregiverClickListener caregiverClickListener = new CaregiverClickListener() {
        @Override
        public void onClick(Caregiver caregiver) {
            if(!caregiver.getAvailable()){
                callForAvailableCaregiver("Sorry, this caregiver is not available right now!");
                return;
            }else{
                Intent intent = new Intent(getContext(), FeeActivity.class);
                intent.putExtra("caregiver_id", caregiver.getId());
                intent.putExtra("name", caregiver.getCaregiverMotherName());
                intent.putExtra("location", caregiver.getAddress());
                intent.putExtra("speciality", caregiver.getSpeciality());
                intent.putExtra("feedback", caregiver.getAdminFeedBack());
                intent.putExtra("isAvailable", caregiver.getAvailable());
                intent.putExtra("img", "img1");
                startActivity(intent);
            }

//            getActivity().finish();
        }

        @Override
        public void onLongClick(Caregiver caregiver, TextView textView) {
//            callForDialog(caregiver.getCaregiverMotherName());
            Toast.makeText(getContext(), "long selected: " + caregiver.getCaregiverMotherName(), Toast.LENGTH_SHORT).show();
        }
    };

    private final DeleteCaregiverListener deleteCaregiverListener = new DeleteCaregiverListener(){
        @Override
        public void onClick(Caregiver caregiver){
            deleteCareGiver(caregiver);
        }
    };

    private void deleteCareGiver(Caregiver caregiver) {
        {
//        progressBar.setVisibility(View.VISIBLE);
            Call<DefaultResponse> call = RetrofitAPIClient
                    .getInstance()
                    .getAPI()
                    .deleteCaregiver(caregiver.getId());

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        DefaultResponse defaultResponse = response.body();
                        Toast.makeText(getContext(), defaultResponse.getMessage() + ", Status: " + defaultResponse.getStatus(), Toast.LENGTH_LONG).show();
                        getCareGiverList();
                    } else {
//                    progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateProjectsRecycler() {
        recyclerView = getView().findViewById(R.id.projectsRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        caregiverListAdapter = new CaregiverListAdapter(getContext(), caregiverList, caregiverClickListener, deleteCaregiverListener);
        recyclerView.setAdapter(caregiverListAdapter);
    }

    private void callForDialog(String dialogStringData){
        dialog.setContentView(R.layout.sample_info_layout);
        TextView cancell = dialog.findViewById(R.id.cancelId);
        TextView dialogText = dialog.findViewById(R.id.dialogTextId);
        dialogText.setText(dialogStringData);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void callForAvailableCaregiver(String dialogStringData){
        dialog.setContentView(R.layout.sample_alert_dialog);
        TextView cancell = dialog.findViewById(R.id.cancelId);
        TextView dialogText = dialog.findViewById(R.id.dialogTextId);
        dialogText.setText(dialogStringData);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void callForFilterDialog(){
        dialog.setContentView(R.layout.sample_filter_layout);
        TextView cancell = dialog.findViewById(R.id.cancelId);
        Button startDatebt = dialog.findViewById(R.id.startDateBtId);
        startDatebt.setText("Start Date");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callForDialog("I am hello");
                dialog.cancel();
            }
        });

        startDatebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForTimePickerDiolog();
            }
        });
        dialog.show();
    }

    public void callForTimePickerDiolog(){
        int mMinute = 0, mHour = 0;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void getCareGiverList() {
//        progressBar.setVisibility(View.VISIBLE);
        Call<AllCaregiverResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .findAllCaregiver();

        call.enqueue(new Callback<AllCaregiverResponse>() {
            @Override
            public void onResponse(Call<AllCaregiverResponse> call, Response<AllCaregiverResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    String message = response.body().getStatus();
                    Toast.makeText(getActivity(), "Successfully got data! status: "+message, Toast.LENGTH_LONG).show();
                    caregiverList = response.body().getCaregivers();

                    updateProjectsRecycler();


                } else {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllCaregiverResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}