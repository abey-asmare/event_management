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
    private int number;
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}