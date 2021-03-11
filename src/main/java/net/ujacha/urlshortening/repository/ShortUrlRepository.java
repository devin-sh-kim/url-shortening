package net.ujacha.urlshortening.repository;

import net.ujacha.urlshortening.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByOriginalUrl(String originalUrl);

}
