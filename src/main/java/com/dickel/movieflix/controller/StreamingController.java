package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Streaming;
import com.dickel.movieflix.mapper.StreamingMapper;
import com.dickel.movieflix.request.StreamingRequest;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.response.StreamingResponse;
import com.dickel.movieflix.service.StreamingService;
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

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
@Tag(name = "Streaming", description = "Endpoints para gerenciamento de serviços de streaming")
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping()
    @Operation(summary = "Buscar streaming", description = "Endpoint para buscar streaming.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Streaming obtidos com sucesso", content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    public ResponseEntity<List<StreamingResponse>> getAllStreamings(){
        List<StreamingResponse> streamings = streamingService.findAll()
                .stream()
                .map(StreamingMapper:: toStreamingResponse)
                .toList();
        return ResponseEntity.ok(streamings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter streaming por ID", description = "Endpoint para obter um streaming específico pelo seu ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Streaming obtido com sucesso", content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    @ApiResponse(responseCode = "404" , description = "Streaming não encontrado", content = @Content())
    public ResponseEntity<StreamingResponse> getStreamingById(@PathVariable Long id){
        return streamingService.findById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Salvar novo Streaming", description = "Endpoint para salvar um novo streaming no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Streaming salvo com sucesso", content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    public ResponseEntity<StreamingResponse> saveStreaming(@Valid @RequestBody StreamingRequest request){
        Streaming newStreaming = StreamingMapper.toStreamingRequest(request);
        Streaming savedStreaming = streamingService.save(newStreaming);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obter streaming por ID", description = "Endpoint para obter um streaming específico pelo seu ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Streaming obtido com sucesso", content = @Content())
    @ApiResponse(responseCode = "404" , description = "Streaming não encontrado", content = @Content())
    public ResponseEntity<Void> deleteStreamingById(@PathVariable Long id){
        streamingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



