package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.repository.ActualiteesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ActualiteesService {
    @Autowired
        private ActualiteesRepository actualiteesRepository;

    public List<Actualitees> getAllNews() {
        return actualiteesRepository.findAll();

    }

    public Actualitees getNewsById(Long id) {
        return actualiteesRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    public String createOrUpdateNews(Actualitees news) {
        LocalDate currentDate = LocalDate.now();
        Date currentDateAsDate = java.sql.Date.valueOf(currentDate);
        news.setDate(currentDateAsDate);
        actualiteesRepository.save(news);
        return "Actualitee added ";
    }

    public void deleteNews(Long id) {
        actualiteesRepository.deleteById(Math.toIntExact(id));
    }
}
