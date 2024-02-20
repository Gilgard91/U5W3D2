package be.epicode.GestioneDispositivi.controllers;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import be.epicode.GestioneDispositivi.exceptions.BadRequestException;
import be.epicode.GestioneDispositivi.payloads.DipendenteLoginDTO;
import be.epicode.GestioneDispositivi.payloads.LoginResponseDTO;
import be.epicode.GestioneDispositivi.payloads.NewDipendentePayload;
import be.epicode.GestioneDispositivi.services.AuthService;
import be.epicode.GestioneDispositivi.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendentiService dipendentiService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody DipendenteLoginDTO payload) {
        return new LoginResponseDTO(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveUser(@RequestBody @Validated NewDipendentePayload newUser, BindingResult validation) {
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.authService.save(newUser);
    }
}