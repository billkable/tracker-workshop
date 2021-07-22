package com.vmware.education.tracker.backlog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BacklogControllerTest {
    private BacklogRepository repository;
    private BacklogController controller;

    @BeforeEach
    void setUp() {
        repository = mock(BacklogRepository.class);
        controller = new BacklogController(repository);
    }
    
    @Test
    void createStory() {
        Story storyToCreate =
                new Story(2L,
                        LocalDate.of(2019,11,28),
                        "new story");

        Story storySaved =
                new Story(1L,
                        2L,
                        LocalDate.of(2019,11,28),
                        "new story");

        doReturn(storySaved)
                .when(repository)
                .save(storyToCreate);

        ResponseEntity<Story> storyResponseEntity =
                controller.create(storyToCreate);

        verify(repository)
                .save(storyToCreate);

        assertThat(storyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(storyResponseEntity.getBody()).isEqualTo(storySaved);
    }

    @Test
    void testFindById() {
        Story storyFound =
                new Story(1L,
                        2L,
                        LocalDate.of(2019,11,28),
                        "existing story");

        doReturn(Optional.of(storyFound))
                .when(repository)
                .findById(1L);

        ResponseEntity<Story> storyResponseEntity =
                controller.findById(1L);

        verify(repository)
                .findById(1L);

        assertThat(storyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(storyResponseEntity.getBody()).isEqualTo(storyFound);
    }
}
