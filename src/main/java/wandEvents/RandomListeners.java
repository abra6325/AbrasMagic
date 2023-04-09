package wandEvents;

import Utils.Configs;
import Utils.DataStore;
import Utils.PlayerMemory;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RandomListeners implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getKiller()!=null){
            if(e.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                Player p = e.getEntity().getKiller();
                ItemStack origItem = p.getItemInHand();
                net.minecraft.server.v1_8_R3.ItemStack nmsWand = CraftItemStack.asNMSCopy(origItem);
                NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
                if(wandCompound.getInt("isWand")==1) {
                    if (wandCompound.getInt("manaSteal") >= 1) {
                        Integer toAdd = (wandCompound.getInt("Mana") + wandCompound.getInt("manaSteal"));
                        System.out.println(toAdd);
                        if (toAdd > wandCompound.getInt("MaxMana")) {
                            toAdd = wandCompound.getInt("MaxMana");
                        }
                        wandCompound.set("Mana", new NBTTagInt(toAdd));
                    }
                    nmsWand.setTag(wandCompound);
                    ItemStack newItem = CraftItemStack.asBukkitCopy(nmsWand);
                    ItemMeta newMeta = newItem.getItemMeta();
                    List<String> lore = newMeta.getLore();
                    lore.set(3, wandCompound.getInt("Mana") + "/" + wandCompound.getInt("MaxMana"));
                    newMeta.setLore(lore);
                    newItem.setItemMeta(newMeta);
                    p.setItemInHand(newItem);
                    p.sendMessage(ChatColor.DARK_PURPLE+"Replenished "+ChatColor.RED+wandCompound.getInt("manaSteal")+ChatColor.DARK_PURPLE+" Mana!");
                }
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        PlayerMemory m = new PlayerMemory();
        File f = new File(DataStore.getFolderPath(p) + "/stats.yml");
        if(f.exists()){
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            m.setHealth(cfg.getDouble("Health"));
            m.setHardness(cfg.getInt("Hardness"));
            m.setInvulnerability(cfg.getInt("Invulnerability"));
            m.setAggression(cfg.getInt("Aggression"));
            m.setMana(cfg.getInt("Mana"));
            m.setGOLD(cfg.getInt("Gold"));
            m.setMaxHealth(cfg.getDouble("MaxHealth"));

        }
        DataStore.setPlayerMemory(p,m);


    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        PlayerMemory m = DataStore.getPlayerMemory(p);
        m.setHealth(m.getMaxHealth());
        DataStore.setPlayerMemory(p,m);

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        PlayerMemory m = DataStore.getPlayerMemory(p);
        File f = new File(DataStore.getFolderPath(p)+"/stats.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("Health",m.getHealth());
        cfg.set("Hardness",m.getHardness());
        cfg.set("Invulnerability",m.getInvulnerability());
        cfg.set("Aggression",m.getAggression());
        cfg.set("Mana",m.getMana());
        cfg.set("Gold",m.getGOLD());
        cfg.set("MaxHealth",m.getMaxHealth());
        try {
            cfg.save(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DataStore.setPlayerMemory(p,null);
    }
    @EventHandler
    public void EntityHit(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            PlayerMemory m = DataStore.getPlayerMemory(p);
            Double damage = e.getDamage();
            m.Damage((int) Math.round(damage));
            DataStore.setPlayerMemory(p,m);
            p.setHealth((m.getHealth()/m.getMaxHealth())*20);


        }
    }
}
