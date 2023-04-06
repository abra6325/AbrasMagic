package Wands;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spell {
    public static ItemStack makeSpell(String spellName,Integer spellId,String description,float cooldown){
        ItemStack spell = new ItemStack(Material.BOOK);
        ItemMeta wandMeta = spell.getItemMeta();
        wandMeta.setDisplayName(ChatColor.YELLOW+spellName);
        List<String> lore = new ArrayList<>();
        lore.add("Spell Id: "+spellId);
        lore.add("Cooldown: "+cooldown);
        lore.add(description);
        wandMeta.setLore(lore);
        spell.setItemMeta(wandMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsWand = CraftItemStack.asNMSCopy(spell);
        NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
        wandCompound.set("SpellId",new NBTTagInt(spellId));
        wandCompound.set("isSpell", new NBTTagInt(1));
        nmsWand.setTag(wandCompound);

        spell = CraftItemStack.asBukkitCopy(nmsWand);


        return spell;
    }
}
