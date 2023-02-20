package com.example.rxjava_demo.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rxjava_demo.bean.UserResponse;
import com.example.rxjava_demo.github.GitHubApi;
import com.example.rxjava_demo.github.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getFreedomPeaceInfo(Context context) {
        GitHubApi gitHubApi = RetrofitManager.getInstance().getRetrofit().create(GitHubApi.class);
        gitHubApi.getFreedomPeace().observeOn(AndroidSchedulers.mainThread()).doOnSuccess(o -> {
            String prettyFormat = toPrettyFormat(o);
            mText.setValue(prettyFormat);
            Log.d("azp", prettyFormat);
        }).doOnError(t -> {
            mText.setValue(t.getMessage());
        }).subscribe();
    }
    public void getReposInfo(Context context,String username) {
        if (Objects.isNull(username)) {
            username = "twbs";
        }
        GitHubApi gitHubApi = RetrofitManager.getInstance().getRetrofit().create(GitHubApi.class);
        gitHubApi.getReposInfo(username).observeOn(AndroidSchedulers.mainThread()).doOnSuccess(o -> {
            String prettyFormat = toPrettyFormat(o);
            mText.setValue(prettyFormat);
            Log.d("azp", prettyFormat);
        }).doOnError(t -> {
            mText.setValue(t.getMessage());
        }).subscribe();
    }
    public void getUsers(Context context) {
        GitHubApi gitHubApi = RetrofitManager.getInstance().getRetrofit().create(GitHubApi.class);
        gitHubApi.getUsers().observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(s -> {
                    Log.d("azp", s.size()+"");
                    for (UserResponse e : s) {
                        String prettyFormat = toPrettyFormat(e);
                        mText.setValue(prettyFormat);
//                        Log.d("azp", prettyFormat);
                    }

                })
                .doOnError(throwable -> {
                    String message = throwable.getMessage();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                })
                .subscribe();
    }

    public static String toPrettyFormat(Object json) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(gson.toJson(json)).getAsJsonObject();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        return gson2.toJson(jsonObject);
    }


}