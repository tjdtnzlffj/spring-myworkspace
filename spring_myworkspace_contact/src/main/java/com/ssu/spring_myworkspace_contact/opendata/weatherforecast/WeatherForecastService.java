package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
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

	@SuppressWarnings("deprecation")
//	@Scheduled(cron = " 0 0 * * * *")
	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void requestWeatherForecastData() throws IOException {
		System.out.println(new Date().toLocaleString() + "--����--");

		getWeatherForecastData("POP"); // ����Ȯ��
		getWeatherForecastData("REH"); // ����
		getWeatherForecastData("SKY"); // �ϴû���
		getWeatherForecastData("T3H"); // 3�ð� ���
	}

	private void getWeatherForecastData(String category) throws IOException {

		String serviceKey = "1hxP79MjoaLrUxX2TlyKyk8515EJ2i9d5y7JZzyrDp41qHhdPs4qfHknxxL0UznLYLuI%2FjLDzgMJApTw%2BGm%2B8g%3D%3D";

		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/1360000/VilageFcstInfoService"); // ���� �ּ�
		builder.append("/getVilageFcst"); // �� ��� �ּ�
		builder.append("?serviceKey=" + serviceKey); // ���� Ű
		builder.append("&numOfRows=9"); // �������� ǥ�� ������ �� (9��)
		builder.append("&pageNo=1"); // ������ ��(1������)
		builder.append("&base_date=20210616"); // ��ǥ���� 06��15��
		builder.append("&base_time=0230"); // ��ǥ�ð� 02��30�� ��ǥ
		builder.append("&nx=55"); // �������� x ��ǥ
		builder.append("&ny=127"); // �������� y ��ǥ
		builder.append("&dataType=JSON");

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

	}
}
