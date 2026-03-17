package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.builder.MovieBuilder;
import it.nicolacosta.movie_app.model.Media;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterStrategyTest {

    @Test
    void testFilterByGenre(){
        List<Media> data = List.of(
                new MovieBuilder().title("Film 1").genre("Horror").build(),
                new MovieBuilder().title("Film 2").genre("Horror").build(),
                new MovieBuilder().title("FIlm 3").genre("Comico").build(),
                new MovieBuilder().title("Film 4").genre("Drammatico").build(),
                new MovieBuilder().title("Film 5").genre("Drammatico").build(),
                new MovieBuilder().title("Film 6").genre("Azione").build(),
                new MovieBuilder().title("Film 7").genre("Azione").build(),
                new MovieBuilder().title("Film 8").genre("Drammatico").build()
        );

        FilterByGenreStrategy strategy = new FilterByGenreStrategy("Horror");
        List<Media> result = strategy.filter(data);
        FilterByGenreStrategy strategy2 = new FilterByGenreStrategy("Drammatico");
        List<Media> result2 = strategy2.filter(data);

        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals(3, result2.size());

    }

    @Test
    void filterByYear(){
        List<Media> data = List.of(
                new MovieBuilder().title("Film 1").year(2020).build(),
                new MovieBuilder().title("Film 2").year(2021).build(),
                new MovieBuilder().title("Film 3").year(2020).build(),
                new MovieBuilder().title("Film 4").year(2019).build()
        );

        FilterByYearStrategy strategy = new FilterByYearStrategy(2020);
        List<Media> result = strategy.filter(data);
        FilterByYearStrategy strategy2 = new FilterByYearStrategy(2021);
        List<Media> result2 = strategy2.filter(data);
        FilterByYearStrategy strategy3 = new FilterByYearStrategy(2018);
        List<Media> result3 = strategy3.filter(data);

        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).getTitle());
        assertEquals(1, result2.size());
        assertEquals("Film 2", result2.get(0).getTitle());
        assertEquals(0, result3.size());
    }

    @Test
    void filterByName(){
        List<Media> data = List.of(
                new MovieBuilder().title("Film 1").build(),
                new MovieBuilder().title("Film 2").build(),
                new MovieBuilder().title("Film 3").build(),
                new MovieBuilder().title("Film 4").build()
        );

        FilterByNameStrategy strategy = new FilterByNameStrategy("f");
        List<Media> result = strategy.filter(data);
        FilterByNameStrategy strategy2 = new FilterByNameStrategy("1");
        List<Media> result2 = strategy2.filter(data);
        FilterByNameStrategy strategy3 = new FilterByNameStrategy("5");
        List<Media> result3 = strategy3.filter(data);

        assertEquals(4, result.size());
        assertEquals(1, result2.size());
        assertEquals(0, result3.size());
    }

    @Test
    void mixedFilter(){
        List<Media> data = List.of(
                new MovieBuilder().title("Film 1").year(2020).genre("Azione").build(),
                new MovieBuilder().title("Film 2").year(2021).genre("Drammatico").build(),
                new MovieBuilder().title("Film 3").year(2020).genre("Azione").build(),
                new MovieBuilder().title("Film 4").year(2019).genre("Horror").build()
        );

        FilterByYearStrategy yearFilter = new FilterByYearStrategy(2020);
        List<Media> yearFiltered = yearFilter.filter(data);
        FilterByGenreStrategy genreFilter = new FilterByGenreStrategy("Azione");
        List<Media> genreFiltered = genreFilter.filter(yearFiltered);

        assertEquals(2, genreFiltered.size());

        FilterByNameStrategy nameFilter = new FilterByNameStrategy("1");
        List<Media> nameFiltered = nameFilter.filter(genreFiltered);

        assertEquals(1, nameFiltered.size());


    }

}


