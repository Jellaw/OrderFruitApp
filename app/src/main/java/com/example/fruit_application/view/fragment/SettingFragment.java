package com.example.fruit_application.view.fragment;

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
import com.example.fruit_application.databinding.FragmentSettingBinding;
import com.example.fruit_application.view.activity.AccountActivity;
import com.example.fruit_application.view.activity.MainActivity;
import com.example.fruit_application.view.activity.YourOrderActivity;
import com.example.fruit_application.viewmodel.InMainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;
    InMainViewModel inMainViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            intent.putExtra("userID", ((MainActivity) getActivity()).idUser);
            startActivity(intent);
        });
        binding.yourOrder.setOnClickListener(view1 ->{
            binding.yourOrder.setBackgroundResource(R.drawable.background_item_setting_clicked);
            new Handler().postDelayed(() -> {
                binding.yourOrder.setBackgroundResource(R.drawable.background_item_setting);
            }, 500);
            Intent intent = new Intent(getActivity(), YourOrderActivity.class);
            intent.putExtra("userID", ((MainActivity) getActivity()).idUser);
            startActivity(intent);
        });
    }
}