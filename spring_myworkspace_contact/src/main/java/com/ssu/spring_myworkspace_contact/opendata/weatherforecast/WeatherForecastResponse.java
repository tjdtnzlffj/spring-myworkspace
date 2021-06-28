package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import java.util.List;

import lombok.Data;

@Data
public class WeatherForecastResponse {
	private Response response;

	@Data
	public class Response {
		private Body body;
	}

	@Data
	public class Body {
		private Items items;
	}

	@Data
	public class Items {
		private List<Item> item;
	}

	@Data
	public class Item {
		private String baseDate;
		private String baseTime;
		private String category;
		private String fcstDate;
		private String fcstTime;
		private String fcstValue;
		private String nx;
		private String ny;
	}
}
