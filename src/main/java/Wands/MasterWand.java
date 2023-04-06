package Wands;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterWand {
    public static ItemStack createMasterWand(){
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.BLUE+"Master's Wand");
        List<String> lore = new ArrayList<>();
        lore.add("the highest level of basic wands.");
        lore.add("Power "+4);
        lore.add("CurrentSpell " + "FireBall");
        lore.add("Energy 1000/1000");
        lore.add("SpellSlots "+7);

        lore.add(ChatColor.GOLD+"Energy Steal");
        lore.add(ChatColor.AQUA+"Spell Cooldown -"+ChatColor.RED+"50%");
        lore.add("Upgrade slots: "+3);
        lore.add("Upgrades:none");

        wandMeta.setLore(lore);
        wand.setItemMeta(wandMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsWand = CraftItemStack.asNMSCopy(wand);
        NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
        wandCompound.set("isWand",new NBTTagInt(1));
        wandCompound.set("Power", new NBTTagInt(4));
        wandCompound.set("CurSpell",new NBTTagInt(1));
        wandCompound.set("CooldownReduce",new NBTTagFloat(0.5F));
        wandCompound.set("manaSteal",new NBTTagInt(50));
        wandCompound.set("spellIndex",new NBTTagInt(0));
        wandCompound.set("SpellSlots",new NBTTagInt(7));
        wandCompound.set("MaxMana",new NBTTagInt(4000));
        wandCompound.set("Mana",new NBTTagInt(4000));
        int[] BaseStoredSpells = {1,2,0,0,0,0,0};
        wandCompound.set("StoredSpells", new NBTTagIntArray(Arrays.copyOf(BaseStoredSpells,BaseStoredSpells.length)));
        int[] BaseWandUpgrades = {0,0,0,0,0,0,0};
        wandCompound.set("Upgrades", new NBTTagIntArray(Arrays.copyOf(BaseWandUpgrades,BaseWandUpgrades.length)));
        nmsWand.setTag(wandCompound);

        wand = CraftItemStack.asBukkitCopy(nmsWand);


        return wand;
    }
}
