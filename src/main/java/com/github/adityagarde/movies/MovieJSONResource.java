package com.github.adityagarde.movies;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/jmovies")
@Tag(name = "Movie Resource", description = "Movie REST APIs")
public class MovieJSONResource {

    public static List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getMovies", summary = "Get Movies", description = "Fetches all Movies in a List")
    @APIResponse(
            responseCode = "200",
            description = "Operation Completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    @Operation(operationId = "getMovieCount", summary = "Count Movies", description = "Returns size of the Movie List")
    @APIResponse(
            responseCode = "200",
            description = "Operation Completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN))
    public Integer getMovieCount() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "addMovie", summary = "Creates Movie", description = "Adds new movie to the list")
    @APIResponse(
            responseCode = "201",
            description = "Movie Created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response addMovie(
            @RequestBody(
                    description = "Movie to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Movie.class)))
                    Movie newMovie) {
        movies.add(newMovie);
        return Response.status(Response.Status.CREATED).entity(movies).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "updateMovie", summary = "Updates Movie", description = "Updates existing movie")
    @APIResponse(
            responseCode = "200",
            description = "Movie Updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response updateMovie(
            @Parameter(description = "Movie id", required = true) @PathParam("id") int id,
            @Parameter(description = "Movie Title", required = true) @QueryParam("newTitle") String newTitle) {
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
    @Operation(operationId = "deleteMovie", summary = "Deletes Movie", description = "Deletes existing movie")
    @APIResponse(
            responseCode = "204",
            description = "Movie Deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(
            responseCode = "400",
            description = "Movie not Found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
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