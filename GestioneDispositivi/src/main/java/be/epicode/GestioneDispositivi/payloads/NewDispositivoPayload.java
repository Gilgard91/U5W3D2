package be.epicode.GestioneDispositivi.payloads;

import be.epicode.GestioneDispositivi.entities.TipologiaDispositivo;
import lombok.Getter;

@Getter
public class NewDispositivoPayload {
    private TipologiaDispositivo tipologiaDispositivo;
    public NewDispositivoPayload(TipologiaDispositivo tipologiaDispositivo) {
        this.tipologiaDispositivo = TipologiaDispositivo.DISPONIBILE;
    }
}
