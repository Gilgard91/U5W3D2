package be.epicode.GestioneDispositivi.repositories;

import be.epicode.GestioneDispositivi.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositiviDAO extends JpaRepository<Dispositivo, Integer> {
}
