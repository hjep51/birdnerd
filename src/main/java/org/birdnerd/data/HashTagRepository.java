package org.birdnerd.data;

import org.birdnerd.data.models.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HashTagRepository
    extends JpaRepository<HashTag, Long>, JpaSpecificationExecutor<HashTag> {}
