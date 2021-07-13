package com.github.adityagarde.movies;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/jmovies")
public class MovieJSONResource {

    public static List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer getMovieCount() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMovie(Movie newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") int id,
                                @QueryParam("newTitle") String newTitle) {
        movies = movies.stream().map(movie -> {
            if (movie.getId() == id) {
                movie.setTitle(newTitle);
            }
            return movie;
        }).collect(Collectors.toList());

        return Response.ok(movies).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") int id) {
        Optional<Movie> myMovie = movies.stream()
                                        .filter(movie -> movie.getId() == id)
                                        .findFirst();
        boolean isRemoved = false;
        if (myMovie.isPresent()) {
            isRemoved = movies.remove(myMovie.get());
        }

        return isRemoved ? Response.noContent().build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

}