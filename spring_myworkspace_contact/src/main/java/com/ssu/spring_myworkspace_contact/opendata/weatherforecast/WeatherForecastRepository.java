package com.ssu.spring_myworkspace_contact.opendata.weatherforecast;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, WeatherForecastId> {

}
