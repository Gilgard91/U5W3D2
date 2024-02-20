package be.epicode.GestioneDispositivi.map;

import be.epicode.GestioneDispositivi.entities.Dipendente;
import be.epicode.GestioneDispositivi.payloads.NewDipendentePayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DipendentePayloadToDipendenteMapper {
    DipendentePayloadToDipendenteMapper INSTANCE = Mappers.getMapper(DipendentePayloadToDipendenteMapper.class);

//    @Mapping(source = "username", target = "nomeutente")
    Dipendente map(NewDipendentePayload newDipendente);


}
