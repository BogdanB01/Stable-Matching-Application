package com.license.smapp.service.impl;

import com.license.smapp.model.Answer;
import com.license.smapp.repository.AnswerRepository;
import com.license.smapp.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer save(Answer entity) {
        return answerRepository.save(entity);
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findOne(id);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        answerRepository.delete(id);
    }
}
