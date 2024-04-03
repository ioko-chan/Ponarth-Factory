package com.solomennicova.AuthTemplate.Repository;

import com.solomennicova.AuthTemplate.Entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

}
