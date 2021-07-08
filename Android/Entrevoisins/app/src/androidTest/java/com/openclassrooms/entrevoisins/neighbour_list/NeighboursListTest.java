
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoriteNeighbourFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    /**
     * ldev
     */
    private NeighbourApiService mApiservice;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

    /**
     * ldev
     */
        mApiservice = DI.getNeighbourApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        //onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * When we select an item, the detail item is shown and name ok
     */
    @Test
    public void myNeighboursListItem_selectAction_shouldDisplayDetailScreen() {
        //Given : list of neighbour items
        onView(withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        //when perform a click on item
        onView(withId(R.id.list_neighbours)).perform
                //(RecyclerViewActions.scrollToPosition(11),
                (RecyclerViewActions.actionOnItemAtPosition(3, click()));

        //onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        //Then assert Displayed Detail Activity and Neighbour name check
        onView(withId(R.id.activity_neighbour_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.seeProfil)).check(matches(withText(mApiservice.getNeighbours().get(3).getName())));
    }
    /**ldev
     * When we set a neighbour favorite , the neighbour is shown in favorite Neighbour list
     */
    @Test
    public void myFavoriteTab_selectAction_shouldDisplayOnlyFavoriteNeighboursSettedByStarButton() {
            //Given : list of neighbour items
            //when perform a click on item
        //onView(ViewMatchers.withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.addFavouriteFBA)).perform(ViewActions.click());
        pressBack();
        onView(withId(R.id.list_neighbours)).perform(click((ViewAction) withId(R.id.tabItem2)));
        //onView(withId(R.id.tabItem2)).perform (click());
        //onView(withContentDescription("Favorites")).perform(click());
            //then
        //onView(withId(R.id.container)).perform(ViewActions.swipeLeft());
        onView(withId(R.id.list_Favorites_neighbours)).check(ViewAssertions.matches(isDisplayed()));

    }
}
