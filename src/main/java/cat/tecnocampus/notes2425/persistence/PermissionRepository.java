package cat.tecnocampus.notes2425.persistence;

import cat.tecnocampus.notes2425.domain.Note;
import cat.tecnocampus.notes2425.domain.Tag;
import cat.tecnocampus.notes2425.domain.UserLab;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class PermissionRepository {
    private final JdbcClient jdbcClient;
    private final UserRepository userRepository;

    public PermissionRepository(JdbcClient jdbcClient, UserRepository userRepository) {
        this.jdbcClient = jdbcClient;
        this.userRepository = userRepository;
    }

    public List<Note> findNotesUserCanView(long userId) {
        String query = """
            select n.id, n.title, n.content, n.creation_date, n.owner_id, tn.tag_name as tags
            from note n
            join permission p on n.id = p.note_id and (p.can_read = true or p.can_edit = true)
            join note_tag tn on n.id = tn.note_id
            where p.user_id = ?
        """;
        return jdbcClient.sql(query).param(userId).query(new NoteExtractor());
    }

    public List<Note> findNotesUserCanEdit(long userId) {
        String query = """
            select n.id, n.title, n.content, n.creation_date, n.owner_id, tn.tag_name as tags
            from note n
            join permission p on n.id = p.note_id and p.can_edit = true
            join note_tag tn on n.id = tn.note_id
            where p.user_id = ?
        """;
        return jdbcClient.sql(query).param(userId).query(new NoteExtractor());
    }

    //insert or update permission with merge (h2)
    public void addPermission(long userId, long noteId, boolean canRead, boolean canEdit) {
        String query = """
            merge into permission (user_id, note_id, can_read, can_edit) values (?, ?, ?, ?)
        """;
        jdbcClient.sql(query).param(userId).param(noteId).param(canRead).param(canEdit).update();
    }

    private class NoteExtractor implements ResultSetExtractor<List<Note>> {
        @Override
        public List<Note> extractData(ResultSet rs) throws SQLException {
            HashMap<Long, Note> notes = new HashMap<>();
            while (rs.next()) {
                long noteId = rs.getLong("id");
                if (!notes.containsKey(noteId)) {
                    //create the new note
                    UserLab owner = userRepository.findById(rs.getLong("owner_id")).orElseThrow();
                    notes.put(noteId, new Note(noteId, owner, rs.getString("title"),
                            rs.getString("content"), rs.getTimestamp("creation_date").toLocalDateTime(),
                            new HashSet<>(Arrays.asList(new Tag(rs.getString("tag_name"))))));
                }
                else {
                    notes.get(noteId).tags().add(new Tag(rs.getString("tag_name")));
                }
            }
            return notes.values().stream().toList();
        }
    }
}
