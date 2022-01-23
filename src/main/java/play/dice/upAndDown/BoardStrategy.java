package play.dice.upAndDown;

import java.util.HashMap;
import java.util.Map;

public class BoardStrategy {

    private static Map<Integer,Integer> positionsMap = new HashMap<>();
    private static final Integer MAX_POINTS = new Integer(100);
    static {
        positionsMap.put(2,17);
        positionsMap.put(14,48);
        positionsMap.put(21,15);
        positionsMap.put(29,9);
        positionsMap.put(35,5);
        positionsMap.put(37,78);
        positionsMap.put(41,75);
        positionsMap.put(47,23);
        positionsMap.put(55,89);
        positionsMap.put(56,31);
        positionsMap.put(67,92);
        positionsMap.put(69,45);
        positionsMap.put(78,96);
        positionsMap.put(80,40);
        positionsMap.put(94,77);
        positionsMap.put(98,88);
    }

    public static Integer getActualPosition(Integer prevPosition, Integer currentPoints){
        Integer currentPosition = prevPosition + currentPoints;
        if(currentPosition > MAX_POINTS){
            return prevPosition;
        }
        return positionsMap.get(currentPosition);
    }
}
