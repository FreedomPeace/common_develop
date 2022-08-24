package com.example.rxjava_demo.ui.home;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rxjava_demo.github.GitHubApi;
import com.example.rxjava_demo.github.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Retrofit;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getTextFromGithub(Context context) {
        GitHubApi gitHubApi = RetrofitManager.getInstance().getRetrofit().create(GitHubApi.class);
        gitHubApi.getDefunkt().observeOn(AndroidSchedulers.mainThread()).doOnSuccess(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Throwable {
                        mText.setValue(toPrettyFormat(s));
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        String message = throwable.getMessage();
//                        mText.setValue(message);
                        Toast.makeText(context , message, Toast.LENGTH_SHORT).show();
                    }
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