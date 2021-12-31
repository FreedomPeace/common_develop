package com.example.rxjava_demo.api.card;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardResponse {

    @SerializedName("cards")
    private List<RechargeCard> cards;

    public List<RechargeCard> getCards() {
        return cards;
    }
}
