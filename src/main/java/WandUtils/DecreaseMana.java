package WandUtils;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DecreaseMana {
    public static void setMana(Player p, ItemStack nmsWand,Integer mana){
        NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
        wandCompound.set("Mana",new NBTTagInt(mana));
        nmsWand.setTag(wandCompound);
        org.bukkit.inventory.ItemStack newItem = CraftItemStack.asBukkitCopy(nmsWand);
        ItemMeta newMeta = newItem.getItemMeta();
        List<String> lore = newMeta.getLore();
        lore.set(3,wandCompound.getInt("Mana")+"/"+wandCompound.getInt("MaxMana"));
        newMeta.setLore(lore);
        newItem.setItemMeta(newMeta);
        p.setItemInHand(newItem);

    }
}
