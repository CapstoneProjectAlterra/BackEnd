package com.backend.vaccinebookingsystem.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FamilyBookingKey implements Serializable {

    @Column(name = "family_id")
    private Long familyId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamilyBookingKey)) return false;
        FamilyBookingKey that = (FamilyBookingKey) o;
        return getFamilyId().equals(that.getFamilyId()) && getBookingId().equals(that.getBookingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFamilyId(), getBookingId());
    }
}
