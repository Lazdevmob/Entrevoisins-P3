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
    //private Neighbour neighbour;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }



    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /*** ldev*/
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        //given: some neighbours are favorite
        service.getNeighbours().forEach(neighbour -> neighbour.setFavorite(false));
        //assertTrue(service.getFavoriteNeighbours().isEmpty());
        Neighbour favoriteNeighbour = service.getNeighbours().get(0);
        Neighbour favoriteNeighbour2 = service.getNeighbours().get(1);
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

    /*** ldev*/
    @Test
    public void addFavoriteNeighbourWithSuccess() {
        Neighbour FavoriteNeighbourToAdd = service.getNeighbours().get(0);
        service.addFavorite(FavoriteNeighbourToAdd);
        assertTrue(service.getFavoriteNeighbours().contains(FavoriteNeighbourToAdd));
    }
    // /**
    //  * ldev
    //  */
    // @Test
    // public void addFavoriteNeighbourWithSuccess() {
    //     Neighbour favoriteNeighbourToAdd = service.getNeighbours().get(0);
    //     Neighbour serializedFavoriteNeighbourToAdd = (Neighbour) (Serializable) favoriteNeighbourToAdd;
    //     service.addFavorite(serializedFavoriteNeighbourToAdd);
    //     assertTrue(service.getFavoriteNeighbours().contains(favoriteNeighbourToAdd));
    // }

    /*** ldev*/
    @Test
    public void removeFavoriteNeighbourWithSuccess() {
        Neighbour FavoriteNeighbourToDelete = service.getNeighbours().get(0);
        Neighbour FavoriteNeighbourNotToDelete = service.getNeighbours().get(1);
        service.addFavorite(FavoriteNeighbourToDelete);
        service.addFavorite(FavoriteNeighbourNotToDelete);
        service.removeFavorite(FavoriteNeighbourToDelete);
        assertFalse(service.getFavoriteNeighbours().contains(FavoriteNeighbourToDelete));
    }

  // /**
  //  * ldev
  //  */
  // @Test
  // public void removeFavoriteNeighbourWithSuccess() {
  //     Neighbour FavoriteNeighbourToDelete = service.getNeighbours().get(0);
  //     Neighbour FavoriteNeighbourNotToDelete = service.getNeighbours().get(1);
  //     service.addFavorite(FavoriteNeighbourToDelete);
  //     service.addFavorite(FavoriteNeighbourNotToDelete);
  //     service.removeFavorite(FavoriteNeighbourToDelete);
  //     assertFalse(service.getFavoriteNeighbours().contains(FavoriteNeighbourToDelete));
  // }


}