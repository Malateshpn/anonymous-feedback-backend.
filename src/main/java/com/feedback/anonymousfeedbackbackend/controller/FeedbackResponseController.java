package com.feedback.anonymousfeedbackbackend.controller;

import com.feedback.anonymousfeedbackbackend.model.FeedbackBox;
import com.feedback.anonymousfeedbackbackend.model.FeedbackResponse;
import com.feedback.anonymousfeedbackbackend.repository.FeedbackBoxRepository;
import com.feedback.anonymousfeedbackbackend.repository.FeedbackResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/responses")
@CrossOrigin(origins = "*")
public class FeedbackResponseController {

    @Autowired
    private FeedbackResponseRepository responseRepository;

    @Autowired
    private FeedbackBoxRepository boxRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitResponse(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String message = request.get("message");

        Optional<FeedbackBox> boxOpt = boxRepository.findByCode(code);
        if (boxOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid box code!");
        }

        FeedbackResponse response = new FeedbackResponse(message, boxOpt.get());
        FeedbackResponse savedResponse = responseRepository.save(response);
        return ResponseEntity.ok(savedResponse);
    }

    @GetMapping("/box/{boxId}")
    public List<FeedbackResponse> getResponsesByBox(@PathVariable Long boxId) {
        return responseRepository.findByFeedbackBoxIdOrderByCreatedAtDesc(boxId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@PathVariable Long id) {
        return responseRepository.findById(id).map(response -> {
            responseRepository.delete(response);
            return ResponseEntity.ok("Response deleted successfully");
        }).orElse(ResponseEntity.notFound().build());
    }
}