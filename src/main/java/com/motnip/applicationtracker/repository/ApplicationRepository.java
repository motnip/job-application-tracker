package com.motnip.applicationtracker.repository;

import com.motnip.applicationtracker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Transactional
    Optional<Application> findById(Long aLong);
}
