package be.epicode.GestioneDispositivi.services;

import be.epicode.GestioneDispositivi.entities.Dispositivo;
import be.epicode.GestioneDispositivi.entities.TipologiaDispositivo;
import be.epicode.GestioneDispositivi.exceptions.NotFoundException;
import be.epicode.GestioneDispositivi.repositories.DispositiviDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositiviService {

    @Autowired
    DispositiviDAO dispositiviDAO;
//    @Autowired
//    DipendentiService dipendentiService;

    public Dispositivo save(){
        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setTipologiaDispositivo(TipologiaDispositivo.DISPONIBILE);

        return dispositiviDAO.save(newDispositivo);
    }

    public Page<Dispositivo> getDispositivi(int pageNumber, int size, String orderBy){
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return dispositiviDAO.findAll(pageable);
    }

    public List<Dispositivo> getDispositiviById(List<Integer> dispositiviIds){
        return dispositiviDAO.findAllById(dispositiviIds);
    }

    public Dispositivo findById(int id){
        return dispositiviDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id){
        Dispositivo found = this.findById(id);
        found.setTipologiaDispositivo(TipologiaDispositivo.DISPONIBILE);
        dispositiviDAO.delete(found);
    }

    public Dispositivo findByIdAndUpdate(int id){
        Dispositivo found = this.findById(id);
        TipologiaDispositivo currentTipologia = found.getTipologiaDispositivo();

        if (currentTipologia == TipologiaDispositivo.ASSEGNATO) {
            found.setTipologiaDispositivo(TipologiaDispositivo.IN_MANUTENZIONE);
        } else if (currentTipologia == TipologiaDispositivo.IN_MANUTENZIONE) {
            found.setTipologiaDispositivo(TipologiaDispositivo.ASSEGNATO);
        }
        return dispositiviDAO.save(found);
    }
}
