package com.citalacki_dnevnik.server.event.listener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 Basic event for Event listener
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TestEvent {

    private String message;
}
