package com.backend.vaccinebookingsystem.domain.common;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FamilyBookingKey implements Serializable {

    private Long bookingId;

    private Long familyId;

}
