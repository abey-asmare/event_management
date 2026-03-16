package com.management.event_management.application.event.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelEventCommand {

    private UUID eventId;
}