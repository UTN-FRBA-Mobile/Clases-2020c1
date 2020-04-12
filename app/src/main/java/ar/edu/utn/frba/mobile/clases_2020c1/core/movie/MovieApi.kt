package ar.edu.utn.frba.mobile.clases_2020c1.core.movie

import ar.edu.utn.frba.mobile.clases_2020c1.R

class MovieApi {
    fun getMovies(): MutableList<Movie> {
        val dataset = mutableListOf<Movie>()
        for (i in 0..30) {
            if (i % 5 == 0) {
                dataset.add(Movie("Category $i", null, true))
                continue
            }

            if (i % 2 == 0)
            //even
                dataset.add(Movie("Movie $i", R.drawable.movie_icon_1, false))
            else
            //odd
                dataset.add(Movie("Movie $i", R.drawable.movie_icon_2, false))
        }
        return dataset
    }
}