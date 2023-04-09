package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static Map<String,PlayerMemory> onlinePlayerStats = new HashMap<>();
    public static String getFolderPath(Player p){
        return Bukkit.getServer().getPluginManager().getPlugin("AbrasMagic").getDataFolder() + "/player/" + p.getUniqueId();

    }

    public static PlayerMemory getPlayerMemory(Player p ){
        if(!onlinePlayerStats.containsKey(p.getUniqueId().toString())){
            PlayerMemory m = new PlayerMemory();
            m.init();
            onlinePlayerStats.put(p.getUniqueId().toString(),m);
            return m;
        }
        return onlinePlayerStats.get(p.getUniqueId().toString());

    }
    public static void setPlayerMemory(Player p,PlayerMemory m){
        if(m != null){
        onlinePlayerStats.put(p.getUniqueId().toString(),m);}
        else{
            onlinePlayerStats.remove(p.getUniqueId().toString());

        }
    }


}
