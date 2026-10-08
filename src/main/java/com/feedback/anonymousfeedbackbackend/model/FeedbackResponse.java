package com.feedback.anonymousfeedbackbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_responses")
public class FeedbackResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private FeedbackBox feedbackBox;

    public FeedbackResponse() {}

    public FeedbackResponse(String message, FeedbackBox feedbackBox) {
        this.message = message;
        this.feedbackBox = feedbackBox;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public FeedbackBox getFeedbackBox() { return feedbackBox; }
    public void setFeedbackBox(FeedbackBox feedbackBox) { this.feedbackBox = feedbackBox; }
}