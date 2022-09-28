package nl.ted.tech.tedTalkAPI.service;

import nl.ted.tech.tedTalkAPI.model.TedTalk;
import nl.ted.tech.tedTalkAPI.repository.TedTalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"tedTalks"})
public class TedTalkDataService {
    private final static Logger log = LoggerFactory.getLogger(TedTalkDataService.class);
    @Autowired
    private TedTalkRepository tedTalkRepository;

    @CacheEvict(cacheNames = "tedTalks", allEntries = true)
    @Secured("ROLE_VIEWER")
    public List<TedTalk> getTedTalks() {
        log.info("All Talks ...");
        return tedTalkRepository.findAll();
    }

    @Cacheable(cacheNames = "tedTalks", key = "#id")
    @Secured("ROLE_VIEWER")
    public Optional<TedTalk> getTedTalkById(Long id) {
        log.info("Get single Talk ...");
        Optional<TedTalk> tedTalk = tedTalkRepository.findById(id);
        return tedTalk;
    }

    @CacheEvict(cacheNames = "tedTalks")
    @Secured("ROLE_ADMIN")
    public void deleteTedTalkById(long id) {
        tedTalkRepository.deleteById(id);
    }

    @CacheEvict(cacheNames = "tedTalks")
    @Secured("ROLE_EDITOR")
    public TedTalk createTedTalk(TedTalk tedTalk) {
        return tedTalkRepository.save(tedTalk);
    }

    @CachePut(cacheNames = "tedTalks")
    @Secured("ROLE_EDITOR")
    public Optional<TedTalk> updateTedTalk(TedTalk tedTalk, long id) {
        Optional<TedTalk> tedTalkOptional = tedTalkRepository.findById(id);
        if (tedTalkOptional.isEmpty())
            return Optional.empty();
        tedTalk.setId(id);
        tedTalkRepository.save(tedTalk);
        return tedTalkOptional;
    }
}
