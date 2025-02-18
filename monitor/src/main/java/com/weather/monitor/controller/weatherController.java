package com.weather.monitor.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.weather.monitor.model.WeatherResponse;

@Controller
public class weatherController {
	
	@Value("${api.key}")
	private String apiKey;

	
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	@GetMapping("/weather")
	public String getWeather(@RequestParam("city") String city,Model model) {
		System.out.println(apiKey +" : api key");
		System.out.println(city);
	String url = "http://api.openweathermap.org/data/2.5/weather?q="+ city + "&appid=" + apiKey + "&units=metric" ;	
	RestTemplate restTemplate = new RestTemplate();
	WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
	
	if (weatherResponse != null) {
		model.addAttribute("city",weatherResponse.getName());
		model.addAttribute("country",weatherResponse.getSys().getCountry());
		model.addAttribute("weatherDescription",weatherResponse.getWeather().get(0).toString());
		model.addAttribute("temperature",weatherResponse.getMain().getTemp());
		model.addAttribute("humidity",weatherResponse.getMain().getHumidity());
		model.addAttribute("windSpeed",weatherResponse.getWind().getSpeed());
		String weatherIcon = "wi wi-owm-"+weatherResponse.getWeather().get(0).getId();
		model.addAttribute("weatherIcon",weatherIcon);
	}else {
		model.addAttribute("error","city not found");
	}
	
		return "weather";
	}
	
}
