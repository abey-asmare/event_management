package com.management.event_management.domain.entities.venue;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.shared.Address;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "venues")
public class Venue extends BaseEntity {
    private String name;
    private int capacity;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events;

}