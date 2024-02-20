package be.epicode.GestioneDispositivi.controllers;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import be.epicode.GestioneDispositivi.payloads.DipendenteDTOOut;
import be.epicode.GestioneDispositivi.payloads.NewDipendentePayload;
import be.epicode.GestioneDispositivi.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    @Autowired
    DipendentiService dipendentiService;


//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Dipendente saveDipendente(@RequestBody @Validated NewDipendentePayload body, BindingResult validation){
//        if(validation.hasErrors()){
//            throw new BadRequestException(validation.getAllErrors());
//        }
//        return this.dipendentiService.save(body);
//    }

    @GetMapping("")
    public Page<DipendenteDTOOut> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "nome") String orderBy) {
        return this.dipendentiService.getDipendenti(page, size, orderBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable int dipendenteId){
        return dipendentiService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findAndUpdate(@PathVariable int dipendenteId, @RequestBody NewDipendentePayload body){
        return dipendentiService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable int dipendenteId){
        dipendentiService.findByIdAndDelete(dipendenteId);
    }

    @PutMapping("/{dipendenteId}/dispositivi")
    public Dipendente assegnaDispositivi(@PathVariable int dipendenteId, @RequestBody List<Integer> dispositivi){
        return dipendentiService.assegnaDispositivi(dipendenteId, dispositivi);
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile img) throws IOException {
        return this.dipendentiService.uploadImg(img);
    }

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public Dipendente getMeAndUpdate(@AuthenticationPrincipal Dipendente currentAuthenticatedUser, @RequestBody NewDipendentePayload updatedUser) {
        return this.dipendentiService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal Dipendente currentAuthenticatedUser) {
        this.dipendentiService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }
}
