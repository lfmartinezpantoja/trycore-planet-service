package com.trycore.planet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trycore.commons.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

	public Optional<Planet> findByPlanetName(String planetName);
}
