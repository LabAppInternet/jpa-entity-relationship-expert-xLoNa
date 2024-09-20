package cat.tecnocampus.notes2425.application.dtos;

import java.util.Set;

public record CreateNoteDTO(String title, String content, Set<String> tags) {
}
