package be.epicode.GestioneDispositivi.services;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import be.epicode.GestioneDispositivi.exceptions.UnauthorizedException;
import be.epicode.GestioneDispositivi.payloads.DipendenteLoginDTO;
import be.epicode.GestioneDispositivi.payloads.NewDipendentePayload;
import be.epicode.GestioneDispositivi.repositories.DipendentiDAO;
import be.epicode.GestioneDispositivi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private DipendentiDAO dipendentiDAO;
    @Autowired
    private PasswordEncoder bcrypt;


    public Dipendente save(NewDipendentePayload body) {
        Dipendente newDipendente = new Dipendente(body.getUsername(),body.getNome(),body.getCognome(),body.getEmail(),
                bcrypt.encode(body.getPassword()) ,body.getAvatarUrl());
//        newDipendente = DipendentePayloadToDipendenteMapper.INSTANCE.map(body);
//        newDipendente.setUsername(body.getUsername());
//        newDipendente.setNome(body.getNome());
//        newDipendente.setCognome(body.getCognome());
//        newDipendente.setEmail(body.getEmail());
//        newDipendente.setPassword(body.getPassword());
//        newDipendente.setAvatarUrl(body.getAvatarUrl());
//        newDipendente.setRuolo(Ruolo.USER);
        return dipendentiDAO.save(newDipendente);
    }

    public String authenticateUserAndGenerateToken(DipendenteLoginDTO payload) {
        Dipendente user = dipendentiService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }


    }


}
