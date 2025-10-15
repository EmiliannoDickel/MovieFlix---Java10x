package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Streaming;
import com.dickel.movieflix.mapper.StreamingMapper;
import com.dickel.movieflix.request.StreamingRequest;
import com.dickel.movieflix.response.StreamingResponse;
import com.dickel.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping()
    public ResponseEntity<List<StreamingResponse>> getAllStreamings(){
        List<StreamingResponse> streamings = streamingService.findAll()
                .stream()
                .map(StreamingMapper:: toStreamingResponse)
                .toList();
        return ResponseEntity.ok(streamings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getStreamingById(@PathVariable Long id){
        return streamingService.findById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@RequestBody StreamingRequest request){
        Streaming newStreaming = StreamingMapper.toStreamingRequest(request);
        Streaming savedStreaming = streamingService.save(newStreaming);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingById(@PathVariable Long id){
        streamingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



