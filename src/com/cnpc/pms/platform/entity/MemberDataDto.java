package com.cnpc.pms.platform.entity;

public class MemberDataDto {

    String cityName;
    String storeNo;
    String inviteCode;
    String mobilePhone;
    String hidden_flag;
    String open_card_time_begin;
    String open_card_time_end;

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHidden_flag() {
        return hidden_flag;
    }

    public void setHidden_flag(String hidden_flag) {
        this.hidden_flag = hidden_flag;
    }

    public String getOpen_card_time_begin() {
        return open_card_time_begin;
    }

    public void setOpen_card_time_begin(String open_card_time_begin) {
        this.open_card_time_begin = open_card_time_begin;
    }

    public String getOpen_card_time_end() {
        return open_card_time_end;
    }

    public void setOpen_card_time_end(String open_card_time_end) {
        this.open_card_time_end = open_card_time_end;
    }
}
