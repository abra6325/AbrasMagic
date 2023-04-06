package net.abrasmagic.abrasmagic;
import Commands.armorCommand;
import Commands.magicalGive;
import Commands.switchSpellsGuiOpen;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import wandEvents.EntityDeath;
import wandEvents.GuiEvents;
import wandEvents.rightClick;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbrasMagic extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new rightClick(),this);
        getServer().getPluginManager().registerEvents(new EntityDeath(),this);
        getServer().getPluginManager().registerEvents(new GuiEvents(),this);
        getCommand("magicalgive").setExecutor(new magicalGive());
        getCommand("switchspells").setExecutor(new switchSpellsGuiOpen());
        getCommand("magicalgivearmor").setExecutor(new armorCommand());
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player p: Bukkit.getServer().getOnlinePlayers()){
                    ItemStack helmet = p.getInventory().getHelmet();
                    ItemStack chestplate = p.getInventory().getChestplate();
                    ItemStack legs = p.getInventory().getLeggings();
                    ItemStack boots = p.getInventory().getBoots();

                    net.minecraft.server.v1_8_R3.ItemStack nmsHelmet = CraftItemStack.asNMSCopy(helmet);
                    net.minecraft.server.v1_8_R3.ItemStack nmsChestplate = CraftItemStack.asNMSCopy(chestplate);
                    net.minecraft.server.v1_8_R3.ItemStack nmsLeggings = CraftItemStack.asNMSCopy(legs);
                    net.minecraft.server.v1_8_R3.ItemStack nmsBoots = CraftItemStack.asNMSCopy(boots);


                    NBTTagCompound helmetCompound = nmsHelmet!=null? ((nmsHelmet.hasTag()) ? nmsHelmet.getTag() : new NBTTagCompound()) : null;
                    NBTTagCompound chestplateCompound = nmsChestplate!=null ? ((nmsChestplate.hasTag()) ? nmsChestplate.getTag() : new NBTTagCompound()) : null;
                    NBTTagCompound legsCompound = nmsLeggings != null ? ((nmsLeggings.hasTag()) ? nmsLeggings.getTag() : new NBTTagCompound()) : null;
                    NBTTagCompound bootsCompound = nmsBoots!= null ? ((nmsBoots.hasTag()) ? nmsBoots.getTag() : new NBTTagCompound()) : null;

                    Integer ID1 = helmetCompound != null ? helmetCompound.getInt("setID") : 0;
                    Integer ID2 =  chestplateCompound!=null ?  chestplateCompound.getInt("setID") : 0;
                    Integer ID3 = legsCompound!=null ?   legsCompound.getInt("setID") : 0;
                    Integer ID4 =  bootsCompound!= null ? bootsCompound.getInt("setID") : 0;
                    //full set bonus
                    if(ID1==ID2&& ID2.equals(ID3) && ID3.equals(ID4)){
                        Integer setID = ID1;
                        if(setID == 1){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,300,2,true));
                        }
                    }

                }
            }
        }, 0L, 1L);
    }

}
