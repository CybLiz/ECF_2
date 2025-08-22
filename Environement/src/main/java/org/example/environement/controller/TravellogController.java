package org.example.environement.controller;

import org.example.environement.dto.specie.SpecieDtoReceive;
import org.example.environement.dto.specie.SpecieDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.service.TravellogsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/travel-logs")
public class TravellogController {

    private final TravellogsService travellogsService;

    public TravellogController(TravellogsService travellogsService) {
        this.travellogsService = travellogsService;
    }

    @PostMapping()
    public ResponseEntity<TravellogDtoResponse> createTravellog (@RequestBody TravellogDtoReceive travellogDtoReceive){
        return ResponseEntity.status(HttpStatus.CREATED).body(travellogsService.create(travellogDtoReceive));
    }

    @GetMapping()
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs (
            @RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "10") int pageNumber){
        return ResponseEntity.ok(travellogsService.get(pageSize,pageNumber));
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<TravellogDtoStat> getStatFromObseration (@PathVariable long id){
        return ResponseEntity.ok(travellogsService.getStat(id));
    }

//    @GetMapping("/user/{name}")
//    public ResponseEntity<Map<String,TravellogDtoStat>> getTravelStatForUserOnLAstMonth (@PathVariable String name){
//        return ResponseEntity.ok(travellogsService.getStatForUserLastMonth(name));
//    }
}
