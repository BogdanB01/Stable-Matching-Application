package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag getFirstByName(String name);
}
