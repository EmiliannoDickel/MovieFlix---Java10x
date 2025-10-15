package com.dickel.movieflix.mapper;

import com.dickel.movieflix.entity.Streaming;
import com.dickel.movieflix.request.StreamingRequest;
import com.dickel.movieflix.response.StreamingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    //Criar para receber um Request e transformar em Entity
public static Streaming toStreamingRequest (StreamingRequest streamingRequest) {
        return Streaming.builder()
                .name(streamingRequest.name())
                .build();
    }

    //Responder uma request com uma response
    public static StreamingResponse toStreamingResponse (Streaming streaming) {
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}
