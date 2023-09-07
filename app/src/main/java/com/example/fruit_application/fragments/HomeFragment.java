package com.example.fruit_application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fruit_application.Model.Fruit;
import com.example.fruit_application.R;
import com.example.fruit_application.activities.MainActivity;
import com.example.fruit_application.adapter.FruitAdapter;
import com.example.fruit_application.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    List<Fruit> list = new ArrayList<>();

    FruitAdapter fruitAdapter , bestfruitadapter;
    String idUser;

    private static final String p1 = "param1";
    private static final String p2 = "param2";

    private String mparam1;
    private String mparem2;
    public HomeFragment() {
    }
    public static HomeFragment newInstance(String param1 , String param2){
        HomeFragment fragment = new HomeFragment();
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

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home, container , false);
        View view = binding.getRoot();
        idUser = ((MainActivity)getActivity()).idUser;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.add(new Fruit(0,"Durian", "2.9", R.drawable.durian));
        list.add(new Fruit(1,"Apple", "2.9", R.drawable.apple));
        list.add(new Fruit(2,"Papaya", "1.9", R.drawable.papaya));
        list.add(new Fruit(0,"Watermelon", "1.9", R.drawable.watermelon));
        list.add(new Fruit(0,"Tomato", "2.5", R.drawable.tomato));
        list.add(new Fruit(0,"Peach", "2.9", R.drawable.peach));
        list.add(new Fruit(0,"Kiwi", "2.0", R.drawable.kiwi_xanh_500x500));
        initRecyclerViewOurStore();
        initRecyclerViewBestFruit();

        binding.ourStoreMore.setOnClickListener(view1 -> {
            showMoreFruit(1);
        });
        binding.ourStoreMore.setOnClickListener(view1 -> {
            showMoreFruit(2);
        });
    }

    private void initRecyclerViewOurStore() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvOurStore.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(list, getContext(), idUser);
        binding.rvOurStore.setAdapter(fruitAdapter);
        fruitAdapter.notifyDataSetChanged();
    }
    private void initRecyclerViewBestFruit() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvBestFruit.setLayoutManager(layoutManager);
        bestfruitadapter = new FruitAdapter(list, getContext(), idUser);
        binding.rvBestFruit.setAdapter(bestfruitadapter);
        bestfruitadapter.notifyDataSetChanged();
    }
    public void showMoreFruit(int i){
        ((MainActivity)getActivity()).goToMoreFruitActivity(i);
    }
}
