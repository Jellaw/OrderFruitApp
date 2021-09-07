package com.example.fruit_application.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.UserDBHelper;
import com.example.fruit_application.model.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private UserDBHelper userDBHelper = UserDBHelper.getInstance();

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();
    }

    public void login(String email, String password) {
        authAppRepository.login(email, password);
    }

    public void register(String email, String password) {
        authAppRepository.register(email, password);
    }

    public void createInfoUser(String uID, String email, String password, String fullName){
        userDBHelper.createUser(uID, fullName, email, password, "","", new IRealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                return null;
            }
        });
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
