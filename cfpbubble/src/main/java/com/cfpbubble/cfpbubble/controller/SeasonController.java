package com.cfpbubble.cfpbubble.controller;

import com.cfpbubble.cfpbubble.dto.SeasonResponse;
import com.cfpbubble.cfpbubble.entity.SeasonEntity;
import com.cfpbubble.cfpbubble.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/seasons")
public class SeasonController {

    @Autowired
    SeasonRepository seasonRepository;

    @GetMapping("/current")
    public SeasonResponse getSeason() {
        SeasonEntity seasonEntity = seasonRepository.findByYear(2026).
                orElseThrow(()-> new RuntimeException("SEASON NOT FOUND"));
        return new SeasonResponse(
                seasonEntity.getYear(),
                seasonEntity.getSubmissionOpen(),
                seasonEntity.getSubmissionClose(),
                seasonEntity.getStatus().toString()
        );
    }
}
