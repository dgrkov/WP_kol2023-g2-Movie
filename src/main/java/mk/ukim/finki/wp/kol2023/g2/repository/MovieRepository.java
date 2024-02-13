package mk.ukim.finki.wp.kol2023.g2.repository;

import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param rating          Double that is used to filter the movies that have less rating than this value.
     *                        This param can be null, and is not used for filtering in this case.
     * @param genre           Used for filtering the movies that are from this genre.
     *                        This param can be null, and is not used for filtering in this case.
     * @return The movies that meet the filtering criteria
     */
    List<Movie> findByRatingLessThan(Double rating);
    List<Movie> findByGenreEquals(Genre genre);
    List<Movie> findByRatingLessThanAndGenreEquals(Double rating, Genre genre);
}
