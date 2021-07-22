package com.vmware.education.tracker.backlog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/backlog")
class BacklogController {
    private final BacklogRepository repository;

    BacklogController(BacklogRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<Story> create(@RequestBody Story storyToCreate) {
        return created(null)
                .body(repository.save(storyToCreate));
    }
}
