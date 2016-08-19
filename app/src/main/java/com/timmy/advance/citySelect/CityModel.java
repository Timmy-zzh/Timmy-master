package com.timmy.advance.citySelect;

import java.util.List;

public class CityModel {
	private String name;

	private List<DistrictModel> districtList;

	public CityModel() {
		super();
	}

	public CityModel(String name) {
		super();
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
