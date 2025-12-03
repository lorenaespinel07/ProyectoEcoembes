package es.deusto.sd.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.PlantaReciclaje;

@Repository
public interface PlantaReciclajeRepository extends JpaRepository<PlantaReciclaje, Long>{

}
