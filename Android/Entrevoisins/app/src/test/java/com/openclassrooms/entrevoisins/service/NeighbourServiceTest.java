package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;


import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private int POSITION_ITEM = 0;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    /** we ensure that the new neighbour list created is the same of the original neighbour list*/
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /** we ensure that a new neighbour created is in the new neighbour list*/
    @Test
    public void createNeighbourWithSuccess() {
        //given: a new neighbour
        Neighbour newNeighbour = new Neighbour(13, "Toto", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km",
                "+33 6 00 00 00 00 ",  "Bonjour !Je souhaiterais faire de la marche nordique...");
        //when: I add the newNeighbour to the neighbourList
        service.createNeighbour(newNeighbour);
        List<Neighbour> neighbours = service.getNeighbours();
        assertTrue(neighbours.contains(newNeighbour));
    }



    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(POSITION_ITEM);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /*** When we set a neighbour favorite , this neighbour is in the new favorite Neighbour list*/
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        //given: some neighbours are favorite
        service.getNeighbours().forEach(neighbour -> neighbour.setFavorite(false));
        //assertTrue(service.getFavoriteNeighbours().isEmpty());
        Neighbour favoriteNeighbour = service.getNeighbours().get(POSITION_ITEM);
        Neighbour favoriteNeighbour2 = service.getNeighbours().get(POSITION_ITEM+1);
        favoriteNeighbour.setFavorite(true);
        favoriteNeighbour2.setFavorite(true);

        //when: I retrieve the favorite neighbours list
        List<Neighbour> actualFavoriteNeighbours = service.getFavoriteNeighbours();

        //then: the list contains all and only the favorite neighbours
        List<Neighbour> expectedFavoriteNeighbours = new ArrayList<>();
        expectedFavoriteNeighbours.add(favoriteNeighbour);
        expectedFavoriteNeighbours.add(favoriteNeighbour2);
        assertThat(actualFavoriteNeighbours,
                IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFavoriteNeighbours.toArray()));
        assertEquals(2, actualFavoriteNeighbours.size());
    }

    /** we ensure that when we add favorite a neighbour,this neighbour is in the new favorite Neighbour list*/
    @Test
    public void addFavoriteNeighbourWithSuccess() {
        Neighbour FavoriteNeighbourToAdd = service.getNeighbours().get(POSITION_ITEM);
        service.addFavorite(FavoriteNeighbourToAdd);
        assertTrue(service.getFavoriteNeighbours().contains(FavoriteNeighbourToAdd));
    }


    /** we ensure that a deleted neighbour is no longer in the new neighbour list*/
    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        Neighbour FavoriteNeighbourToDelete = service.getNeighbours().get(POSITION_ITEM);
        Neighbour FavoriteNeighbourNotToDelete = service.getNeighbours().get(POSITION_ITEM+1);
        service.addFavorite(FavoriteNeighbourToDelete);
        service.addFavorite(FavoriteNeighbourNotToDelete);
        service.removeFavorite(FavoriteNeighbourToDelete);
        assertFalse(service.getFavoriteNeighbours().contains(FavoriteNeighbourToDelete));
    }

}