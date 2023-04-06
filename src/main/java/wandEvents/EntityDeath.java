package wandEvents;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EntityDeath implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        System.out.println("Test1");
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
}
