package com.cfpbubble.cfpbubble.controller;

import com.cfpbubble.cfpbubble.dto.BubbleCreationReponse;
import com.cfpbubble.cfpbubble.dto.BubbleRequest;
import com.cfpbubble.cfpbubble.dto.BubbleResponse;
import com.cfpbubble.cfpbubble.service.BubbleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bubbles")
public class BubbleController {

    @Autowired
    private BubbleService bubbleService;

    @GetMapping("/{publicId}")
    public BubbleResponse getBubbleByPublicId(@PathVariable String publicId) {
        return  bubbleService.getBubbleByPublicId(publicId);
    }

    @GetMapping
    public List<BubbleResponse> getBubblesByEmail(@RequestParam(required = false) String email) {
        if (email != null && !email.isBlank()) {
            return bubbleService.getBubblesByEmail(email);
        }

        return bubbleService.getAllBubbles();
    }

    @PostMapping("/create")
    public BubbleCreationReponse createBubble(@Valid @RequestBody BubbleRequest bubbleRequest) {
        return bubbleService.createBubble(bubbleRequest);
    }

    @GetMapping("/users/count")
    public long getUniqueUsers() {
        return bubbleService.getUniqueUsers();
    }
}
