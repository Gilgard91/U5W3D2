package be.epicode.GestioneDispositivi.services;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import be.epicode.GestioneDispositivi.entities.Dispositivo;
import be.epicode.GestioneDispositivi.entities.TipologiaDispositivo;
import be.epicode.GestioneDispositivi.exceptions.NotFoundException;
import be.epicode.GestioneDispositivi.payloads.DipendenteDTOOut;
import be.epicode.GestioneDispositivi.payloads.NewDipendentePayload;
import be.epicode.GestioneDispositivi.repositories.DipendentiDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DipendentiService {

    @Autowired
    DipendentiDAO dipendentiDAO;
    @Autowired
    Cloudinary cloudinaryUploader;
    @Autowired
    DispositiviService dispositiviService;




    public Dipendente assegnaDispositivi(int dipendenteId, List<Integer> dispositiviIds) {
        Dipendente dipendente = findById(dipendenteId);
        List<Dispositivo> dispositivi = dispositiviService.getDispositiviById(dispositiviIds);
        dispositivi.forEach(dispositivo -> {
            dispositivo.setDipendente(dipendente);
            dispositivo.setTipologiaDispositivo(TipologiaDispositivo.ASSEGNATO);
        });
        dipendente.getDispositivi().addAll(dispositivi);
        return dipendentiDAO.save(dipendente);
    }

    public Page<DipendenteDTOOut> getDipendenti(int pageNumber, int size, String orderBy) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        Page<Dipendente> dipendenti = dipendentiDAO.findAll(pageable);
        return dipendenti.map(dipendente -> {
           return new DipendenteDTOOut(dipendente.getUsername(),
                    dipendente.getNome(), dipendente.getCognome(), dipendente.getEmail(), dipendente.getPassword(),
                    dipendente.getDispositivi());
        });
    }

    public Dipendente findById(int id) {
        return dipendentiDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Dipendente findByEmail(String email){
        return dipendentiDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Email " + email + " non trovata"));
    }

    public void findByIdAndDelete(int id) {
        Dipendente found = this.findById(id);
        dipendentiDAO.delete(found);
    }

    public Dipendente findByIdAndUpdate(int id, NewDipendentePayload body) {
        Dipendente found = this.findById(id);
        found.setUsername(body.getUsername());
        found.setEmail(body.getEmail());
        found.setAvatarUrl(body.getAvatarUrl());
        return dipendentiDAO.save(found);

    }

    public String uploadImg(MultipartFile img) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }
}
