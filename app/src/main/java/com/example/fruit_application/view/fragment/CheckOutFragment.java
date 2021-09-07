package com.example.fruit_application.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import com.example.fruit_application.R;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.modelRealm.CartRealmModel;
import com.example.fruit_application.databinding.FragmentCheckOutBinding;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.view.activity.MainActivity;
import com.example.fruit_application.view.activity.OrderPlacedActivity;
import com.example.fruit_application.view.fragment.checkoutFragment.CreditCartFragment;
import com.example.fruit_application.view.fragment.checkoutFragment.DeliveryAddressFragment;
import com.example.fruit_application.viewmodel.OrderViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckOutFragment extends Fragment {
    FragmentCheckOutBinding binding;
    FragmentManager manager;
    DeliveryAddressFragment deliveryAddressFragment;
    CreditCartFragment creditCartFragment;
    OrderViewModel orderViewModel;
    public int idCart;
    Cart cart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckOutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckOutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckOutFragment newInstance(String param1, String param2) {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        TransitionInflater inflaterExit = TransitionInflater.from(requireContext());
        setEnterTransition(inflaterExit.inflateTransition(R.transition.slide_right));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_out, container, false);
        View rootView = binding.getRoot();
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackgroundWhenSelect(binding.llLocation, binding.imageLocation
                , binding.llCredit, binding.imageCredit, binding.llUnknown, binding.imageUnknow);
        initDeliveryFragment();
        binding.llLocation.setOnClickListener(view1 -> {
            setBackgroundWhenSelect(binding.llLocation, binding.imageLocation
                    , binding.llCredit, binding.imageCredit, binding.llUnknown, binding.imageUnknow);
            binding.headerCheckout.setText("Delivery Address");
            initDeliveryFragment();
        });
        binding.llCredit.setOnClickListener(view1 -> {
            setBackgroundWhenSelect(binding.llCredit, binding.imageCredit
                    , binding.llLocation, binding.imageLocation, binding.llUnknown, binding.imageUnknow);
            binding.headerCheckout.setText("Payment Method");
            initPaymentMethodFragment();
        });
        binding.llUnknown.setOnClickListener(view1 -> {
            setBackgroundWhenSelect(binding.llUnknown, binding.imageUnknow
                    , binding.llLocation, binding.imageLocation, binding.llCredit, binding.imageCredit);
            binding.headerCheckout.setText("Unknown Fragment");
        });
        binding.buttonPlaceOrder.setOnClickListener(view1 -> {
            deliveryAddressFragment.updateUser(new IRealmResponse<Boolean, Boolean>() {
                @Override
                public Boolean executeService(Boolean args) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orderViewModel.getCartData(idCart, ((MainActivity) getActivity()).idUser, new IRealmResponse<Boolean, CartRealmModel>() {
                                @Override
                                public Boolean executeService(CartRealmModel args) {
                                    if (args!=null){
                                        cart = new Cart(args.getCartID(),
                                                args.getuID(),args.getIdFruit(),1,args.getNameFruit(),
                                                args.getQuantity(),args.getPrice());
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            orderViewModel.saveOrder(cart, "Free", true);
                                        }
                                    });
                                    return null;
                                }
                            });
                        }
                    });
                    return null;
                }
            });
            new Handler().postDelayed(() -> {
                deleteCartOnTop(new IRealmResponse<Boolean, Boolean>() {
                    @Override
                    public Boolean executeService(Boolean args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(() -> {
                                    getFragmentManager().beginTransaction().remove(CheckOutFragment.this).commitAllowingStateLoss();
                                }, 1000);
                                getActivity().onBackPressed();
                                Intent intent = new Intent(getActivity(), OrderPlacedActivity.class);
                                startActivity(intent);
                            }
                        });
                        return null;
                    }
                });
            }, 1000);
        });
        binding.imageViewBack.setOnClickListener(view1 -> {
            getFragmentManager().beginTransaction().remove(CheckOutFragment.this).commitAllowingStateLoss();
        });
    }

    private void setBackgroundWhenSelect(LinearLayout l1, ImageView img1, LinearLayout l2, ImageView img2, LinearLayout l3, ImageView img3) {
        l1.setBackgroundResource(R.drawable.background_checkout_item_header_clicked);
        l2.setBackgroundResource(R.drawable.background_checkout_header_item_unclick);
        l3.setBackgroundResource(R.drawable.background_checkout_header_item_unclick);
        switch (img1.getId()) {
            case R.id.imageLocation:
                img1.setImageResource(R.drawable.ic_baseline_location_on_24);
                img2.setImageResource(R.drawable.ic_baseline_credit_card_unclick);
                img3.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_unclick);
                break;
            case R.id.imageCredit:
                img1.setImageResource(R.drawable.ic_baseline_credit_card_24);
                img2.setImageResource(R.drawable.ic_baseline_location_on_24_unclick);
                img3.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_unclick);
                break;
            case R.id.imageUnknow:
                img1.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24);
                img2.setImageResource(R.drawable.ic_baseline_location_on_24_unclick);
                img3.setImageResource(R.drawable.ic_baseline_credit_card_unclick);
                break;
        }
    }

    private void initDeliveryFragment() {
        manager = getChildFragmentManager();
        deliveryAddressFragment = new DeliveryAddressFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        if (deliveryAddressFragment == null) {
            deliveryAddressFragment = new DeliveryAddressFragment();
        }
        transaction.add(R.id.fl_checkout, deliveryAddressFragment, "Delivery status");
        transaction.commit();
    }

    private void initPaymentMethodFragment() {
        manager = getChildFragmentManager();
        creditCartFragment = new CreditCartFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        if (creditCartFragment == null) {
            creditCartFragment = new CreditCartFragment();
        }
        transaction.replace(R.id.fl_checkout, creditCartFragment, "payment status");
        transaction.commit();
    }

//    private void saveOrderData() {
//        Cart cart = orderViewModel.getCartData(idCart, ((MainActivity) getActivity()).idUser);
//        orderViewModel.saveOrder(cart, "Free", true);
//
//    }

    private void deleteCartOnTop(IRealmResponse<Boolean, Boolean> callback) {
        orderViewModel.deleteCartItem(idCart, ((MainActivity) getActivity()).idUser, callback);
    }
}