package br.com.dcasimulator.repository;

import br.com.dcasimulator.entity.SimulationResult; // <--- This import is key!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationResultRepository extends JpaRepository<SimulationResult, Long> {
}