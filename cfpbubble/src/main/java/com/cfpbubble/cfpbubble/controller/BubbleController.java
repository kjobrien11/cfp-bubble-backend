package com.cfpbubble.cfpbubble.controller;

import com.cfpbubble.cfpbubble.dto.BubbleCreationReponse;
import com.cfpbubble.cfpbubble.dto.BubbleRequest;
import com.cfpbubble.cfpbubble.dto.BubbleResponse;
import com.cfpbubble.cfpbubble.exception.RateLimitExceededException;
import com.cfpbubble.cfpbubble.service.BubbleService;
import com.cfpbubble.cfpbubble.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bubbles")
public class BubbleController {


    private final BubbleService bubbleService;
    private final RateLimitService rateLimitService;

    public BubbleController(
            BubbleService bubbleService,
            RateLimitService rateLimitService) {

        this.bubbleService = bubbleService;
        this.rateLimitService = rateLimitService;
    }

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
    public BubbleCreationReponse createBubble(@Valid @RequestBody BubbleRequest bubbleRequest, HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddr();

        if (!rateLimitService.allowSubmission(ipAddress)) {
            throw new RateLimitExceededException(
                    "Too many bubble submissions. Please try again later."
            );
        }

        return bubbleService.createBubble(bubbleRequest);
    }

    @GetMapping("/users/count")
    public long getUniqueUsers() {
        return bubbleService.getUniqueUsers();
    }
}
