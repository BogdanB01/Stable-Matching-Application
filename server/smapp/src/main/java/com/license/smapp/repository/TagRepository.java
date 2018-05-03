package com.license.smapp.repository;

import com.license.smapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag getFirstByName(String name);
}
