package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     * @return
     */
    @Override
    public List<Neighbour> createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
        return null;
    }

    /**
     * ldev
     */
    @Override
    public void addFavorite(Neighbour serializedNeighbour) {
        Neighbour originalNeighbour = neighbours.get(neighbours.indexOf(serializedNeighbour));
        originalNeighbour.setFavorite(true);
    }

    /**
     * ldev
     */
    @Override
    public void removeFavorite(Neighbour serializedNeighbour) {
        Neighbour originalNeighbour = neighbours.get(neighbours.indexOf(serializedNeighbour));
        originalNeighbour.setFavorite(false);
    }

    /**
     * ldev
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteNeighbourList = new ArrayList<>();
        for (Neighbour n : neighbours) {
            if (n.isFavorite()) {
                favoriteNeighbourList.add(n);
            }
        }
        return favoriteNeighbourList;
    }
}
