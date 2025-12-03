package es.deusto.sd.ecoembes.dao;

import org.springframework.stereotype.Repository;

import es.deusto.sd.ecoembes.entity.Personal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>{
	Optional<Personal> findByEmail(String email);
}
