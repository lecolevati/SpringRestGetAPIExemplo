package br.com.leandrocolevati.timezones.controller.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leandrocolevati.timezones.model.TZ;

@RestController
@RequestMapping("/api")
public class TimezoneServices {

	@GetMapping("/timezonesId")
	public List<String> getTimezones() {
		return ZoneId.getAvailableZoneIds()
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	@GetMapping("/worldDateTime")
	public List<TZ> getHorariosMundiais() {
		List<String> timezonesId = getTimezones();
		List<TZ> listHorariosMundiais = new ArrayList<TZ>();
		
		for (String id : timezonesId) {
			listHorariosMundiais.add(geraTz(id));
		}
		
		return listHorariosMundiais;
	}

	@GetMapping("/worldDateTime/{id}")
	public ResponseEntity<TZ> getHorarioTimezone(@PathVariable(value = "id") String id) {
		try {
			return ResponseEntity.ok().body(geraTz(id));
		} catch (ZoneRulesException e) {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping("/worldDateTime/{id1}/{id2}")
	public ResponseEntity<TZ> getHorarioTimezone(@PathVariable(value = "id1") String id1, @PathVariable(value = "id2") String id2) {
		String id = id1 + "/" + id2;
		try {
			return ResponseEntity.ok().body(geraTz(id));
		} catch (ZoneRulesException e) {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/worldDateTime/{id1}/{id2}/{id3}")
	public ResponseEntity<TZ> getHorarioTimezone(@PathVariable(value = "id1") String id1, @PathVariable(value = "id2") String id2, @PathVariable(value = "id3") String id3) {
		String id = id1 + "/" + id2 + "/" + id3;
		try {
			return ResponseEntity.ok().body(geraTz(id));
		} catch (ZoneRulesException e) {
			return ResponseEntity.notFound().build();
		}

	}
	
	private TZ geraTz(String id) {
		LocalDate localDate = LocalDate.now(ZoneId.of(id)); 
		LocalTime localTime = LocalTime.now(ZoneId.of(id));
		
		TZ tz = new TZ();
		tz.setDt(localDate.toString());
		tz.setHr(localTime.toString());
		tz.setTz(id);
		return tz;
	}
	
}
