package com.example.fruit_application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fruit_application.R;
import com.example.fruit_application.activities.MainActivity;
import com.example.fruit_application.activities.YourAccountActivity;
import com.example.fruit_application.activities.YourOrderActivity;
import com.example.fruit_application.activities.viewModel.InMainViewModel;
import com.example.fruit_application.databinding.FragmentSettingBinding;


public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;
    InMainViewModel inMainViewModel;
    private static final String p1 = "param1";
    private static final String p2= "param2";

    private String mParam1;
    private String mParam2;

    public SettingFragment() {
    }


    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
            mParam1 = getArguments().getString(p1);
            mParam2 = getArguments().getString(p2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting, container, false);
        View rootView = binding.getRoot();
        inMainViewModel = new ViewModelProvider(this).get(InMainViewModel.class);
        inMainViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).backToLoginActivity();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMainViewModel.logOut();
                binding.signOut.setBackgroundResource(R.drawable.background_item_setting_clicked);
            }
        });
        binding.yourAccount.setOnClickListener(view1 -> {
            binding.yourAccount.setBackgroundResource(R.drawable.background_item_setting_clicked);
            new Handler().postDelayed(() -> {
                binding.yourAccount.setBackgroundResource(R.drawable.background_item_setting);
            }, 500);
            Intent intent = new Intent(getActivity(), YourAccountActivity.class);
            intent.putExtra("idUser", ((MainActivity) getActivity()).idUser);
            startActivity(intent);
        });
        binding.yourOrder.setOnClickListener(view1 ->{
            binding.yourOrder.setBackgroundResource(R.drawable.background_item_setting_clicked);
            new Handler().postDelayed(() -> {
                binding.yourOrder.setBackgroundResource(R.drawable.background_item_setting);
            }, 500);
            Intent intent = new Intent(getActivity(), YourOrderActivity.class);
            intent.putExtra("idUser", ((MainActivity) getActivity()).idUser);
            startActivity(intent);
        });
    }
}