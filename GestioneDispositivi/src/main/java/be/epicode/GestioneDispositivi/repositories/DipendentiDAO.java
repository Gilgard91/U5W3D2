package be.epicode.GestioneDispositivi.repositories;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendentiDAO extends JpaRepository<Dipendente, Integer> {
    Optional<Dipendente> findByEmail(String email);
}
