package com.motnip.applicationtracker.repository;

import com.motnip.applicationtracker.model.Application;
import com.motnip.applicationtracker.model.ApplicationState;
import com.motnip.applicationtracker.model.Application_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ApplicationSpec {

    public static Specification<Application> byCompanyName(String companyName){

        return (root, query, criteriaBuilder)->
                criteriaBuilder.equal(root.get(Application_.companyName),companyName);
    }

    public static Specification<Application> inStates(List<ApplicationState> states){

        return (root, query, criteriaBuilder)->
                root.get(Application_.state).in(states);
    }
}
