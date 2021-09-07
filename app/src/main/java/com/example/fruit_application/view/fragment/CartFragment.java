package com.example.fruit_application.view.fragment;

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

import com.example.fruit_application.R;
import com.example.fruit_application.adapter.CartAdapter;
import com.example.fruit_application.callback.CallbackItemCart;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.CartDBHelper;
import com.example.fruit_application.database.modelRealm.CartRealmModel;
import com.example.fruit_application.databinding.FragmentCartBinding;
import com.example.fruit_application.dialog.EditQuantityDialog;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    List<Cart> list = new ArrayList<>();
    CartAdapter adapter;
    CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    float totalMoney =0;
    EditQuantityDialog editQuantityDialog;
    CheckOutFragment checkOutFragment;
    public int idCart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add();
    }
    private void add() {
        initRecyclerView();
        getCartData();
        binding.checkout.setOnClickListener(view ->{
            initCheckOutFragment();
        });
    }



    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCart.setLayoutManager(layoutManager);
        adapter = new CartAdapter(getContext(), CartFragment.this,list, new CallbackItemCart() {
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
        cartDBHelper.getListCartByUser(((MainActivity) getActivity()).idUser, new IRealmResponse<Boolean, List<CartRealmModel>>() {
            @Override
            public Boolean executeService(List<CartRealmModel> args) {
                if (args.size()!=0){
                    for (CartRealmModel cartRealmModel : args){
                        switch (cartRealmModel.getIdFruit()){
                            case 0: list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),0, R.drawable.durian,"Durian", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                            break;
                            case 1: list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),1, R.drawable.watermelon_img,"Watermelon", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                                break;
                            case 2: list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),2, R.drawable.apple,"Apple", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                                break;
                            case 3 : list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),3, R.drawable.kiwi_xanh_500x500,"Kiwi", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                                break;
                            case 4: list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),4, R.drawable.lemon_1205_1667,"Lemon", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                                break;
                            case 5: list.add(new Cart(cartRealmModel.getCartID()
                                    ,cartRealmModel.getuID(),5, R.drawable.papaya,"Papaya", cartRealmModel.getQuantity(), cartRealmModel.getPrice()));
                                break;
                        }
                        totalMoney = totalMoney + Float.parseFloat(cartRealmModel.getPrice().substring(2));
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
        cartDBHelper.deleteCartItem(cart, idUser, new IRealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                totalMoney = totalMoney - Float.parseFloat(cart.getPrice().substring(2));
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
        cartDBHelper.updateQuantityCart(cart, idUser, positionAdapter,quantity, new IRealmResponse<Boolean,Boolean>(){

            @Override
            public Boolean executeService(Boolean args) {
                if(args == true) {
                    Cart cartEdit = list.get(positionAdapter);
                    totalMoney = totalMoney - Float.parseFloat(cart.getPrice().substring(2));
                    cartEdit.setPrice("$ "+(Float.parseFloat(cart.getPrice().substring(2))/cart.getQuantity())*quantity);
                    cartEdit.setQuantity(quantity);
                    totalMoney = totalMoney + Float.parseFloat(cartEdit.getPrice().substring(2));
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