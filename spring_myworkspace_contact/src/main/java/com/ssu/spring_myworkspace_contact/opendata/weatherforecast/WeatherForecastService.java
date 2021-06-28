package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class WeatherForecastService {

	WeatherForecastRepository repo;

	@Autowired
	public WeatherForecastService(WeatherForecastRepository repo) {
		this.repo = repo;
	}

	// @CacheEvic: 메서드가 실행될 때 해당 캐시를 삭제함
	// allEntries = true, 해당객체 타임에 해당하는 캐시를 모두 삭제함

//	@CacheEvict(cacheNames = "weatherForecast-list", key = "0")
	@CacheEvict(cacheNames = "weatherForecast-list", allEntries = true)
	@SuppressWarnings("deprecation")
//	@Scheduled(fixedRate = 1000 * 60 * 30)
	@Scheduled(cron = " 0 10 02 * * *")
	@Scheduled(cron = "0 10 05 * * *")
	@Scheduled(cron = "0 10 08 * * *")
	@Scheduled(cron = "0 10 11 * * *")
	@Scheduled(cron = "0 10 14 * * *")
	@Scheduled(cron = "0 10 17 * * *")
	@Scheduled(cron = "0 10 20 * * *")
	@Scheduled(cron = "0 10 23 * * *")
	public void requestWeatherForecastData() throws IOException {
		System.out.println(new Date().toLocaleString() + "--실행--");

		getWeatherForecastData();

	}

	private void getWeatherForecastData() throws IOException {

		HashMap<String, String> areas = new HashMap<>();

		areas.put("area1", "&nx=59&ny=127"); // 서대문구
		areas.put("area2", "&nx=60&ny=126"); // 용산구
		areas.put("area3", "&nx=60&ny=127"); // 종로구
		areas.put("area4", "&nx=62&ny=125"); // 송파구
		areas.put("area5", "&nx=61&ny=127"); // 동대문구
		areas.put("area6", "&nx=61&ny=128"); // 강북구
		areas.put("area7", "&nx=61&ny=125"); // 강남구
		areas.put("area8", "&nx=62&ny=127"); // 중랑구

		for (String val : areas.values()) {

			String serviceKey = "1hxP79MjoaLrUxX2TlyKyk8515EJ2i9d5y7JZzyrDp41qHhdPs4qfHknxxL0UznLYLuI%2FjLDzgMJApTw%2BGm%2B8g%3D%3D";

			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			Calendar base = Calendar.getInstance();
			String baseDate = date.format(base.getTime());

			SimpleDateFormat time = new SimpleDateFormat("HHmm");
			long start = System.currentTimeMillis();
//			time.format(new Date(start)); // 현재 시간

			StringBuilder builder = new StringBuilder();
			builder.append("http://apis.data.go.kr/1360000/VilageFcstInfoService"); // 서비스 주소
			builder.append("/getVilageFcst"); // 상세 기능 주소
			builder.append("?serviceKey=" + serviceKey); // 서비스 키
			builder.append("&numOfRows=12"); // 페이지당 표출 데이터 수 (12개)
			builder.append("&pageNo=1"); // 페이지 수(1페이지)
//			builder.append("&base_date=20210624");
//			builder.append("&base_time=1400");
			builder.append("&base_date=" + baseDate); // 발표일자 현재날짜
			builder.append("&base_time=" + time.format(new Date(start))); // 발표시각 02시00분 부터 시작
			builder.append(val);
			System.out.println(builder.toString());

			URL url = new URL(builder.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			byte[] result = con.getInputStream().readAllBytes();

			String data = new String(result, "UTF-8");
			System.out.println(data);

			JSONObject jObject = XML.toJSONObject(data);
			System.out.println(jObject.toString());

			WeatherForecastResponse response = new Gson().fromJson(jObject.toString(), WeatherForecastResponse.class);
			System.out.println(response);

			for (WeatherForecastResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
				repo.save(new WeatherForecast(item));
			}
		}

	}
}
