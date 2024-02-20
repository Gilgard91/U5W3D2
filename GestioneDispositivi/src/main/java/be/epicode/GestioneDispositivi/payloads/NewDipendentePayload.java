package be.epicode.GestioneDispositivi.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewDipendentePayload {
    @NotEmpty(message = "Lo username è obbligatorio!")
    private String username;
    @NotEmpty(message = "Il nome è obbligatorio!")
    private String nome;
    @NotEmpty(message = "Il cognome è obbligatorio!")
    private String cognome;
    @NotEmpty(message = "L'email è obbligatoria!")
    private String email;
    @NotEmpty(message = "La password è obbligatoria!")
    private String password;
    private String avatarUrl;


}
