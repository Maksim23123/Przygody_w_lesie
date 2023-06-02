package Maksym_Smal.studABNS.MyOwn2DGame;

public class Random {
    public static int getRandomInt(int range, boolean notNull) {
        int result = (int) (Math.random() / (1f / (float)range));
        if (result == 0 && notNull) {
            result = getRandomInt(range, notNull);
        }
        return result;
    }
}
