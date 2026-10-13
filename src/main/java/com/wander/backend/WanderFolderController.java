package com.wander.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/folders")
public class WanderFolderController {

    private final WanderFolderRepository repo;
    private final ObjectMapper mapper;

    public WanderFolderController(
            WanderFolderRepository repo,
            ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return repo.findAll().stream()
                .map(this::toMap)
                .toList();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOne(@PathVariable Long id) {
        return toMap(findFolder(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(
            @RequestBody Map<String, Object> data) {
        WanderFolder folder = new WanderFolder();
        folder.setData(writeJson(data));
        return toMap(repo.save(folder));
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> data) {
        WanderFolder folder = findFolder(id);
        folder.setData(writeJson(data));
        return toMap(repo.save(folder));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.delete(findFolder(id));
    }

    private WanderFolder findFolder(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Folder not found"));
    }

    private String writeJson(Map<String, Object> data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid trip data");
        }
    }

    private Map<String, Object> toMap(WanderFolder folder) {
        try {
            Map<String, Object> data = mapper.readValue(
                folder.getData(),
                new TypeReference<Map<String, Object>>() {});
            data.put("id", folder.getId());
            return data;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Could not read trip data");
        }
    }
}
