package gametree;

/**
 * Created by hendr_000 on 1/17/2017.
 */
public class GlobalCounter {
    private static int counter = 0;

    public static void increaseCounter(){
        counter++;
    }

    public static int getCounter(){
        return counter;
    }
}
