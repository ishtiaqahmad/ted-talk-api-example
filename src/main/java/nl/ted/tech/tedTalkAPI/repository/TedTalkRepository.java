package nl.ted.tech.tedTalkAPI.repository;

import nl.ted.tech.tedTalkAPI.model.TedTalk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TedTalkRepository extends JpaRepository<TedTalk, Long> {
}
