package com.timmy.customeView.letterNavigation;

/**
 * Created by timmy1 on 17/2/12.
 */

public class User {

    private int img;
    private String userName;//姓名
    private String pinyin;//姓名的拼音
    private String firstLetter;//姓名的拼音的第一个字母

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
