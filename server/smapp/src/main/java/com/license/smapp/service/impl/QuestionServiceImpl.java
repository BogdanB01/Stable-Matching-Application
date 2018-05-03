package com.license.smapp.service.impl;

import com.license.smapp.model.Question;
import com.license.smapp.repository.QuestionRepository;
import com.license.smapp.service.QuestionService;
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
