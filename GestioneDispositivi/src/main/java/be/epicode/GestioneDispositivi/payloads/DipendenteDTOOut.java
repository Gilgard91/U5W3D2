package be.epicode.GestioneDispositivi.payloads;

import be.epicode.GestioneDispositivi.entities.Dispositivo;

import java.util.Set;

public record DipendenteDTOOut(String username, String nome, String cognome, String email,
                               String password, Set<Dispositivo> dispositivi) {
}
