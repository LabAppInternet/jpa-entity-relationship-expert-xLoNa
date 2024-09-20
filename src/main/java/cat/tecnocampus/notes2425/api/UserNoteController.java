package cat.tecnocampus.notes2425.api;

import cat.tecnocampus.notes2425.application.PermissionService;
import cat.tecnocampus.notes2425.application.UserNotesService;
import cat.tecnocampus.notes2425.application.dtos.CreateNoteDTO;
import cat.tecnocampus.notes2425.application.dtos.NoteDTO;
import cat.tecnocampus.notes2425.application.dtos.PermissionCreation;
import cat.tecnocampus.notes2425.application.dtos.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
class UserNoteController {

    private final UserNotesService userNotesService;
    private final PermissionService permissionService;

    public UserNoteController(UserNotesService userNotesService, PermissionService permissionService) {
        this.userNotesService = userNotesService;
        this.permissionService = permissionService;
    }

    @GetMapping("/users/{username}")
    private UserDTO findUserByUsername(@PathVariable String username) {
        var result = userNotesService.findUserByUsername(username);
        return result;
    }

    @GetMapping("/users/{ownerId}/notes")
    private List<NoteDTO> getUserNotes(@PathVariable long ownerId) {
        List<NoteDTO> userNotes = userNotesService.findUserNotes(ownerId);
        return userNotesService.findUserNotes(ownerId);
    }

    @PostMapping("/users/{userId}/notes")
    private ResponseEntity<Void> createNote(@PathVariable long userId, @RequestBody CreateNoteDTO noteDTO, UriComponentsBuilder ucb) {
        long noteId = userNotesService.createNote(userId, noteDTO);
        URI location = ucb.path("/users/{userId}/notes/{noteId}").buildAndExpand(userId, noteId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}/notesCanView/")
    private List<NoteDTO> getNotesUserCanView(@PathVariable long userId) {
        return permissionService.getNotesUserCanView(userId);
    }

    @GetMapping("/users/{userId}/notesCanEdit/")
    private List<NoteDTO> getNotesUserCanEdit(@PathVariable long userId) {
        return permissionService.getNotesUserCanEdit(userId);
    }

    @PostMapping("/users/{ownerId}/permissions")
    @ResponseStatus(HttpStatus.CREATED)
    private void giveEditPermission(@PathVariable long ownerId, @RequestBody PermissionCreation permission) {
        permissionService.newPermission(ownerId, permission);
    }

   @PutMapping("/users/{userId}/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateNote(@PathVariable long userId, @PathVariable long noteId, @RequestBody CreateNoteDTO noteDTO) {
        userNotesService.updateNote(userId, noteId, noteDTO);
    }
}
