package cat.tecnocampus.notes2425.application.exceptions;

public class UserHasNoEditPermission extends RuntimeException {
    public UserHasNoEditPermission(long userId, long noteId) {
        super("User with userId " + userId + " is not allowed to edit the note with noteId " + noteId);
    }
}
