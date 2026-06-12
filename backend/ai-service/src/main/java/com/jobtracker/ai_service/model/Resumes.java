package com.jobtracker.ai_service.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resumes", schema = "aiservice")
public class Resumes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "applicationid")
    private Long applicationId;

    @Column(name = "s3url", length = 500)
    private String s3Url;

    @Column(name = "resumetext")
    private String resumeText;

    @Column(name = "jobdescription")
    private String jobDescription;

    @Column(name = "aioutput")
    private String aiOutput;

    @CreationTimestamp
    @Column(name = "createdat")
    private LocalDateTime createdAt;
}
