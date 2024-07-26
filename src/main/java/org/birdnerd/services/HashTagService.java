package org.birdnerd.services;

import org.birdnerd.data.HashTagRepository;
import org.birdnerd.data.models.HashTag;
import org.birdnerd.data.models.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HashTagService {

    private final HashTagRepository repository;

    public HashTagService(HashTagRepository repository) {
        this.repository = repository;
    }

    public Optional<HashTag> get(Long id) {
        return repository.findById(id);
    }

    public HashTag update(HashTag entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<HashTag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<HashTag> list(Pageable pageable, Specification<HashTag> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<HashTag> listAll() {
        return repository.findAll();
    }

}
