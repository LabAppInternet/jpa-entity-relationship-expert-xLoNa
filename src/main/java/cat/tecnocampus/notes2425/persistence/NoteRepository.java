package cat.tecnocampus.notes2425.persistence;

import cat.tecnocampus.notes2425.application.dtos.CreateNoteDTO;
import cat.tecnocampus.notes2425.application.dtos.NoteDTO;
import cat.tecnocampus.notes2425.domain.Note;
import cat.tecnocampus.notes2425.domain.Tag;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class NoteRepository {
    private final JdbcClient jdbcClient;

    public NoteRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<NoteDTO> findUserNotes(long ownerId) {
        String query = """ 
            select n.id, n.title, n.content, n.creation_Date, null as tags
            from note n 
            where owner_id = :ownerId
        """;
        return jdbcClient.sql(query).param("ownerId",ownerId).query(NoteDTO.class).list();
    }


    public Set<String> getNoteTags(long noteId) {
        String query = "select nt.tag_name from note_tag nt where nt.note_id = ?";
        return new HashSet<>(jdbcClient.sql(query).param(noteId).query(String.class).list());
    }

    public Long save(Note note) {
        String query = "insert into note (owner_id, title, content, creation_date) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(query)
                .param(note.owner().id())
                .param(note.title())
                .param(note.content())
                .param(note.creationDate())
                .update(keyHolder);

        long noteId = keyHolder.getKey().longValue();
        saveTagsIfExist(noteId, note.tags());
        return noteId;
    }

    public void saveTagsIfExist(long noteId, Set<Tag> tags) {
        Stream<String> tagsAlreadyInTheDatabase = getTagsAlreadyInTheDatabase(tags.stream().map(Tag::name).toList());
        tagsAlreadyInTheDatabase.forEach(tag -> {
            jdbcClient.sql("merge into note_tag (note_id, tag_name) values (?, ?)")
                    .param(noteId)
                    .param(tag)
                    .update();
        });
    }

    private Stream<String> getTagsAlreadyInTheDatabase(List<String> tags) {
        return tags.stream().filter(tag -> {
            String query = "select count(*) from tag where name = ?";
            int count = jdbcClient.sql(query).param(tag).query(Integer.class).single();
            return count > 0;
        });
    }

    public boolean isUserOwnerOfNote(long userId, long noteId) {
        String query = "select count(*) from note where owner_id = ? and id = ?";
        int count = jdbcClient.sql(query).param(userId).param(noteId).query(Integer.class).single();
        return count > 0;
    }

    public boolean userCanEditNote(long userId, long noteId) {
        String query = """
            select count(*) from permission p
            where p.user_id = ? and p.note_id = ? and p.can_edit = true
        """;
        int count = jdbcClient.sql(query).param(userId).param(noteId).query(Integer.class).single();
        return count > 0;
    }

    public void update(CreateNoteDTO note, long noteId) {
        String query = """
            update note set title = ?, content = ? where id = ?
        """;
        jdbcClient.sql(query).param(note.title()).param(note.content()).param(noteId).update();
        saveTagsIfExist(noteId, note.tags().stream().map(Tag::new).collect(Collectors.toSet()));
    }
}
