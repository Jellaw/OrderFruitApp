package com.example.fruit_application.view.fragment.checkoutFragment;

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
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.UserDBHelper;
import com.example.fruit_application.database.modelRealm.UserRealmModel;
import com.example.fruit_application.databinding.FragmentDeliveryAddressBinding;
import com.example.fruit_application.view.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryAddressFragment extends Fragment {
    UserDBHelper userDBHelper = UserDBHelper.getInstance();
    FragmentDeliveryAddressBinding binding;
    String userID, email, password;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeliveryAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliveryAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveryAddressFragment newInstance(String param1, String param2) {
        DeliveryAddressFragment fragment = new DeliveryAddressFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_delivery_address, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNameUser();
    }
    private void getNameUser(){
        userDBHelper.getUser(((MainActivity) getActivity()).idUser, new IRealmResponse<Boolean, UserRealmModel>() {
            @Override
            public Boolean executeService(UserRealmModel args) {
                if (args!=null){
                    String name = args.getFullName();
                    String add = args.getAddress();
                    String town = args.getTown();
                    userID = args.getuID();
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
    public void updateUser(IRealmResponse<Boolean,Boolean> callback){
        userDBHelper.createUser(userID, binding.editTextFullName.getText().toString(),email, password,
                binding.editTextStreetAddress.getText().toString(), binding.editTextCity.getText().toString(), callback);

    }
}