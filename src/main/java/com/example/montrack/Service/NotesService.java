package com.example.montrack.Service;

import com.example.montrack.DTO.ApiResponse;
import com.example.montrack.Models.Notes;
import com.example.montrack.Repositories.NotesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;

    public ApiResponse<Notes> addNotes(Notes notes) {
        if (notes == null || notes.getUser().getUserId() == 0 || notes.getTitle() == null) {
            return new ApiResponse<>(false, "Notes data is invalid", null);
        }
        Notes savedNotes = notesRepository.save(notes);
        return new ApiResponse<>(true, "Notes added successfully", savedNotes);
    }

    public ApiResponse<Void> deleteNotes(Long notesId) {
        if (!notesRepository.existsById(notesId)) {
            return new ApiResponse<>(false, "Notes not found", null);
        }
        notesRepository.deleteById(notesId);
        return new ApiResponse<>(true, "Notes deleted successfully", null);
    }
}
