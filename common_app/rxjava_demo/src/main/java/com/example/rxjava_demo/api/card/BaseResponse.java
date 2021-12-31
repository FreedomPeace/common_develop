package com.example.rxjava_demo.api.card;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author sk
 * @Description Base返回
 * @Date 2021/5/18 10:31
 **/
@Keep
public class BaseResponse<T> implements Serializable {
    T data;
    String message="";
    String resultCode="";
    String serverTime="";
    int total=0;
    String transactionId="";

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccess(){
      if("200".equals(resultCode))
          return true;
         else return false;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", total=" + total +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
