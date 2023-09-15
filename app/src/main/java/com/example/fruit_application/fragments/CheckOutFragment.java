package com.example.fruit_application.fragments;

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

import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.R;
import com.example.fruit_application.activities.MainActivity;
import com.example.fruit_application.activities.OderPlacedActivity;
import com.example.fruit_application.activities.viewModel.OrderViewModel;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.modelRealm.CartRealm;
import com.example.fruit_application.databinding.FragmentCheckoutBinding;

public class CheckOutFragment extends Fragment {
    FragmentCheckoutBinding binding;
    FragmentManager manager;
    DeliveryAddressFragment deliveryAddressFragment;
    CreditCardFragment creditCardFragment;
    OrderViewModel orderViewModel;
    public int idCart;
    Cart cart;

    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparam2;
    public CheckOutFragment() {
    }
    public static CheckOutFragment newInstance(String param1 , String param2) {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(p1, param1);
        bundle.putString(p2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        TransitionInflater inflaterExit = TransitionInflater.from(requireContext());
        setEnterTransition(inflaterExit.inflateTransition(R.transition.slide_right));
        if (getArguments() != null){
            mparam1 = getArguments().getString(p1);
            mparam2 = getArguments().getString(p2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
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
            deliveryAddressFragment.updateUser(new RealmResponse<Boolean, Boolean>() {
                @Override
                public Boolean executeService(Boolean args) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orderViewModel.getCartData(idCart, ((MainActivity) getActivity()).idUser, new RealmResponse<Boolean, CartRealm>() {
                                @Override
                                public Boolean executeService(CartRealm args) {
                                    if (args!=null){
                                        cart = new Cart(args.getIdCart(),
                                                args.getIdUs(),args.getIdFruit(),1,args.getNamefruit(),
                                                args.getQuanlity(),args.getPriceFruit());
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
                deleteCartOnTop(new RealmResponse<Boolean, Boolean>() {
                    @Override
                    public Boolean executeService(Boolean args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(() -> {
                                    getFragmentManager().beginTransaction().remove(CheckOutFragment.this).commitAllowingStateLoss();
                                }, 1000);
                                getActivity().onBackPressed();
                                Intent intent = new Intent(getActivity(), OderPlacedActivity.class);
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
        creditCardFragment = new CreditCardFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        if (creditCardFragment == null) {
            creditCardFragment = new CreditCardFragment();
        }
        transaction.replace(R.id.fl_checkout, creditCardFragment, "payment status");
        transaction.commit();
    }



    private void deleteCartOnTop(RealmResponse<Boolean, Boolean> callback) {
        orderViewModel.deleteCartItem(idCart, ((MainActivity) getActivity()).idUser, callback);
    }
}
