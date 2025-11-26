package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.entity.Movie;
import com.dickel.movieflix.mapper.MovieMapper;
import com.dickel.movieflix.request.MovieRequest;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/movies")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Endpoints para gerenciamento de filmes")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @Operation(summary = "Salvar um novo filme", description = "Endpoint para salvar um novo filme no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    public ResponseEntity<MovieResponse> save (@Valid @RequestBody MovieRequest movieRequest) {
        Movie movie = movieService.save(MovieMapper.toMovieRequest(movieRequest));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movie));
    }

    @GetMapping
    @Operation(summary = "Obter todos os filmes", description = "Endpoint para obter todos os filmes cadastrados no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Lista de filmes obtida com sucesso", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    public ResponseEntity<List<MovieResponse>> getAllMovies () {
        return ResponseEntity.ok(movieService.findAll()
                .stream()
                .map(movie -> MovieMapper.toMovieResponse(movie))
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter filme por ID", description = "Endpoint para obter um filme específico pelo seu ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filme obtido com sucesso", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404" , description = "Filme não encontrado", content = @Content())
    public ResponseEntity<MovieResponse> findById (@PathVariable Long id) {
        return movieService.findById(id).map(movie-> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar filme", description = "Endpoint para atualizar os dados de um filme existente.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @ApiResponse(responseCode = "404" , description = "Filme não encontrado", content = @Content())
    public ResponseEntity<MovieResponse> update (@PathVariable Long id, @Valid @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovieRequest(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar filmes por categoria", description = "Endpoint para buscar filmes com base em uma categoria específica.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filmes obtidos com sucesso", content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    public ResponseEntity<List<MovieResponse>> searchByCategory (@RequestParam Long categoryId) {
        return ResponseEntity.ok(        movieService.findByCategory(categoryId)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme", description = "Endpoint para deletar um filme existente pelo seu ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404" , description = "Filme não encontrado", content = @Content())
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        Optional<Movie> optId = movieService.findById(id);
        if (optId.isPresent()) {
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
