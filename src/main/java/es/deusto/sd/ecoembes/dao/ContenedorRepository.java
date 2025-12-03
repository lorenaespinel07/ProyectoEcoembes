package es.deusto.sd.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import es.deusto.sd.ecoembes.entity.Contenedor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long>{

}
