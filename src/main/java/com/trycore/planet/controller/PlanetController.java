package com.trycore.planet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trycore.commons.dto.PlanetDTO;
import com.trycore.commons.dto.PlanetResponseDTO;
import com.trycore.planet.service.PlanetService;

@RestController
public class PlanetController {

	@Autowired
	PlanetService planetService;

	@PostMapping("planets")
	public ResponseEntity<PlanetResponseDTO> addPlanet(@RequestBody PlanetDTO planetDTO) {
		return new ResponseEntity<>(planetService.addPlanet(planetDTO), HttpStatus.CREATED);
	}
	@GetMapping("planets/{idPlanet}")
	public ResponseEntity<PlanetResponseDTO> getPlanet(@PathVariable Long idPlanet) {
		return new ResponseEntity<>(planetService.getPlanet(idPlanet), HttpStatus.CREATED);
	}
	@GetMapping("planets")
	public ResponseEntity<PlanetResponseDTO> getAllPlanet() {
		return new ResponseEntity<>(planetService.getAllPlanets(), HttpStatus.CREATED);
	}

}
