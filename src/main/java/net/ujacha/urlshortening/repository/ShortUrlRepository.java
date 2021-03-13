package net.ujacha.urlshortening.repository;

import net.ujacha.urlshortening.entity.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByOriginalUrl(String originalUrl);

    @Query("SELECT su FROM ShortUrl su WHERE su.originalUrl LIKE %:filter%")
    Page<ShortUrl> findAllByFilter(String filter, Pageable Pageable);
}
