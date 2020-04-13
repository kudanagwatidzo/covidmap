package org.zincat.covidmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zincat.covidmap.models.ZincatUser;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<ZincatUser, Long> {

    ZincatUser findByUsername(String username);

    boolean existsByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
