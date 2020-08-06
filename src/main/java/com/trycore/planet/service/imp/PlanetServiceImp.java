package com.trycore.planet.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.trycore.commons.dto.PlanetDTO;
import com.trycore.commons.dto.PlanetResponseDTO;
import com.trycore.commons.exception.CustomException;
import com.trycore.commons.messages.MessagesEnum;
import com.trycore.commons.model.Planet;
import com.trycore.planet.repository.PlanetRepository;
import com.trycore.planet.service.PlanetService;

@Service
public class PlanetServiceImp implements PlanetService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PlanetRepository planetRepository;

	@Override
	public PlanetResponseDTO addPlanet(PlanetDTO planetDTO) {
		Planet planet = new Planet();
		Optional<Planet> checkPlanet = planetRepository.findByPlanetName(planetDTO.getPlanetName());
		if (checkPlanet.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, MessagesEnum.PLANET_ALREADY_EXIST,
					planetDTO.getPlanetName());
		}
		modelMapper.map(planetDTO, planet);
		planet.setViewsCounter(0L);
		planetRepository.save(planet);
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description);
	}

	@Override
	public PlanetResponseDTO getPlanet(Long idPlanet) {
		Optional<Planet> planet = planetRepository.findById(idPlanet);
		PlanetDTO planetDTO = new PlanetDTO();
		List<PlanetDTO> planets = new ArrayList<>();
		if (!planet.isPresent()) {
			throw new CustomException(HttpStatus.NOT_FOUND, MessagesEnum.PLANET_NOT_FOUND, Long.toString(idPlanet));
		}
		modelMapper.map(planet.get(), planetDTO);
		planets.add(planetDTO);
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description, planets);
	}

	@Override
	public PlanetResponseDTO updatePlanet(PlanetDTO planetDTO) {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Optional<Planet> checkPlanet = planetRepository.findByPlanetName(planetDTO.getPlanetName());
		if (!checkPlanet.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, MessagesEnum.PLANET_NOT_FOUND, planetDTO.getPlanetName());
		}
		modelMapper.map(planetDTO, checkPlanet.get());
		checkPlanet.get().setViewsCounter(0L);
		planetRepository.save(checkPlanet.get());
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description);
	}

	@Override
	public PlanetResponseDTO disable(PlanetDTO planetDTO) {
		Optional<Planet> checkPlanet = planetRepository.findByPlanetName(planetDTO.getPlanetName());
		if (checkPlanet.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, MessagesEnum.PLANET_ALREADY_EXIST,
					planetDTO.getPlanetName());
		}
		checkPlanet.get().setEnabled(false);
		planetRepository.save(checkPlanet.get());
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description);
	}

	@Override
	public PlanetResponseDTO increaseCounterPlanetViews(Long idPlanet) {
		Optional<Planet> checkPlanet = planetRepository.findById(idPlanet);
		if (!checkPlanet.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, MessagesEnum.PLANET_NOT_FOUND);
		}
		checkPlanet.get().setViewsCounter(checkPlanet.get().getViewsCounter() + 1);
		planetRepository.save(checkPlanet.get());
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description);
	}

	@Override
	public PlanetResponseDTO getAllPlanets() {
		List<Planet> planets = planetRepository.findAll();
		List<PlanetDTO> planetsDTO = planets.stream().map(planet -> modelMapper.map(planet, PlanetDTO.class))
				.collect(Collectors.toList());
		return new PlanetResponseDTO(MessagesEnum.SUCCESFULLY_OPERATION.code,
				MessagesEnum.SUCCESFULLY_OPERATION.description, planetsDTO);
	}

}
