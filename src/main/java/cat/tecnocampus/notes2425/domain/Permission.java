package cat.tecnocampus.notes2425.domain;

public record Permission(UserLab owner, Note note, boolean canRead, boolean canEdit) {
}
