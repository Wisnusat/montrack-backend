package com.example.montrack.Repositories;

import com.example.montrack.Models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
