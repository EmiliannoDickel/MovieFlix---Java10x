package com.dickel.movieflix.mapper;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.entity.Movie;
import com.dickel.movieflix.entity.Streaming;
import com.dickel.movieflix.request.MovieRequest;
import com.dickel.movieflix.response.CategoryResponse;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.response.StreamingResponse;
import lombok.experimental.UtilityClass;

import java.util.List;


@UtilityClass
public class MovieMapper {

    public static Movie toMovieRequest (MovieRequest movieRequest) {

        List<Category> categories = movieRequest.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = movieRequest.streamings().stream()
                .map(streamingID -> Streaming.builder().id(streamingID).build())
                .toList();

        return Movie.builder()
                .title(movieRequest.title())
                .description(movieRequest.description())
                .releaseDate(movieRequest.releaseDate())
                .rating(movieRequest.rating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }


    public static MovieResponse toMovieResponse (Movie movie){

       List<CategoryResponse> categories = movie.getCategories().stream().map(category -> CategoryMapper.toCategoryResponse(category))
                .toList();
        List<StreamingResponse> streamings = movie.getStreamings().stream().map(StreamingMapper::toStreamingResponse)
                .toList();
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }
}
