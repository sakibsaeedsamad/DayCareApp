package com.example.daycareapp.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daycareapp.R;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.activities.FeeActivity;
import com.example.daycareapp.adapters.OrderListAdapter;
import com.example.daycareapp.listeners.OrderClickListener;
import com.example.daycareapp.models.Order;
import com.example.daycareapp.network.response.AllOrderResponse;
import com.example.daycareapp.util.SharedRefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private String demoMessage;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    OrderListAdapter orderListAdapter;
    List<Order> orderList;
    private SharedRefs sharedRefs;
    private Dialog dialog;
    private Button filterButton;

    public OrderFragment(String demoMessage) {
        this.demoMessage = demoMessage;
    }

    public static OrderFragment newInstance(String demoMessage) {
        OrderFragment fragment = new OrderFragment(demoMessage);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderList = new ArrayList<>();

        getOrderList();

        dialog = new Dialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TextView demoTextView = view.findViewById(R.id.demo_text_view);
//        demoTextView.setText(demoMessage);
    }

    public static class newInstance extends Fragment {
        public newInstance(String demo_fragment) {
        }
    }


    private final OrderClickListener orderClickListener = new OrderClickListener() {
        @Override
        public void onClick(Order order) {
            Intent intent = new Intent(getContext(), FeeActivity.class);
            intent.putExtra("name", order.getId());
            intent.putExtra("location", order.getId());
            intent.putExtra("img", "img1");
            startActivity(intent);
//            getActivity().finish();
            Toast.makeText(getContext(), "selected: " + order.getId(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLongClick(Order order, TextView textView) {
            callForDialog(order.getId().toString()+" id");
            Toast.makeText(getContext(), "long selected: " + order.getId()+" ", Toast.LENGTH_SHORT).show();
        }
    };

    private void getOrderList() {
//        progressBar.setVisibility(View.VISIBLE);
        Call<AllOrderResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .findAllOrder();

        call.enqueue(new Callback<AllOrderResponse>() {
            @Override
            public void onResponse(Call<AllOrderResponse> call, Response<AllOrderResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    String message = response.body().getStatus();
                    Toast.makeText(getActivity(), "Successfully got service data! status: "+message, Toast.LENGTH_LONG).show();
                    orderList = response.body().getOrders();
                    Toast.makeText(getContext(), "list size: " + orderList.size(), Toast.LENGTH_SHORT).show();

                    updateProjectsRecycler();


                } else {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllOrderResponse> call, Throwable t) {
                Log.d(TAG, "*******Fail*******" + t.getMessage());

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProjectsRecycler() {
        recyclerView = getView().findViewById(R.id.projectsRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        orderListAdapter = new OrderListAdapter(getContext(), orderList, orderClickListener);
        recyclerView.setAdapter(orderListAdapter);
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

}