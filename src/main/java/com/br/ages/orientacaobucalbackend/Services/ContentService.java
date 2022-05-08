package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    ContentRepository contentRepository;

    public List<Content> list() {
        return contentRepository.findAll();
    }

    public Optional<Content> findById(Long id) {
        return contentRepository.findById(id);
    }

    public void deleteAll() {
        contentRepository.deleteAll();
    }

    public boolean deleteById(Long id) {
        Optional<Content> content = this.findById(id);
        if (content.isPresent()) {
            contentRepository.delete(content.get());
            return true;
        } else {
            return false;
        }
    }

    public void save(Content content) {
        contentRepository.save(content);
    }

    public boolean update(Long id, Content newContent) {
        Optional<Content> oldContent = this.findById(id);
        if (oldContent.isPresent()) {
            Content content = oldContent.get();
            content.setTitle(newContent.getTitle());
            content.setTextUrl(newContent.getTextUrl());
            content.setPanfletoUrl(newContent.getPanfletoUrl());
            content.setVideoUrl(newContent.getVideoUrl());

            this.save(content);
            return true;
        } else {
            throw new NullPointerException("this content doesn't exist.");
        }
    }
}