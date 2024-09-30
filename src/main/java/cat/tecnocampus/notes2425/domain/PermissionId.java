package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Embeddable
public class PermissionId implements Serializable {
    private long noteId;
    private long userId;

    public PermissionId() {
    }
    public PermissionId(long noteId, long userId) {
        this.noteId = noteId;
        this.userId = userId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}