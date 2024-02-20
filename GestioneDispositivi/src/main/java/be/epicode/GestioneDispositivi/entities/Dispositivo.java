package be.epicode.GestioneDispositivi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dispositivi")
public class Dispositivo {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "tipologia_dispositivo")
    @Enumerated(EnumType.STRING)
    private TipologiaDispositivo tipologiaDispositivo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    public Dispositivo(TipologiaDispositivo tipologiaDispositivo, Dipendente dipendente) {
        this.tipologiaDispositivo = TipologiaDispositivo.DISPONIBILE;
        this.dipendente = dipendente;
    }
}
