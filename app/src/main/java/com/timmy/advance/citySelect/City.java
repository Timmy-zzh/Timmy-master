package com.timmy.advance.citySelect;

/**
 * Created by admin on 2016/8/19.
 */
public class City {

    private String cityPinyin;
    private String cityName;
    private String firstPinYin;//城市名称第一个字母--作为索引

    public String getCityPinyin() {
        return cityPinyin;
    }

    public void setCityPinyin(String cityPinyin) {
        this.cityPinyin = cityPinyin;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFirstPinYin() {
        return firstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        this.firstPinYin = firstPinYin;
    }
}
