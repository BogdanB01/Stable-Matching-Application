package com.license.smapp.control.service.impl;

import com.license.smapp.entity.model.Question;
import com.license.smapp.entity.repository.QuestionRepository;
import com.license.smapp.control.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question save(Question entity) {
        return this.questionRepository.save(entity);
    }

    @Override
    public Question findById(Long id) {
        return this.questionRepository.findOne(id);
    }

    @Override
    public List<Question> findAll() {
        return this.questionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.questionRepository.delete(id);
    }
}
