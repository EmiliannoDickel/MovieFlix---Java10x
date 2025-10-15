package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Movie;
import com.dickel.movieflix.mapper.MovieMapper;
import com.dickel.movieflix.request.MovieRequest;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> save (@RequestBody MovieRequest movieRequest) {
        Movie movie = movieService.save(MovieMapper.toMovieRequest(movieRequest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies () {
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList());
    }

}
