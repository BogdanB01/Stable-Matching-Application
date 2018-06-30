package com.license.smapp.control.service.impl;

import com.license.smapp.entity.model.*;
import com.license.smapp.entity.repository.BibliographyRepository;
import com.license.smapp.entity.repository.ProjectRepository;
import com.license.smapp.entity.repository.QuestionRepository;
import com.license.smapp.entity.repository.TagRepository;
import com.license.smapp.control.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectServiceImpl implements ProjectService{
    Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private static <T> Collection<T> getSymmetricDifference(Collection<T> coll1, Collection<T> coll2){
        return Stream.concat(
                coll1.stream().filter( c -> !coll2.contains(c)),
                coll2.stream().filter( c -> !coll1.contains(c))
                ).collect(Collectors.toList());
    }

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    BibliographyRepository bibliographyRepository;

    @Autowired
    QuestionRepository questionRepository;


    @Override
    public Project save(Project entity) {

        Iterator<Tag> iterator = entity.getTags().iterator();

        List<Tag> existingTags = new ArrayList<>();
        while (iterator.hasNext()) {

            Tag existingTag = tagRepository.getFirstByName(iterator.next().getName());

            if (existingTag != null) {
                existingTags.add(existingTag);
                iterator.remove();
            }
        }

        entity.getTags().addAll(existingTags);

        Project project = this.projectRepository.save(entity);

        return project;
    }

    @Override
    public Project findById(Long id) {
        return this.projectRepository.findOne(id);
    }

    @Override
    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.projectRepository.delete(id);
    }

    @Override
    public Page<Project> listAllByPage(String filter, Pageable pageable) {
        return projectRepository.findAllByLecturerNameContainingOrTitleContaining(filter, filter, pageable);
    }

    @Override
    public Project update(Project project, Project model) {

        List<Bibliography> bibliographiesToBeDeletedOrAdded = new ArrayList<>(getSymmetricDifference(model.getBibliographies(), project.getBibliographies()));

        for(Bibliography b : bibliographiesToBeDeletedOrAdded) {
            if (b.getId() != null) {
                project.removeBibliography(b);
            } else {
                project.addBibliography(b);
            }
        }

        List<Question> questionsToBeDeletedOrAdded = new ArrayList<>(getSymmetricDifference(model.getQuestions(), project.getQuestions()));

        for(Question q : questionsToBeDeletedOrAdded) {
            if (q.getId() != null) {
                project.removeQuestion(q);
            } else {
                project.addQuestion(q);
            }
        }

        List<Tag> tagsToBeDeletedOrAdded = new ArrayList<>(getSymmetricDifference(model.getTags(), project.getTags()));

        for(Tag t : tagsToBeDeletedOrAdded) {
            if (t.getId() != null) {
                project.removeTag(t);
            } else {
                Tag existingTag = tagRepository.getFirstByName(t.getName());
                if (existingTag != null) {
                    project.addTag(existingTag);
                } else {
                    LOGGER.error(t.getName());
                    project.addTag(t);
                }
            }
        }

        project.setTitle(model.getTitle());
        project.setCapacity(model.getCapacity());
        project.setDescription(model.getDescription());

        if (model.getFile() != project.getFile()) {
            project.removeFile();
            if (model.getFile() != null) {
                project.addFile(model.getFile());
            }
        }
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsForLecturer(Lecturer lecturer, boolean active) {
        return projectRepository.findAllByLecturerAndActive(lecturer, active);
    }

    @Override
    public Page<Project> listAllProjectsByActive(boolean active, Pageable pageable) {
        return projectRepository.findAllByActive(active, pageable);
    }

    @Override
    public Page<Project> filterActiveProjectsByProjectTitle(boolean active, String filter, Pageable pageable) {
        return projectRepository.findAllByActiveAndTitleStartingWithIgnoreCase(active, filter, pageable);
    }

    @Override
    public Page<Project> filterActiveProjectsByLecturerName(boolean active, String filter, Pageable pageable) {
        return projectRepository.findAllByActiveAndLecturerNameStartingWithIgnoreCase(active, filter, pageable);
    }

    @Override
    public Page<Project> filterActiveProjectsByTagName(boolean active, String filter, Pageable pageable) {
        return projectRepository.findDistinctByActiveAndTags_NameStartingWithIgnoreCase(active, filter, pageable);
    }

    @Override
    public void setAllProjectsInactive() {
        this.projectRepository.setAllProjectsInactive();
    }
}
