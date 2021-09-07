package com.example.fruit_application.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruit_application.R;
import com.example.fruit_application.adapter.FruitAdapter;
import com.example.fruit_application.databinding.FragmentHomeBinding;
import com.example.fruit_application.model.Fruit;
import com.example.fruit_application.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    List<Fruit> list = new ArrayList<>();
    FruitAdapter adapter, adapterBestFruit;
    String idUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View rootView = binding.getRoot();
        idUser = ((MainActivity)getActivity()).idUser;
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add();
        binding.ourStoreMore.setOnClickListener(view1 -> {
            showMoreFruit(1);
        });
        binding.bestSummerMore.setOnClickListener(view1 -> {
            showMoreFruit(2);
        });
    }
    private void add() {
        list.add(new Fruit(0,R.drawable.durian,"Durian", "2.5"));
        list.add(new Fruit(1,R.drawable.watermelon_img,"Watermelon", "1.5"));
        list.add(new Fruit(2,R.drawable.apple,"Apple", "2.0"));
        list.add(new Fruit(3,R.drawable.kiwi_xanh_500x500,"Kiwi", "2.5"));
        list.add(new Fruit(4,R.drawable.lemon_1205_1667,"Lemons", "0.5"));
        list.add(new Fruit(5,R.drawable.papaya,"Papaya", "2.5"));
        initRecyclerViewOurStore();
        initRecyclerViewBestFruit();
    }



    private void initRecyclerViewOurStore() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvOurStore.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(getContext(), list, idUser);
        binding.rvOurStore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initRecyclerViewBestFruit() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvBestFruit.setLayoutManager(layoutManager);
        adapterBestFruit = new FruitAdapter(getContext(), list, idUser);
        binding.rvBestFruit.setAdapter(adapterBestFruit);
        adapterBestFruit.notifyDataSetChanged();
    }
    public void showMoreFruit(int i){
        ((MainActivity)getActivity()).goToMoreFruitActivity(i);
    }
}