package com.dickel.movieflix.request;

import lombok.Builder;

@Builder
public record StreamingRequest(String name) {
}
