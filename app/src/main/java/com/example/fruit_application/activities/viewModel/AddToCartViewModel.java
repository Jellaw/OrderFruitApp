package com.example.fruit_application.activities.viewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fruit_application.Model.Fruit;
import com.example.fruit_application.database.CartDBHelper;
import com.example.fruit_application.database.RealmResponse;

public class AddToCartViewModel extends ViewModel {

    public int idFruit = -1;
    private CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    //LiveData là một lớp giữ dữ liệu quan sát được. Không giống như một thiết bị quan sát thông thường, LiveData nhận biết vòng đời, có nghĩa là nó tôn trọng vòng đời của các thành phần ứng dụng khác, chẳng hạn như các activities, fragments hoặc services. Nhận thức này đảm bảo LiveData chỉ cập nhật các thành phần ứng dụng quan sát nó khi những thành phần này đang ở trạng thái hoạt động
    public MutableLiveData<String > nameFruit = new MutableLiveData<>();
    public MutableLiveData<String > priceFruit = new MutableLiveData<>();
    private MutableLiveData<String > quantity = new MutableLiveData<>();
    private MutableLiveData<Fruit> fruitMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Fruit> getFruitMutableLiveData() {
        if (fruitMutableLiveData == null){
            fruitMutableLiveData = new MutableLiveData<>();
        }
        return fruitMutableLiveData;
    }

    public void setIdFruit(int id){
        this.idFruit=id;
    }
    public void setQuantity(String quantity){
        this.quantity.setValue(quantity);
    }

    public MutableLiveData<String> getQuantity() {
        return quantity;
    }
    public void setNameFruit(String nameFruit){
        this.nameFruit.setValue(nameFruit);
    }
    public void setPrice(String price){
        this.priceFruit.setValue(price);
    }
    public void onClickAddCart(View view, String idUser){
        Fruit fruit = new Fruit(idFruit, priceFruit.getValue(), nameFruit.getValue(),1);
        fruitMutableLiveData.setValue(fruit);
        saveCart(idUser);
    }
    public void saveCart(String idUser){
        cartDBHelper.creatCart(fruitMutableLiveData.getValue(), idUser, Integer.parseInt(quantity.getValue()), new RealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                return null;
            }
        });
    }

}
