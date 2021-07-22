package com.vmware.education.tracker.backlog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BacklogIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreate() {
        Story storyToCreate =
                new Story(2L,
                        LocalDate.of(2019,11,28),
                        "new story");

        ResponseEntity<Story> storyResponseEntity =
                restTemplate.postForEntity("/backlog", storyToCreate, Story.class);

        assertThat(storyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(storyResponseEntity.getBody()).isNotNull();

        Story storySaved = storyResponseEntity.getBody();

        assertThat(storySaved.getId()).isGreaterThan(0L);
        assertThat(storySaved.getProjectId()).isEqualTo(storyToCreate.getProjectId());
        assertThat(storySaved.getCreateDate()).isEqualTo(storyToCreate.getCreateDate());
        assertThat(storySaved.getTitle()).isEqualTo(storyToCreate.getTitle());
    }

    @Test
    void testFind() {
        Story storyCreated = createStory(
                new Story(
                        22L,
                        LocalDate.of(2019,11,28),
                        "new story"
                )
        );

        ResponseEntity<Story> storyResponseEntity =
                restTemplate.getForEntity("/backlog/" + storyCreated.getId(),
                        Story.class);

        assertThat(storyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(storyResponseEntity.getBody()).isEqualTo(storyCreated);
    }

    @Test
    void testFind_notFound() {
        ResponseEntity<Story> storyResponseEntity =
                restTemplate.getForEntity("/backlog/0",
                        Story.class);

        assertThat(storyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Story createStory(Story storyToCreate) {
        return restTemplate
                .postForEntity("/backlog", storyToCreate, Story.class)
                .getBody();
    }
}
