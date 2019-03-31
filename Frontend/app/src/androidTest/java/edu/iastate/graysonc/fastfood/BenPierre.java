package edu.iastate.graysonc.fastfood;

import org.junit.Test;

import java.util.ArrayList;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.fragments.HomeFragment;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class BenPierre {
    @Test
    public void test1() {
        recycler_card test = mock(recycler_card.class);
        when(test.getFood()).thenReturn("CheeseBurger");
        when(test.getFoodId()).thenReturn(4);
        assertEquals(test.getFood(), "CheeseBurger");
        assertEquals(test.getFoodId(), 4);
    }

    @Test
    public void test2() {
        Food test = new Food(6, "Cheesburger", 6, 6, 6, 6, 6);
        Food spytest = spy(test);

        doReturn(6).when(spytest).getCalorieTotal();
        doReturn(6).when(spytest).getProteinTotal();
        doReturn(6).when(spytest).getCarbTotal();
        doReturn(6).when(spytest).getFatTotal();
        doReturn(6).when(spytest).getLocation();
        assertEquals(6, spytest.getCalorieTotal());
        assertEquals(6, spytest.getProteinTotal());
        assertEquals(6, spytest.getCarbTotal());
        assertEquals(6, spytest.getFatTotal());
        assertEquals(6, spytest.getLocation());
    }

    @Test
    public void test3(){
        HomeFragment test = mock(HomeFragment.class);
        when(test.getMacro(new recycler_card(new Food(6,"Cheese",6,6,6,6,6)), HomeFragment.macrosENUM.CARBS)).thenReturn(6);
        assertEquals(test.getMacro(new recycler_card(new Food(6,"Cheese",6,6,6,6,6)), HomeFragment.macrosENUM.CARBS),anyInt());
    }
}
