package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.*;

@Entity
public class Permission {
    @EmbeddedId
    private PermissionId id;
    private boolean canView;
    private boolean canEdit;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noteId")
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserLab allowed;

    public Permission(UserLab allowed, Note note) {
        this.id = new PermissionId(note.getId(), allowed.getId());
        this.note = note;
        this.allowed = allowed;
    }

    public Permission() {

    }

    public PermissionId getId() {
        return id;
    }

    public void setId(PermissionId id) {
        this.id = id;
    }

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public UserLab getAllowed() {
        return allowed;
    }

    public void setAllowed(UserLab owner) {
        this.allowed = owner;
    }
}
