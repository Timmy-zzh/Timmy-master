package com.timmy.advance.citySelect;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 城市列表解析dao
 * 单例设计模式
 * 解析本地assets数据,可以获取所有的省-市-区-数据
 */
public class CityDao {

    private Context mContext;
    private static CityDao cityDao;
    private List<String> provinceNames = new ArrayList<>();
    private List<String> cityNames= new ArrayList<>();
    private List<String> districtNames= new ArrayList<>();

    public static CityDao getInstance(Context context) {
        if (cityDao == null) {
            synchronized (CityDao.class) {
                if (cityDao == null) {
                    cityDao = new CityDao(context);
                }
            }
        }
        return cityDao;
    }

    private CityDao(Context context) {
        this.mContext = context;
        parseXmlData();
    }

    private void parseXmlData() {
        AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            List<ProvinceModel> provinceModelList = handler.getDataList();
            // 初始化默认选中的省、市、区
            if (provinceModelList != null && !provinceModelList.isEmpty()) {
                for (int i = 0; i < provinceModelList.size(); i++) {//遍历省
                    ProvinceModel provinceModel = provinceModelList.get(i);
                    provinceNames.add(provinceModel.getName());

                    List<CityModel> cityModels = provinceModel.getCityList();
                    for (int j = 0; j < cityModels.size(); j++) {//遍历省里面的市
                        CityModel cityModel = cityModels.get(j);
                        cityNames.add(cityModel.getName());

                        List<DistrictModel> districtList = cityModel.getDistrictList();
                        for (int h = 0; h < districtList.size(); h++) {//遍历市里面的区
                            DistrictModel districtModel = districtList.get(h);
                            districtNames.add(districtModel.name);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    public List<String> getProvinceNames(){
        return provinceNames;
    }

    public List<String> getCityNames(){
        return cityNames;
    }

    public List<String> getDistrictNames(){
        return districtNames;
    }

}
