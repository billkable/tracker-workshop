package com.vmware.education.tracker.backlog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

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
        Optional<Story> storyFound = repository.findById(id);

        return storyFound.isEmpty()?
                notFound().build():
                ok(storyFound.get());
    }
}
