package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1743176488971107945L;
	private String baseDate;
	private String baseTime;
	private String nx;
	private String ny;
	private String fcstValue;

}
