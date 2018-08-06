package com.cnpc.pms.platform.entity;

public class MemberDataDto {

    String cityName;
    String storeNo;
    String inviteCode;
    String hidden_flag;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getHidden_flag() {
        return hidden_flag;
    }

    public void setHidden_flag(String hidden_flag) {
        this.hidden_flag = hidden_flag;
    }
}
