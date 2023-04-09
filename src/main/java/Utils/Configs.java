package Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configs {
    private  File file;
    private  FileConfiguration fileConfiguration;
    public  void setUp(String name, PlayerMemory m){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AbrasMagic").getDataFolder() + "/player/"+name,"stats.yml");
        if(!file.exists()){
            try{
                file.createNewFile();

            }catch (IOException e){
                //DICK
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.addDefault("Health",m.getHealth());
        fileConfiguration.addDefault("Hardness",m.getHardness());
        fileConfiguration.addDefault("Invulnerability",m.getInvulnerability());
        fileConfiguration.addDefault("Aggression",m.getAggression());
        fileConfiguration.addDefault("Mana",m.getMana());
        fileConfiguration.addDefault("Gold",m.getGOLD());
        fileConfiguration.addDefault("MaxHealth",m.getMaxHealth());
        fileConfiguration.options().copyDefaults(true);

    }
    public  FileConfiguration get(){
        return fileConfiguration;
    }
    public  void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            System.out.println("FAILED SAVE");
        }
    }
    public  void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
