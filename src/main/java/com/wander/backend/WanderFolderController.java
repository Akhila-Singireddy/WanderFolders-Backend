package com.wander.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class WanderFolderController {

    private final WanderFolderRepository repo;

    public WanderFolderController(WanderFolderRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<WanderFolder> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public WanderFolder getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WanderFolder create(@RequestBody WanderFolder folder) {
        folder.setId(null);
        return repo.save(folder);
    }

    @PutMapping("/{id}")
    public WanderFolder update(@PathVariable Long id, @RequestBody WanderFolder in) {
        WanderFolder f = getOne(id);
        f.setTitle(in.getTitle());
        f.setDestination(in.getDestination());
        f.setStartDate(in.getStartDate());
        f.setEndDate(in.getEndDate());
        f.setItinerary(in.getItinerary());
        f.setMemories(in.getMemories());
        f.setExpenses(in.getExpenses());
        f.setImageUrl(in.getImageUrl());
        return repo.save(f);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        getOne(id);
        repo.deleteById(id);
    }
}