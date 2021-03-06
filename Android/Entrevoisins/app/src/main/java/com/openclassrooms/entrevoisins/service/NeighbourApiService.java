package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Get all my Favorite Neighbours
     * @return {@link List}
     * * ldev
     */
    List<Neighbour> getFavoriteNeighbours();


    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     * @return
     */
    List<Neighbour> createNeighbour(Neighbour neighbour);

    /**
     * Remove a favorite neighbour
     * @param neighbour
     * ldev
     */
    void removeFavorite(Neighbour neighbour);

    /**
     * add a favorite neighbour
     * @param neighbour
     * ldev
     */
    void addFavorite(Neighbour neighbour);
}
