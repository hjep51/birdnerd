package org.birdnerd.data;

import org.birdnerd.data.models.HashTagGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HashTagGroupRepository
    extends JpaRepository<HashTagGroup, Long>, JpaSpecificationExecutor<HashTagGroup> {}
