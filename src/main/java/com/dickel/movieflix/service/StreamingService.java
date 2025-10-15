package com.dickel.movieflix.service;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.entity.Streaming;
import com.dickel.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> findAll() {
        return streamingRepository.findAll();
    }

    public Optional<Streaming> findById(Long id) {
        return streamingRepository.findById(id);
    }

    public Streaming save (Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public void deleteById(Long id) {
        streamingRepository.deleteById(id);
    }
}
