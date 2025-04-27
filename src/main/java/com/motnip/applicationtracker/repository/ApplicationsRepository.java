package com.motnip.applicationtracker.repository;

import com.motnip.applicationtracker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationsRepository extends JpaRepository<Application, Long> {
}
