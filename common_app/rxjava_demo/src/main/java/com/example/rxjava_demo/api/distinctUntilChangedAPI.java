package com.example.rxjava_demo.api;

import com.example.rxjava_demo.api.card.BaseResponse;
import com.example.rxjava_demo.api.card.CardResponse;
import com.example.rxjava_demo.api.card.RechargeCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class distinctUntilChangedAPI {
    public List<RechargeCard> getFakeData() {
        Type type = new TypeToken<BaseResponse<CardResponse>>() {
        }.getType();
        Gson gson = new Gson();
        BaseResponse<CardResponse> o = gson.fromJson(mockStr, type);
        return o.getData().getCards();
    }
    public static final String mockStr = "{\n" +
            "    \"resultCode\": \"200\",\n" +
            "    \"transactionId\": \"csdasfff\",\n" +
            "    \"serverTime\": 1637752105,\n" +
            "    \"data\": {\n" +
            "        \"cards\": [\n" +
            "            {   \n" +
            "               \"bmsChargeStatus\": 7,\n" +
            "               \"bmsChrgSpRsn\": 1,\n" +
            "                \"bmsPackSOCDsp\":10,\n" +
            "                \"chargedPower\":0.5545,\n" +
            "                \"chargingRemainTime\":58,\n" +
            "                \"chrgngSpdngTime\":690,\n" +
            "                \"equipmentType\":\"string\",\n" +
            "                \"cardType\":\"charging\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"cardType\": \"roadRescue\",\n" +
            "                \"serviceNode\": \"道路救援\",\n" +
            "                \"orderId\": \"20211124132911\",\n" +
            "                \"createTime\": \"2021-11-24 14:39:38\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"cardType\":\"maintenance\",\n" +
            "               \"serviceNode\":\"维修保养\",\n" +
            "               \"hint\":\"12月11日\",\n" +
            "               \"hintText\":\"车辆正在维修中,预计 12月11日 可正常使用\",\n" +
            "               \"orderId\":\"67676\",\n" +
            "               \"createTime\":\"2021-11-23 12:09:11\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"bmsPackSOCDsp\":10,\n" +
            "               \"cardType\":\"lowBattery\"\n" +
            "             }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

}
