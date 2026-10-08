package com.feedback.anonymousfeedbackbackend.controller;

import com.feedback.anonymousfeedbackbackend.model.FeedbackBox;
import com.feedback.anonymousfeedbackbackend.model.User;
import com.feedback.anonymousfeedbackbackend.repository.FeedbackBoxRepository;
import com.feedback.anonymousfeedbackbackend.repository.FeedbackResponseRepository;
import com.feedback.anonymousfeedbackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/boxes")
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class FeedbackBoxController {

    @Autowired
    private FeedbackBoxRepository boxRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackResponseRepository responseRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBox(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String description = request.get("description");
        Long userId = Long.parseLong(request.get("userId"));

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        FeedbackBox box = new FeedbackBox(title, description, code, userOpt.get());
        FeedbackBox savedBox = boxRepository.save(box);
        return ResponseEntity.ok(savedBox);
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackBox> getUserBoxes(@PathVariable Long userId) {
        return boxRepository.findByUserId(userId);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getBoxByCode(@PathVariable String code) {
        return boxRepository.findByCode(code)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid box code!"));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteBox(@PathVariable Long id) {
        return boxRepository.findById(id).map(box -> {
            responseRepository.deleteByFeedbackBoxId(id);
            boxRepository.delete(box);
            return ResponseEntity.ok("Box deleted successfully");
        }).orElse(ResponseEntity.notFound().build());
    }
}