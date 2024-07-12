package org.birdnerd.services;

import org.birdnerd.data.Species;
import org.birdnerd.data.SpeciesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpeciesService {

    private final SpeciesRepository repository;

    public SpeciesService(SpeciesRepository repository) {
        this.repository = repository;
    }

    public Optional<Species> get(Long id) {
        return repository.findById(id);
    }

    public Species update(Species entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Species> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Species> list(Pageable pageable, Specification<Species> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}
