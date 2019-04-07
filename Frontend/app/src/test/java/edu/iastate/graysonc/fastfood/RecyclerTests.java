package edu.iastate.graysonc.fastfood;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;

import static junit.framework.TestCase.assertEquals;

public class RecyclerTests {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Food food;

    @Test
    public void testGetFoodMethods(){
        Mockito.when(food.getCalorieTotal()).thenReturn(30);
        Mockito.when(food.getCarbTotal()).thenReturn(30);
        Mockito.when(food.getFatTotal()).thenReturn(30);
        Mockito.when(food.getId()).thenReturn(30);
        recycler_card card = new recycler_card(food);
        assertEquals(card.getFoodObj(),food);
        assertEquals(card.getFoodObj().getCalorieTotal(),30);
        assertEquals(card.getFoodId(),30);
    }

    @Test
    public void testGetFoodGetString(){
        recycler_card card = new recycler_card(food);
        assertEquals(card.getData(),"");
    }









}
