package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserLab owner;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags;

    public Note(Object o, UserLab owner, String title, String content, LocalDateTime now, Set<Tag> collect) {
    }

    public long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String content() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    public UserLab getOwner() {
        return owner;
    }

    public void setOwner(UserLab owner) {
        this.owner = owner;
    }

    public Set<Tag> tags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.addNote(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.removeNote(this);
    }

    public boolean isOwner(long id) {
        return this.owner.getId() == id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
