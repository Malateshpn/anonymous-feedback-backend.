package com.feedback.anonymousfeedbackbackend.repository;

import com.feedback.anonymousfeedbackbackend.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface FeedbackResponseRepository extends JpaRepository<FeedbackResponse, Long> {

    // Find responses for a box ordered by creation date (newest first)
    List<FeedbackResponse> findByFeedbackBoxIdOrderByCreatedAtDesc(Long boxId);

    // Standard lookup by box ID
    List<FeedbackResponse> findByFeedbackBoxId(Long boxId);

    // Used for deleting all responses when a box is deleted
    @Transactional
    void deleteByFeedbackBoxId(Long boxId);
}