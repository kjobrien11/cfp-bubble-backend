package com.cfpbubble.cfpbubble.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BubbleRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotEmpty @Size(min=1, max = 20)
        List<Integer> teams

) {}
