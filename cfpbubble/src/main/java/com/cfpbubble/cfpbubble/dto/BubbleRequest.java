package com.cfpbubble.cfpbubble.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BubbleRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotEmpty
        List<Integer> teams

) {}
