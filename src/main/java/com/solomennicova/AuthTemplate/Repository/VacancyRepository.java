package com.solomennicova.AuthTemplate.Repository;

import com.solomennicova.AuthTemplate.Entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
