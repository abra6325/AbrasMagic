package wandEvents;
import WandUtils.DecreaseMana;
import WandUtils.WandConstants;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import WandUtils.NameGetter;
import java.util.ArrayList;
import java.util.List;

public class rightClick implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction()==Action.RIGHT_CLICK_AIR){
            ItemStack nmsWand = CraftItemStack.asNMSCopy(p.getItemInHand());
            NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
            if( wandCompound.getInt("isWand")==1){
                if(p.isSneaking()){
                    List<Integer> spells = new ArrayList<>();
                    for(int i : wandCompound.getIntArray("StoredSpells")){
                        spells.add(i);
                    }

                    Integer currentSpell = (Integer) wandCompound.getInt("CurSpell");
                    Integer index = wandCompound.getInt("spellIndex");
                    if(index<spells.size()-1){
                        wandCompound.set("CurSpell",new NBTTagInt(spells.get(index+1)));
                        wandCompound.set("spellIndex",new NBTTagInt(index+1));
                    }else if (index == spells.size()-1){
                        wandCompound.set("CurSpell",new NBTTagInt(spells.get(0)));
                        wandCompound.set("spellIndex",new NBTTagInt(0));
                    }
                    String spellName = NameGetter.getName(wandCompound.getInt("CurSpell"));
                    nmsWand.setTag(wandCompound);
                    org.bukkit.inventory.ItemStack newItem = CraftItemStack.asBukkitCopy(nmsWand);
                    ItemMeta newMeta = newItem.getItemMeta();
                    List<String> lore = newMeta.getLore();
                    lore.set(2,"CurrentSpell "+spellName);
                    newMeta.setLore(lore);
                    newItem.setItemMeta(newMeta);

                    p.setItemInHand(newItem);
                    p.sendMessage("Swapped to spell "+ChatColor.GOLD+spellName+ChatColor.WHITE+"!");
                    return;
                }
                Integer curSpell = wandCompound.getInt("CurSpell");
                if(WandConstants.activeCooldowns.containsKey(curSpell)){
                    if(System.currentTimeMillis() - WandConstants.activeCooldowns.get(curSpell) >= WandConstants.getWandCooldown(curSpell)*wandCompound.getFloat("CooldownReduce")){
                        WandConstants.activeCooldowns.remove(1);
                    }else{

                        p.sendMessage(ChatColor.DARK_RED+"Still on Cooldown! "+((WandConstants.getWandCooldown(curSpell)*wandCompound.getFloat("CooldownReduce"))-(System.currentTimeMillis() - WandConstants.activeCooldowns.get(curSpell)))/1000 + " seconds left!");
                        return;
                    }

                }
                switch(curSpell){
                    case 0:
                        p.sendMessage(ChatColor.RED + "There is no spell selected.");
                        break;
                    case 1:

                        if(wandCompound.getInt("Mana")>=5) {

                            Fireball fireball = (Fireball) p.getWorld().spawnEntity(p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(1)), EntityType.FIREBALL);
                            fireball.setShooter(p);
                            fireball.setYield(0);
                            fireball.setDirection(p.getLocation().getDirection());
                            fireball.setCustomName("Magic Fireball");
                            fireball.setIsIncendiary(false);
                            Integer toSet = wandCompound.getInt("Mana")-5;
                            if(toSet <0){
                                toSet = 0;
                            }
                            DecreaseMana.setMana(p,nmsWand,toSet);
                            WandConstants.activeCooldowns.put(1,System.currentTimeMillis());
                        }else{
                            p.sendMessage(ChatColor.YELLOW+"You Don't have enough Energy to cast that Spell!");
                        }
                        break;
                    case 2:
                        if(wandCompound.getInt("Mana")>=20){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,40 + (wandCompound.getInt("Power")*10),2,false));
                            Integer toSet = wandCompound.getInt("Mana")-20;
                            if(toSet <0) toSet = 0;
                            DecreaseMana.setMana(p,nmsWand,toSet);
                            WandConstants.activeCooldowns.put(2,System.currentTimeMillis());

                        }
                        break;
                    case 3:
                        if(wandCompound.getInt("Mana")>=20){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,1000+(wandCompound.getInt("Power")*10),2,false));
                            Integer toSet = wandCompound.getInt("Mana")-20;
                            if(toSet <0) toSet = 0;
                            DecreaseMana.setMana(p,nmsWand,toSet);
                            WandConstants.activeCooldowns.put(3,System.currentTimeMillis());
                        }
                }
            }
        }
    }
    @EventHandler
    public void onLand(ProjectileHitEvent e){
        if(e.getEntity().getCustomName()!= null & e.getEntity().getCustomName().equals("Magic Fireball")){
            for(Entity entity : e.getEntity().getNearbyEntities(2,2,2)){
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;

                    Player p = (Player) e.getEntity().getShooter();
                    if(livingEntity == p){
                        continue;
                    }
                    if(p.getItemInHand().equals(Material.AIR)){
                        livingEntity.damage(2);
                        return;
                    }
                    ItemStack nmsWand = CraftItemStack.asNMSCopy(p.getItemInHand());
                    NBTTagCompound wandCompound = (nmsWand.hasTag()) ? nmsWand.getTag() : new NBTTagCompound();
                    if(wandCompound.getInt("isWand")==1) {
                        livingEntity.damage(wandCompound.getInt("Power")+1);
                    }else{
                        livingEntity.damage(2);
                    }
                }
            }
        }
    }
}
