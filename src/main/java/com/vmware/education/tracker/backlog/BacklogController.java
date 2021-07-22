package com.vmware.education.tracker.backlog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

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

    @GetMapping("{id}")
    ResponseEntity<Story> findById(@PathVariable long id) {
        return ok(repository.findById(id).get());
    }
}
