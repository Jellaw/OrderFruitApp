package com.example.fruit_application.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruit_application.activities.EditQuantityDialog;
import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.R;
import com.example.fruit_application.activities.MainActivity;
import com.example.fruit_application.adapter.CallbackItemCart;
import com.example.fruit_application.adapter.CartAdapter;
import com.example.fruit_application.database.CartDBHelper;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.modelRealm.CartRealm;
import com.example.fruit_application.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class Cartfragment extends Fragment {
    public int idCart;

    List<Cart> list = new ArrayList<>();
    FragmentCartBinding binding;
    CartAdapter adapter;
    CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    float totalMoney = 0;

    EditQuantityDialog editQuantityDialog;
    CheckOutFragment checkOutFragment;

    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparem2;
    private Object cartData;

    public Cartfragment(){
    }

    public static Cartfragment newInstance(String param1 , String param2){
        Cartfragment fragment = new Cartfragment();
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
            mparem2  = getArguments().getString(p2);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_cart, container , false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        getCartData();
        binding.checkout.setOnClickListener(view1 -> {
            initCheckOutFragment();
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCart.setLayoutManager(layoutManager);
        adapter = new CartAdapter(getContext(), Cartfragment.this,list, new CallbackItemCart() {
            @Override
            public void editCart(int index) {
                showEditDialog(list.get(index), index);
            }

            @Override
            public void deleteCart(int index) {
                Cart cart = list.get(index);
                deleteCartItem(cart, ((MainActivity)getActivity()).idUser, index);
            }
        });
        binding.rvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void getCartData(){
        cartDBHelper.getListCartByUser(((MainActivity) getActivity()).idUser, new RealmResponse<Boolean, List<CartRealm>>() {
            @Override
            public Boolean executeService(List<CartRealm> args) {
                if (args.size()!=0){
                    for (CartRealm cartRealm : args){
                        switch (cartRealm.getIdFruit()){
                            case 0: list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),0, R.drawable.durian,"Durian", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                            case 1: list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),1, R.drawable.watermelon_img,"Watermelon", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                            case 2: list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),2, R.drawable.apple,"Apple", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                            case 3 : list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),3, R.drawable.kiwi_xanh_500x500,"Kiwi", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                            case 4: list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),4, R.drawable.lemon_1205_1667,"Lemon", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                            case 5: list.add(new Cart(cartRealm.getIdCart()
                                    ,cartRealm.getIdUs(),5, R.drawable.papaya,"Papaya", cartRealm.getQuanlity(), cartRealm.getPriceFruit()));
                                break;
                        }
                        totalMoney = totalMoney + Float.parseFloat(cartRealm.getPriceFruit().substring(2));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            binding.totalPrice.setText("$ "+totalMoney);
                            binding.subTotal.setText("$ "+totalMoney);
                        }
                    });
                }
                return null;
            }
        });
    }
    private void deleteCartItem(Cart cart, String idUser, int positionAdapter){
        cartDBHelper.deleteCartItem(cart, idUser, new RealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                totalMoney = totalMoney - Float.parseFloat(cart.getPriceFruit().substring(2));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(positionAdapter);
                        binding.totalPrice.setText("$ "+totalMoney);
                        binding.subTotal.setText("$ "+totalMoney);
                        adapter.notifyDataSetChanged();
                    }
                });
                return null;
            }
        });
    }
    public void updateCartItem(Cart cart, String idUser, int positionAdapter, int quantity){
        cartDBHelper.updateQuantityCart(cart, idUser, positionAdapter,quantity, new RealmResponse<Boolean,Boolean>(){

            @Override
            public Boolean executeService(Boolean args) {
                if(args == true) {
                    Cart cartEdit = list.get(positionAdapter);
                    totalMoney = totalMoney - Float.parseFloat(cart.getPriceFruit().substring(2));
                    cartEdit.setPriceFruit("$ "+(Float.parseFloat(cart.getPriceFruit().substring(2))/cart.getQuanlity())*quantity);
                    cartEdit.setQuanlity(quantity);
                    totalMoney = totalMoney + Float.parseFloat(cartEdit.getPriceFruit().substring(2));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editQuantityDialog.dismiss();
                            binding.totalPrice.setText("$ "+totalMoney);
                            binding.subTotal.setText("$ "+totalMoney);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                return null;
            }
        });
    }
    public void showEditDialog(Cart cart, int positionAdapter) {
        if(editQuantityDialog == null) {
            editQuantityDialog = new EditQuantityDialog(getContext(), this, ((MainActivity)getActivity()).idUser) ;
            editQuantityDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            editQuantityDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
            editQuantityDialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            editQuantityDialog.setCancelable(true);
        }
        editQuantityDialog.setCartEdit(cart, positionAdapter);
        editQuantityDialog.show();
    }
    public void initCheckOutFragment(){
        checkOutFragment = new CheckOutFragment();
        FragmentTransaction transactionEdu = ((MainActivity) getActivity()).manager.beginTransaction();
        if(checkOutFragment == null){
            checkOutFragment = new CheckOutFragment();
        }
        checkOutFragment.idCart = idCart;
        transactionEdu.add(R.id.nav_host_fragment, checkOutFragment, "Checkout status");
        transactionEdu.addToBackStack("Checkout");
        transactionEdu.commit();
    }
}
