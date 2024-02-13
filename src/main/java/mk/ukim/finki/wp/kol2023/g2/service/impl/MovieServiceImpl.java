package mk.ukim.finki.wp.kol2023.g2.service.impl;

import mk.ukim.finki.wp.kol2023.g2.model.Director;
import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import mk.ukim.finki.wp.kol2023.g2.model.exceptions.InvalidMovieIdException;
import mk.ukim.finki.wp.kol2023.g2.repository.MovieRepository;
import mk.ukim.finki.wp.kol2023.g2.service.DirectorService;
import mk.ukim.finki.wp.kol2023.g2.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final DirectorService directorService;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    @Override
    public List<Movie> listAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);
    }

    @Override
    public Movie create(String name, String description, Double rating, Genre genre, Long director) {
        Director director1 = directorService.findById(director);
        Movie movie = new Movie(name, description, rating, genre, director1);
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Long id, String name, String description, Double rating, Genre genre, Long director) {
        Director director1 = directorService.findById(director);
        Movie movie = findById(id);
        movie.setName(name);
        movie.setDescription(description);
        movie.setRating(rating);
        movie.setGenre(genre);
        movie.setDirector(director1);
        return movieRepository.save(movie);
    }

    @Override
    public Movie delete(Long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
        return movie;
    }

    @Override
    public Movie vote(Long id) {
        Movie movie = findById(id);
        movie.setVotes(movie.getVotes() + 1);
        return movieRepository.save(movie);
    }

    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param rating          Double that is used to filter the movies that have less rating than this value.
     *                        This param can be null, and is not used for filtering in this case.
     * @param genre           Used for filtering the movies that are from this genre.
     *                        This param can be null, and is not used for filtering in this case.
     * @return The movies that meet the filtering criteria
     */

    @Override
    public List<Movie> listMoviesWithRatingLessThenAndGenre(Double rating, Genre genre) {
        if (rating == null && genre == null) {
            return listAllMovies();
        } else if (rating == null) {
            return movieRepository.findByGenreEquals(genre);
        } else if (genre == null) {
            return movieRepository.findByRatingLessThan(rating);
        } else {
            return movieRepository.findByRatingLessThanAndGenreEquals(rating, genre);
        }
    }
}
