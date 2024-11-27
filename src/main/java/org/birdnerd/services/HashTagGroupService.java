package org.birdnerd.services;

import java.util.Optional;
import org.birdnerd.data.HashTagGroupRepository;
import org.birdnerd.data.models.HashTagGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class HashTagGroupService {

  private final HashTagGroupRepository repository;

  public HashTagGroupService(HashTagGroupRepository repository) {
    this.repository = repository;
  }

  public Optional<HashTagGroup> get(Long id) {
    return repository.findById(id);
  }

  public HashTagGroup update(HashTagGroup entity) {
    return repository.save(entity);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public Page<HashTagGroup> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<HashTagGroup> list(Pageable pageable, Specification<HashTagGroup> filter) {
    return repository.findAll(filter, pageable);
  }

  public int count() {
    return (int) repository.count();
  }
}
