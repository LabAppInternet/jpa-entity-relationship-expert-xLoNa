package cat.tecnocampus.notes2425.application.dtos;

public record PermissionCreation(long allowedUserId, long noteId, boolean canView, boolean canEdit) {
}
