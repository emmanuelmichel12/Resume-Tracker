package com.jobtracker.application_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications", schema = "trackingservice")
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "jobname", length = 200)
    private String jobName;

    @Column(name = "jobrole", length = 200)
    private String jobRole;

    @Column(name = "joburl", length = 500)
    private String jobUrl;

    @Column(name = "jobstatus", length = 100)
    private String jobStatus;

    @Column(name = "dateapplied")
    private LocalDate dateApplied;

    @Column(name = "notes")
    private String notes;

    @CreationTimestamp
    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

}
