package org.example.environement.service;

import org.example.environement.dto.specie.SpecieDtoReceive;
import org.example.environement.dto.specie.SpecieDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.entity.Specie;
import org.example.environement.entity.Travellog;
import org.example.environement.exception.NotFoundException;
import org.example.environement.repository.SpecieRepositoryPaginate;
import org.example.environement.repository.TravellogRepository;
import org.example.environement.repository.TravellogRepositorypaginate;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravellogsService {

    private final TravellogRepository travellogRepository;
    private final TravellogRepositorypaginate travellogRepositorypaginate;


    public TravellogsService(TravellogRepository travellogRepository, TravellogRepositorypaginate travellogRepositorypaginate) {
        this.travellogRepository = travellogRepository;
        this.travellogRepositorypaginate = travellogRepositorypaginate;
    }

    public TravellogDtoResponse create (TravellogDtoReceive travellogDtoReceive){
        return travellogRepository.save(travellogDtoReceive.dtoToEntity()).entityToDto();
    }

    public List<TravellogDtoResponse> get(int pageSize, int pageNumber) {
      return travellogRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().map(Travellog::entityToDto).collect(Collectors.toList());
    }


    public TravellogDtoStat getStat (long id) {
        List<Travellog> logs = travellogRepository.findTravellogByObservation_Id(id);
        TravellogDtoStat stat = new TravellogDtoStat();

        for (Travellog log : logs) {
            stat.addTotalDistanceKm(log.getDistanceKm());
            stat.addTotalEmissionsKg(log.getEstimatedCo2Kg());

            String mode = log.getMode().toString();
            Double distanceKm = log.getDistanceKm();

            if (stat.getByMode().containsKey(mode)) {
                stat.getByMode().put(mode, stat.getByMode().get(mode) + distanceKm);
            } else {
                stat.getByMode().put(mode, distanceKm);
            }
        }
        return stat;
    }

//    public TravellogDtoStat getStatForUserLastMonth (@PathVariable String observerName){
//    }


}
