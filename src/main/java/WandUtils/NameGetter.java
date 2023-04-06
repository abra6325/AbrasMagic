package WandUtils;

public class NameGetter {
    public static String getName(Integer id){
        switch (id) {
            case 0: return "None";
            case 1: return "Magic Fireball";
            case 2: return "Heal";
            case 3: return "Iron Skin";
        }
        return "UNKNOWN";
    }
}
