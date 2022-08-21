package com.canhlabs.shorten.domain.repo;

import com.canhlabs.shorten.domain.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepo extends JpaRepository<URL, String> {
    URL findAllByHash(String hash);
}
