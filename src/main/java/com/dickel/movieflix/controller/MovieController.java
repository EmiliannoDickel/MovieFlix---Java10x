package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.entity.Movie;
import com.dickel.movieflix.mapper.MovieMapper;
import com.dickel.movieflix.request.MovieRequest;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById (@PathVariable Long id) {
        return movieService.findById(id).map(movie-> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> update (@PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovieRequest(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchByCategory (@RequestParam Long categoryId) {
        return ResponseEntity.ok(        movieService.findByCategory(categoryId)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        Optional<Movie> optId = movieService.findById(id);
        if (optId.isPresent()) {
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
