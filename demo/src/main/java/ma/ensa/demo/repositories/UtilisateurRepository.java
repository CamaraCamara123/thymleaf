package ma.ensa.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import ma.ensa.demo.entities.Utilisatuer;

public interface UtilisateurRepository extends CrudRepository<Utilisatuer, Long> {

}
