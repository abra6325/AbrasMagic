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

public class AdvancedWand {
    public static ItemStack createAdvancedWand(){
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.BLUE+"Advanced Wand");
        List<String> lore = new ArrayList<>();
        lore.add("a Better Wand.");
        lore.add("Power "+2);
        lore.add("CurrentSpell " + "FireBall");
        lore.add("Energy 2000/2000");
        lore.add("SpellSlots "+5);
        lore.add("Upgrade slots: "+3);
        lore.add("Upgrades:none");

        lore.add(ChatColor.AQUA+"Spell Cooldown -"+ChatColor.RED+"20%");
        wandMeta.setLore(lore);
        wand.setItemMeta(wandMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsWand = CraftItemStack.asNMSCopy(wand);
        NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
        wandCompound.set("isWand",new NBTTagInt(1));
        wandCompound.set("Power", new NBTTagInt(2));
        wandCompound.set("CurSpell",new NBTTagInt(1));
        wandCompound.set("spellIndex",new NBTTagInt(0));
        wandCompound.set("CooldownReduce",new NBTTagFloat(0.8F));
        wandCompound.set("manaSteal",new NBTTagInt(0));
        wandCompound.set("SpellSlots",new NBTTagInt(5));
        wandCompound.set("MaxMana",new NBTTagInt(2000));
        wandCompound.set("Mana",new NBTTagInt(2000));
        int[] BaseStoredSpells = {1,2,0,0,0};
        wandCompound.set("StoredSpells", new NBTTagIntArray(Arrays.copyOf(BaseStoredSpells,BaseStoredSpells.length)));
        int[] BaseWandUpgrades = {0,0,0,0,0};
        wandCompound.set("Upgrades", new NBTTagIntArray(Arrays.copyOf(BaseWandUpgrades,BaseWandUpgrades.length)));
        nmsWand.setTag(wandCompound);

        wand = CraftItemStack.asBukkitCopy(nmsWand);


        return wand;
    }
}
