package com.cfpbubble.cfpbubble.controller;

import com.cfpbubble.cfpbubble.dto.BubbleRequest;
import com.cfpbubble.cfpbubble.dto.BubbleResponse;
import com.cfpbubble.cfpbubble.entity.BubbleEntity;
import com.cfpbubble.cfpbubble.service.BubbleService;
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
    public List<BubbleResponse> getBubblesByEmail(@RequestParam String email) {
        return bubbleService.getBubblesByEmail(email);
    }

    @PostMapping("/create")
    public String createBubble(@RequestBody BubbleRequest bubbleRequest) {
        return bubbleService.createBubble(bubbleRequest);
    }
}
