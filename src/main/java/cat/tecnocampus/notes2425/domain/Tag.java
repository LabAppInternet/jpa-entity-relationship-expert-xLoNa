package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {

    @Id
    private String name;

    @ManyToMany(mappedBy = "tags")
    Set<Note> notes;

    public Tag(String name) {
        this.name = name;
        this.notes = new HashSet<>();
    }

    public Tag() {
    }

    public String name() {
        return name;
    }

    public Set<Note> notes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}