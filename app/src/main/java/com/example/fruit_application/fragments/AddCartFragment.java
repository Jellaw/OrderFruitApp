package com.example.fruit_application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.example.fruit_application.R;

public class AddCartFragment extends Fragment {

    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparam2;

    public AddCartFragment(){

    }

    public static AddCartFragment newInstance(String param1 , String param2){
        AddCartFragment fragment = new AddCartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(p1, param1);
        bundle.putString(p2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mparam1 = getArguments().getString(p1);
            mparam2  = getArguments().getString(p2);
        }
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        TransitionInflater inflaterExit = TransitionInflater.from(requireContext());
        setEnterTransition(inflaterExit.inflateTransition(R.transition.slide_bottom));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_add_cart, container, false);
    }
}
