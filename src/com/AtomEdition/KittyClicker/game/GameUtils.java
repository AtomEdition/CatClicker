package com.AtomEdition.KittyClicker.game;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 16.09.14
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameUtils {

    public static final String PREFERENCES_NAME = "kitty_settings", PREFERENCES_HIGH_SCORE = "high_score";
    public static final String PREFERENCES_SOUNDS = "sounds", PREFERENCES_MUSIC = "music", PREFERENCES_VIBRATE = "vibrate";
    public static final Integer CATS_IMAGES_COUNT = 21;
    public static final Integer TEXTURES_COUNT = 6;
    public static Integer SCREEN_WIDTH;
    public static Integer SCREEN_HEIGHT;
    public static final Integer INTERFACE_SIZE = 60+20+10+10;
    public static final Integer VIBRATE_TIME = 100;
    public static final Integer VIBRATE_TIME_SHORT = 40;
    public static final Integer VIBRATE_TIME_MAIN_MENU = 400;
    public static final Integer KITTY_SIZE = 60;
    public static final Integer COUNT_TO_START_MOVE = 1;
    public static final Integer START_COUNT = 1; // no more than 20
    public static final Integer COUNT_DOWN_VALUE = 4999; // in milliseconds
    private static final Integer DIFFICULTY_COEFFICIENT = 20; // lesser - higher;
    private static final Integer DIFFICULTY_EDGE_SCORE = 200;
    public static Integer SCORE = 0;
    public static Integer HIGH_SCORE = 0;
    public static boolean SOUNDS = true;
    public static boolean MUSIC = true;
    public static boolean VIBRATE = true;

    /**
     * Adding new object to list. Checks random position for a new object that won't be too close to already existing
     * objects. Recursive.
     * @param kitties List of objects to check.
     * @return new object that adds in list.
     */
    public static Kitty checkPosition(LinkedList<Kitty> kitties){
        Random random = new Random();
        Integer x,y;
        x = random.nextInt(SCREEN_WIDTH)+1;
        y = random.nextInt(SCREEN_HEIGHT)+1;
        if (kitties.size()!=0)
            for(Kitty kitty : kitties){
                if(((Math.abs(x- kitty.getX())<10)||(Math.abs(kitty.getX()-x)<10))&&((Math.abs(y- kitty.getY())<10)||(Math.abs(kitty.getY()-y)<10))){
                    return checkPosition(kitties);
                }
            }
        return new Kitty(x,y);
    }

    /**
     * Generating the difficulty of the game and list of objects with their own positions and speed (depending on
     * difficulty). Sets one object in true (provides increasing score and generation the new level) or false.
     * @param size count of objects to generate.
     * @return list of generated objects.
     */
    public static LinkedList<Kitty> generateKitties(Integer size){
        Random random = new Random();
        Integer flagTrue = random.nextInt(size);
        Integer difficulty = 0;
        if (SCORE<DIFFICULTY_EDGE_SCORE)
            difficulty = SCORE / DIFFICULTY_COEFFICIENT +1;
        else
            difficulty = DIFFICULTY_EDGE_SCORE / DIFFICULTY_COEFFICIENT +1;
        LinkedList<Kitty> kitties = new LinkedList<Kitty>();
        for (int i=0; i<size; i++){
            kitties.add(checkPosition(kitties));
            kitties.getLast().setSpeedX(random.nextInt(2*difficulty + 1) - difficulty);
            kitties.getLast().setSpeedY(random.nextInt(2*difficulty + 1) - difficulty);
        }
        kitties.get(flagTrue).setFlag(true);
        return kitties;
    }

    /**
     * Protects objects from leaving the screen and changes theirs directions after collide with borders.
     * @param kitty object to check.
     */
    public static void setMoving(Kitty kitty){
        if((kitty.getX()<=1)||(kitty.getX()>= SCREEN_WIDTH))
            kitty.setSpeedX(kitty.getSpeedX()*(-1));
        if((kitty.getY()<=1)||(kitty.getY()>= SCREEN_HEIGHT))
            kitty.setSpeedY(kitty.getSpeedY()*(-1));
        kitty.move();
    }

    public static LinkedList<Integer> excludeRandom(Integer count){
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i=0; i<CATS_IMAGES_COUNT; i++)
            linkedList.add(i);
        Random random = new Random();
        for (int i=0; i<count; i++){
            Integer index = random.nextInt(linkedList.size());
            result.add(linkedList.get(index));
            linkedList.remove(linkedList.get(index));
        }
        return result;
    }

    public static LinkedList<Integer> excludeRandom(Integer count, LinkedList<Integer> excludes){
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i=0; i<CATS_IMAGES_COUNT; i++)
            if(!excludes.contains(i))
                linkedList.add(i);
        Random random = new Random();
        for (int i=0; i<count; i++){
            Integer index = random.nextInt(linkedList.size());
            result.add(linkedList.get(index));
            linkedList.remove(linkedList.get(index));
        }
        return result;
    }

}
