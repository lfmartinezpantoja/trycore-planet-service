package com.trycore.planet.service;

import com.trycore.commons.dto.PlanetDTO;
import com.trycore.commons.dto.PlanetResponseDTO;

public interface PlanetService {

	public PlanetResponseDTO addPlanet(PlanetDTO planetDTO);

	public PlanetResponseDTO getPlanet(Long idPlanet);
	
	public PlanetResponseDTO getAllPlanets();

	public PlanetResponseDTO updatePlanet(PlanetDTO planetDTO);

	public PlanetResponseDTO disable(PlanetDTO planetDTO);

	public PlanetResponseDTO increaseCounterPlanetViews(Long idPlanet);
}
