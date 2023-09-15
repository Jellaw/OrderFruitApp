package com.example.fruit_application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.fruit_application.R;
import com.example.fruit_application.activities.AddCardActivity;
import com.example.fruit_application.databinding.PaymainMethodBinding;

public class CreditCardFragment extends Fragment {

    PaymainMethodBinding binding;
    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparam2;

    public CreditCardFragment(){

    }
    public static CreditCardFragment newInstance(String param1, String param2) {
        CreditCardFragment fragment = new CreditCardFragment();
        Bundle args = new Bundle();
        args.putString(p1, param1);
        args.putString(p2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mparam1 = getArguments().getString(p1);
            mparam2 = getArguments().getString(p2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.paymain_method, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addCard.setOnClickListener(view1 ->{
            Intent intent = new Intent(getActivity(), AddCardActivity.class);
            startActivity(intent);
        });
    }
}
