package Fab_Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pojo.Cat_Model;

/**
 * Created by krupenghetiya on 28/06/17.
 */

public class MovieData {
    private List<Cat_Model> mList = new ArrayList<>();

    public MovieData(List<Cat_Model> mList) {
        this.mList = mList;
    }

    public List<Cat_Model> getAllMovies() {
        return mList;
    }

    public void setmList(List<Cat_Model> mList) {
        this.mList = mList;
    }

    public List<Cat_Model> getGenreFilteredMovies(List<String> genre, List<Cat_Model> mList) {
        List<Cat_Model> tempList = new ArrayList<>();
        /*for (Cat_Model movie : mList) {
            for (String g : genre) {
                if (movie.getGenre().equalsIgnoreCase(g)) {
                    tempList.add(movie);
                }
            }

        }*/
        return tempList;
    }

    public List<Cat_Model> getYearFilteredMovies(List<String> yearstr, List<Cat_Model> mList) {
        List<Cat_Model> tempList = new ArrayList<>();
       /* for (Cat_Model movie : mList) {
            for (String y : yearstr) {
                if (movie.getYear() == Integer.parseInt(y)) {
                    tempList.add(movie);
                }
            }
        }*/
        return tempList;
    }

    public List<Cat_Model> getQualityFilteredMovies(List<String> quality, List<Cat_Model> mList) {
        List<Cat_Model> tempList = new ArrayList<>();
        /*for (Cat_Model movie : mList) {
            for (String q : quality) {
                if (movie.getQuality().equalsIgnoreCase(q)) {
                    tempList.add(movie);
                }
            }
        }*/
        return tempList;
    }

    public List<Cat_Model> getRatingFilteredMovies(List<String> rating, List<Cat_Model> mList) {
        List<Cat_Model> tempList = new ArrayList<>();
       /* for (Cat_Model movie : mList) {
            for (String r : rating) {
                if (movie.getRating() >= Float.parseFloat(r.replace(">",""))) {
                    tempList.add(movie);
                }
            }
        }*/
        return tempList;
    }

    public List<String> getUniqueGenreKeys() {
        List<String> genres = new ArrayList<>();
        /*for (Cat_Model movie : mList) {
            if (!genres.contains(movie.getGenre())) {
                genres.add(movie.getGenre());
            }
        }*/
        Collections.sort(genres);
        return genres;
    }

    public List<String> get_State_Keys() {
        List<String> genres = new ArrayList<>();
        for (Cat_Model movie : mList) {
            if (!genres.contains(movie.getC_state())) {
                genres.add(movie.getC_state());
            }
        }
        Collections.sort(genres);
        return genres;
    }

    public List<String> getUniqueYearKeys() {
        List<String> years = new ArrayList<>();
       /* for (Cat_Model movie : mList) {
            if (!years.contains(movie.getYear() + "")) {
                years.add(movie.getYear() + "");
            }
        }*/
        Collections.sort(years);
        return years;
    }

    public List<String> getUniqueQualityKeys() {
        List<String> qualities = new ArrayList<>();
        /*for (Cat_Model movie : mList) {
            if (!qualities.contains(movie.getQuality())) {
                qualities.add(movie.getQuality());
            }
        }*/
        Collections.sort(qualities);
        return qualities;
    }

    public List<String> getUniqueRatingKeys() {
        List<String> ratings = new ArrayList<>();
        /*for (Cat_Model movie : mList) {
            int rating = (int) Math.floor(movie.getRating());
            String rate = "> " + rating;
            if (!ratings.contains(rate)) {
                ratings.add(rate);
            }
        }*/
        Collections.sort(ratings);
        return ratings;
    }


}
