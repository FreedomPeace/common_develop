package com.example.rxjava_demo.api.card;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class RechargeCard implements Serializable {

    @SerializedName("bmsChargeStatus")
    private int bmsChargeStatus;

    @SerializedName("chargedPower")
    private double chargedPower;

    @SerializedName("chargingRemainTime")
    private int chargingRemainTime;

    @SerializedName("chrgngSpdngTime")
    private int chrgngSpdngTime;

//    @SerializedName("battSOC")
//    private int battSOC;

    @SerializedName("equipmentType")
    private String equipmentType;

    @SerializedName("bmsChrgSpRsn")
    private int bmsChrgSpRsn;

    @SerializedName("bmsPackSOCDsp")
    private double bmsPackSOCDsp;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("cardType")
    private String cardType;

    @SerializedName("serviceNode")
    private String serviceNode;

    @SerializedName("roCreateDate")
    private String roCreateDate;

    @SerializedName("hintText")
    private String hintText;


    @SerializedName("hint")
    private String hint;

    public RechargeCard() {
    }

    public String getHintText() {
		return hintText;
	}

	public String getOrderId() {
        return orderId;
    }

    public String getCardType() {
        return cardType;
    }

    public String getServiceNode() {
//		roadRescue
//		2:  救援人员已接单
//		3:   救援人员已出发
//		4: 救援人员已到达
//        String mServiceNode = "";
//		maintenance 	2:维修保养已开始
//        switch (serviceNode) {
//            case "2":
//                if ("roadRescue".equals(cardType)) {
//                    mServiceNode = "救援人员已接单";
//                } else if ("maintenance".equals(cardType)) {
//                    mServiceNode = "维修保养已开始";
//                }
//                break;
//            case "3":
//                mServiceNode = "救援人员已出发";
//                break;
//            case "4":
//                mServiceNode = "救援人员已到达";
//                break;
//        }
        return serviceNode;
    }

    public String getRoCreateDate() {
        return roCreateDate;
    }

    public String getHint() {
        return hint;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public String contentDescription;

    public double getBmsPackSOCDsp() {
        return bmsPackSOCDsp;
    }

    public int getBmsChargeStatus() {
        return bmsChargeStatus;
    }

    public double getChargedPower() {
        return chargedPower;
    }

    public int getChargingRemainTime() {
        return chargingRemainTime;
    }

    public int getChrgngSpdngTime() {
        return chrgngSpdngTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RechargeCard that = (RechargeCard) o;
        return bmsChargeStatus == that.bmsChargeStatus && Double.compare(that.chargedPower, chargedPower) == 0 && chargingRemainTime == that.chargingRemainTime && chrgngSpdngTime == that.chrgngSpdngTime && bmsChrgSpRsn == that.bmsChrgSpRsn && Double.compare(that.bmsPackSOCDsp, bmsPackSOCDsp) == 0 && Objects.equals(equipmentType, that.equipmentType) && Objects.equals(orderId, that.orderId) && Objects.equals(cardType, that.cardType) && Objects.equals(serviceNode, that.serviceNode) && Objects.equals(roCreateDate, that.roCreateDate) && Objects.equals(hintText, that.hintText) && Objects.equals(hint, that.hint) && Objects.equals(contentDescription, that.contentDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bmsChargeStatus, chargedPower, chargingRemainTime, chrgngSpdngTime, equipmentType, bmsChrgSpRsn, bmsPackSOCDsp, orderId, cardType, serviceNode, roCreateDate, hintText, hint, contentDescription);
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public int getBmsChrgSpRsn() {
        return bmsChrgSpRsn;
    }


    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setBmsChargeStatus(int bmsChargeStatus) {
        this.bmsChargeStatus = bmsChargeStatus;
    }

    public void setChargedPower(double chargedPower) {
        this.chargedPower = chargedPower;
    }

    public void setChargingRemainTime(int chargingRemainTime) {
        this.chargingRemainTime = chargingRemainTime;
    }

    public void setChrgngSpdngTime(int chrgngSpdngTime) {
        this.chrgngSpdngTime = chrgngSpdngTime;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public void setBmsChrgSpRsn(int bmsChrgSpRsn) {
        this.bmsChrgSpRsn = bmsChrgSpRsn;
    }

    public void setBmsPackSOCDsp(double bmsPackSOCDsp) {
        this.bmsPackSOCDsp = bmsPackSOCDsp;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setServiceNode(String serviceNode) {
        this.serviceNode = serviceNode;
    }

    public void setRoCreateDate(String roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

}