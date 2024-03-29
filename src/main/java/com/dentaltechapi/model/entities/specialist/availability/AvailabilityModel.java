package com.dentaltechapi.model.entities.specialist.availability;

import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@Entity(name = "tb_availability")
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(name = "availability_days", joinColumns = @JoinColumn(name = "availability_id"))
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "is_available")
    private Map<String, Boolean> availability;

    @ElementCollection
    @CollectionTable(name = "availability_times", joinColumns = @JoinColumn(name = "availability_id"))
    @Column(name = "time_slot", nullable = false)
    private Set<LocalDateTime> timeSlots;

    @OneToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistModel specialist;
}
