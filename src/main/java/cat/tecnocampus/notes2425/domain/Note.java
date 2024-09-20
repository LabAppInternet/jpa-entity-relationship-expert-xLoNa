package cat.tecnocampus.notes2425.domain;

import java.time.LocalDateTime;
import java.util.Set;

public record Note(Long id, UserLab owner, String title, String content, LocalDateTime creationDate, Set<Tag> tags) {
}
