package com.motnip.applicationtracker.repository;
import com.motnip.applicationtracker.model.ApplicationNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationNoteRepository extends JpaRepository<ApplicationNote, Long> {

    @Modifying
    @Query("select n from ApplicationNote n where n.application.id = ?1")
    List<ApplicationNote> findAllByApplicationId(Long applicationId);
}
