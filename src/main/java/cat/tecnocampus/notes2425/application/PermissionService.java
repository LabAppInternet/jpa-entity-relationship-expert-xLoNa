package cat.tecnocampus.notes2425.application;

import cat.tecnocampus.notes2425.application.dtos.NoteDTO;
import cat.tecnocampus.notes2425.application.dtos.PermissionCreation;
import cat.tecnocampus.notes2425.application.exceptions.UserHasNoEditPermission;
import cat.tecnocampus.notes2425.domain.Note;
import cat.tecnocampus.notes2425.domain.Tag;
import cat.tecnocampus.notes2425.persistence.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final UserNotesService userNotesService;

    public PermissionService(PermissionRepository permissionRepository, UserNotesService userNotesService) {
        this.permissionRepository = permissionRepository;
        this.userNotesService = userNotesService;
    }

    public List<NoteDTO> getNotesUserCanView(long userId) {
        return permissionRepository.findNotesUserCanView(userId).stream().map(NoteMapper::noteToNoteDTO).toList();
    }

    public List<NoteDTO> getNotesUserCanEdit(long userId) {
        return permissionRepository.findNotesUserCanEdit(userId).stream().map(NoteMapper::noteToNoteDTO).toList();
    }

    public void newPermission(long ownerId, PermissionCreation permission) {
        if (!userNotesService.isUserOwnerOfNote(ownerId, permission.noteId()))
            throw new UserHasNoEditPermission(ownerId, permission.noteId());
        permissionRepository.addPermission(permission.allowedUserId(), permission.noteId(), permission.canView(), permission.canEdit());
    }

    private class NoteMapper {
        public static NoteDTO noteToNoteDTO(Note note) {
            return new NoteDTO(note.id(), note.title(), note.content(), note.creationDate(), note.tags().stream().map(Tag::name).collect(Collectors.toSet()));
        }
    }
}
