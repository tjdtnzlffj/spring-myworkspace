package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import javax.persistence.Column;
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
	@Column(columnDefinition = "CHAR(8)")
	private String baseDate;
	@Id
	@Column(columnDefinition = "CHAR(4)")
	private String baseTime;
	@Id
	@Column(columnDefinition = "VARCHAR(10)")
	private String nx;
	@Id
	@Column(columnDefinition = "VARCHAR(10)")
	private String ny;
	@Id
	@Column(columnDefinition = "VARCHAR(10)")
	private String category;

	private String fcstValue;
	private String fcstDate;
	private String fcstTime;

	public WeatherForecast(WeatherForecastResponse.Item item) {
		this.baseDate = item.getBaseDate();
		this.baseTime = item.getBaseTime();
		this.nx = item.getNx();
		this.ny = item.getNy();
		this.category = item.getCategory();
		this.fcstDate = item.getFcstDate();
		this.fcstTime = item.getFcstTime();
		this.fcstValue = item.getFcstValue();

	}

}