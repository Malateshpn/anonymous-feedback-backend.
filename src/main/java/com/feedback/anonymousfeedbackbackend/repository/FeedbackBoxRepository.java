package com.feedback.anonymousfeedbackbackend.repository;

import com.feedback.anonymousfeedbackbackend.model.FeedbackBox;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FeedbackBoxRepository extends JpaRepository<FeedbackBox, Long> {
    List<FeedbackBox> findByUserId(Long userId);
    Optional<FeedbackBox> findByCode(String code);
}