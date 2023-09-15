package com.example.fruit_application.activities.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fruit_application.Model.AuthAppRepository;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.UserDBHelper;
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
        userDBHelper.creatUser(uID, fullName, email, password, "","", new RealmResponse<Boolean, Boolean>() {
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
