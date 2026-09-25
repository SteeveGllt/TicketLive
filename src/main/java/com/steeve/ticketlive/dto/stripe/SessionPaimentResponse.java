package com.steeve.ticketlive.dto.stripe;

import lombok.Data;

@Data
public class SessionPaimentResponse {
    private String checkoutUrl;
}
