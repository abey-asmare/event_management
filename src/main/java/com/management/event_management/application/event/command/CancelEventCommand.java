package com.management.event_management.application.event.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelEventCommand {

    private Long eventId;
}