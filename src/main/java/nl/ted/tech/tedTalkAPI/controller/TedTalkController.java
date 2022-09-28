package nl.ted.tech.tedTalkAPI.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import nl.ted.tech.tedTalkAPI.model.TedTalk;
import nl.ted.tech.tedTalkAPI.service.TedTalkDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tedTalk")
public class TedTalkController {
    private static final String ID = "/{id}";
    private final static Logger log = LoggerFactory.getLogger(TedTalkController.class);

    @Autowired
    private TedTalkDataService tedTalkService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping({"/v1.0", "/v1.1"})
    public List<TedTalk> retrieveAllTedTalks() {
        return tedTalkService.getTedTalks();
    }

    @GetMapping({"/v1.0" + ID, "/v1.1" + ID})
    public TedTalk retrieveTedTalk(@PathVariable long id) {
        Optional<TedTalk> tedTalk = tedTalkService.getTedTalkById(id);
        if (tedTalk.isEmpty()) {
            log.info("TED Talk not found with id:{}", id);
            throw new TedTalkNotFoundException("id-" + id);
        }
        return tedTalk.get();
    }

    @DeleteMapping({"/v1.0" + ID, "/v1.1" + ID})
    public void deleteTedTalk(@PathVariable long id) {
        tedTalkService.deleteTedTalkById(id);
    }

    @PostMapping({"/v1.0", "/v1.1"})
    public ResponseEntity<Object> createTedTalk(@RequestBody TedTalk tedTalk) {
        TedTalk savedTedTalk = tedTalkService.createTedTalk(tedTalk);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTedTalk.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping({"/v1.0" + ID, "/v1.1" + ID})
    public ResponseEntity<Object> updateTedTalk(@RequestBody TedTalk tedTalk, @PathVariable long id) {
        Optional<TedTalk> tedTalkOptional = tedTalkService.updateTedTalk(tedTalk, id);
        if (tedTalkOptional.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping({"/v1.1" + ID})
    public ResponseEntity<Object> updateTedTalkPartially(@RequestBody JsonPatch patch, @PathVariable long id) {
        try {
            TedTalk tedTalk = tedTalkService.getTedTalkById(id).orElseThrow(TedTalkNotFoundException::new);
            TedTalk tedTalkPatched = applyPatchToTedTalk(patch, tedTalk);
            tedTalkService.updateTedTalk(tedTalkPatched, id);
            return ResponseEntity.ok(tedTalkPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (TedTalkNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private TedTalk applyPatchToTedTalk(JsonPatch patch, TedTalk targetCustomer) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, TedTalk.class);
    }
}
