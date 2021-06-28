package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

	private WeatherForecastRepository repo;

	public WeatherForecastController(WeatherForecastRepository repo) {
		this.repo = repo;
	}

	// @Cacheable 리턴 객체를 캐시함
	// cacheNames: 캐시할 객체의 명칭(임의로 정함)
	// key: 캐시할 객체의 key
	@Cacheable(cacheNames = "weatherForecast-list", key = "0")
	@RequestMapping(value = "/opendata/weatherforecast", method = RequestMethod.GET)
	public List<WeatherForecast> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "baseDate"), new Order(Sort.Direction.DESC, "baseTime"),
				new Order(Sort.Direction.ASC, "category") };

		return repo.findAll(PageRequest.of(0, 80, Sort.by(orders))).toList();
	}
}
