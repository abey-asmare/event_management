package com.management.event_management.domain.entities.event;

import com.management.event_management.domain.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter
@Setter
public class Seat extends BaseEntity {

    private String rowLabel;

@Column(name = "seat_number")
    private int seatNumber;
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}