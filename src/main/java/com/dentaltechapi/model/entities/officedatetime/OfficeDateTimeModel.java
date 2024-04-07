package com.dentaltechapi.model.entities.officedatetime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity(name = "tb_office_date_time")
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDateTimeModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer day;

    @ElementCollection
    private List<LocalTime> schedules;
}
