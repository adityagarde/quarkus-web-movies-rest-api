package com.github.adityagarde.movies;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieController {

    public static List<String> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
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
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addMovie(String newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("/{movieToUpdate}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateMovie(@PathParam("movieToUpdate") String movieToUpdate,
                                @QueryParam("movie") String newMovie) {
        movies = movies.stream().map(movie -> {
            if (movie.equals(movieToUpdate)) {
                return newMovie;
            }
            return movie;
        }).collect(Collectors.toList());

        return Response.ok(movies).build();
    }

    @DELETE
    @Path("/{movie}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMovie(@PathParam("movie") String movie) {
        return movies.remove(movie) ? Response.noContent().build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

}