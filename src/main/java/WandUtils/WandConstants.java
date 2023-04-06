package WandUtils;

import java.util.*;

public class WandConstants {

    public static Map<Integer,Long> activeCooldowns = new HashMap<Integer,Long>();
    public static Float getWandCooldown(Integer wandID){
        List<Integer> wandCds = new ArrayList<>(Arrays.asList(0,3000,5000,5000));
        return wandCds.get(wandID).floatValue();

    }

}
