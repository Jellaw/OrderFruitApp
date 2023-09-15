package com.example.fruit_application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.fruit_application.R;
import com.example.fruit_application.activities.MainActivity;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.UserDBHelper;
import com.example.fruit_application.database.modelRealm.UserRealm;
import com.example.fruit_application.databinding.DeliveryAddressBinding;

public class DeliveryAddressFragment extends Fragment {
    UserDBHelper userDBHelper = UserDBHelper.getInstance();
    DeliveryAddressBinding binding;
    String userID, email, password;
    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparam2;

    public DeliveryAddressFragment(){

    }

    public static DeliveryAddressFragment newInstance(String param1, String param2) {
        DeliveryAddressFragment fragment = new DeliveryAddressFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.delivery_address, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNameUser();
    }
    private void getNameUser(){
        userDBHelper.getUser(((MainActivity) getActivity()).idUser, new RealmResponse<Boolean, UserRealm>() {
            @Override
            public Boolean executeService(UserRealm args) {
                if (args!=null){
                    String name = args.getFullname();
                    String add = args.getStreet();
                    String town = args.getTown();
                    userID = args.getIdUser();
                    email = args.getEmail();
                    password = args.getPassword();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                binding.editTextFullName.setText(name);
                                binding.editTextStreetAddress.setText(add);
                                binding.editTextCity.setText(town);
                            } catch (Exception e){
                                Toast.makeText(getContext(), "Fullname exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Fullname null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return null;
            }
        });
    }
    public void updateUser(RealmResponse<Boolean,Boolean> callback){
        userDBHelper.creatUser(userID, binding.editTextFullName.getText().toString(),email, password,
                binding.editTextStreetAddress.getText().toString(), binding.editTextCity.getText().toString(), callback);

    }
}
