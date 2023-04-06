package GUIs;

import net.minecraft.server.v1_8_R3.ItemEnchantedBook;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpellInventory {
    public static void openInventory(Player p){
        Inventory inventory = Bukkit.getServer().createInventory(p,18,"Switch Spells");
        ItemStack blank = clickableItem("",(byte) 8,0);
        ItemStack click = clickableItem("Put wand here",(byte) 4,1);
        ItemStack spellSlot = clickableItem("Spell here",(byte)3,2);
        ItemStack confirm = clickableItem("Confirm!",(byte)5,3);
        ItemStack confirmPutWant = clickableItem("Insert Wand",(byte) 6,4);
        ItemStack[] arrangement = {blank,blank,blank,blank,null,confirm,confirmPutWant,blank,blank,null,null,null,null,null,null,null,null,null};

        inventory.setContents(arrangement);

        p.openInventory(inventory);


    }
    public static ItemStack clickableItem(String name,byte color,Integer id){
        ItemStack clickAble = new ItemStack(Material.STAINED_GLASS,1,color);
        ItemMeta clickAbleMeta = clickAble.getItemMeta();
        clickAbleMeta.setDisplayName(name);
        clickAble.setItemMeta(clickAbleMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(clickAble);
        NBTTagCompound compound = (nmsCopy.hasTag()) ? nmsCopy.getTag() : new NBTTagCompound();
        compound.set("id",new NBTTagInt(id));
        nmsCopy.setTag(compound);
        clickAble = CraftItemStack.asBukkitCopy(nmsCopy);

        return clickAble;
    }
}
