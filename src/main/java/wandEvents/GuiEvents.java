package wandEvents;
import WandUtils.*;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory().getTitle().equalsIgnoreCase("Switch Spells")){

            if (e.getCurrentItem() == null) return;
            ItemStack DDDDDD = e.getCurrentItem();

            if(DDDDDD != null){
                net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(DDDDDD);
                NBTTagCompound compound = new NBTTagCompound();
                if(nmsCopy.hasTag()){
                    compound = nmsCopy.getTag();
                }


                if(compound.getInt("id")==4) {
                    Inventory i = e.getInventory();
                    System.out.println(i.getItem(4).getItemMeta().getDisplayName());
                    net.minecraft.server.v1_8_R3.ItemStack nmsCopycurWand = CraftItemStack.asNMSCopy(i.getItem(4));
                    NBTTagCompound wandCompound = (nmsCopycurWand.hasTag()) ? nmsCopycurWand.getTag() : new NBTTagCompound();
                    if (wandCompound.getInt("isWand") == 1) {
                        List<Integer> spells = new ArrayList<>();
                        for (int j : wandCompound.getIntArray("StoredSpells")) {
                            spells.add(j);
                        }
                        Integer current = 0;
                        for (Integer l : spells) {
                            i.setItem(9 + current, getItemStack.getSpellbyId(l));
                            current++;
                        }

                    }
                }

                if(compound.getInt("id")==3){
                    e.setCancelled(true);
                    Inventory i2 = e.getInventory();
                    if(i2.getItem(4).getType() == Material.STICK){
                        System.out.println("stage1");
                        net.minecraft.server.v1_8_R3.ItemStack nmsWand = CraftItemStack.asNMSCopy(i2.getItem(4));
                        NBTTagCompound wandCompound2 = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
                        if(wandCompound2.getInt("isWand")==1){
                            List<Integer> spells = new ArrayList<>();
                            System.out.println("stage02");
                            for(int j=9;j<=17;j++){
                                ItemStack curSpell = i2.getItem(j);
                                if(curSpell != null){
                                    net.minecraft.server.v1_8_R3.ItemStack nmsSpell = CraftItemStack.asNMSCopy(curSpell);
                                    NBTTagCompound spellCompound = (nmsSpell.hasTag()) ? nmsSpell.getTag() : new NBTTagCompound();
                                    System.out.println("stage3");
                                    if(spellCompound.getInt("isSpell")==1){
                                        System.out.println("stage4");
                                        spells.add(spellCompound.getInt("SpellId"));
                                    }
                                }else{
                                    spells.add(0);
                                }
                            }
                            int [] newSpells = new int[spells.size()];
                            int currentIndex = 0;
                            for(int l:spells){
                                newSpells[currentIndex] = l;
                                currentIndex++;
                            }
                            wandCompound2.set("StoredSpells",new NBTTagIntArray(Arrays.copyOf(newSpells,newSpells.length)));
                            wandCompound2.set("CurSpell", new NBTTagInt(newSpells[wandCompound2.getInt("spellIndex")]));
                            nmsWand.setTag(wandCompound2);
                            i2.setItem(4,CraftItemStack.asBukkitCopy(nmsWand));
                            System.out.println("stage5");
                        }
                    }
                    e.setCancelled(true);
                }
                else if (compound.getInt("isWand")!=1&&compound.getInt("isSpell")!=1){
                    e.setCancelled(true);
                }

        }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory().getTitle().equalsIgnoreCase("Switch Spells")){
            ItemStack toreturn = e.getInventory().getItem(4);
            e.getPlayer().getInventory().addItem(toreturn);
        }
    }
}
