package WandUtils;

import org.bukkit.ChatColor;

public class getRarity {
    public static String withRarity(String s, Integer r){
        switch (r){
            case 0: return ChatColor.WHITE + s;
            case 1: return ChatColor.GREEN + s;
            case 2: return ChatColor.BLUE + s;
            case 3: return ChatColor.DARK_PURPLE + s;
            case 4: return ChatColor.GOLD + s;
            case 5: return ChatColor.UNDERLINE+(ChatColor.DARK_RED+(ChatColor.ITALIC+s));
        }
        return s;
    }
    public static String rarityName(Integer r){
        switch(r){
            case 0: return "COMMON";
            case 1: return "UNCOMMON";
            case 2: return "RARE";
            case 3: return "EPIC";
            case 4: return "LEGENDARY";
            case 5: return "MYTHIC";
        }
        return "COMMON";
    }
}
