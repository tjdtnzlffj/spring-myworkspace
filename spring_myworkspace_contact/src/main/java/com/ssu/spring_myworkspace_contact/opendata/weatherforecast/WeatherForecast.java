package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(WeatherForecastId.class)
public class WeatherForecast {

	@Id
	private String baseDate;
	@Id
	private String baseTime;
	@Id
	private String nx;
	@Id
	private String ny;
	@Id
	private String fcstValue;

	private String resultCode;
	private String resultMsg;
	private String category;
	private String fcstDate;
	private String fcstTime;

	public WeatherForecast(WeatherForecastResponse.Item item) {
		this.baseDate = item.getBaseDate();
		this.baseTime = item.getBaseTime();
		this.category = item.getCategory();
		this.fcstDate = item.getFcstDate();
		this.fcstTime = item.getFcstTime();
		this.fcstValue = item.getFcstValue();
		this.nx = item.getNx();
		this.ny = item.getNy();
	}

}