package WandUtils;

import Armors.BaseArmor;
import Wands.AdvancedWand;
import Wands.BaseWand;
import Wands.MasterWand;
import Wands.Spell;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class getItemStack {
    public static ItemStack getItemStack(String name){
        switch(name){
            case "BaseWand" : return BaseWand.createBaseWand();
            case "AdvancedWand" : return AdvancedWand.createAdvancedWand();
            case "MasterWand": return MasterWand.createMasterWand();
            case "spellMagicFireball" :return Spell.makeSpell("Magic Fireball",1,"Launches a magic fireball",3F);
            case "spellHeal": return Spell.makeSpell("Heal",2,"Heals yourself.",5F);
            case "spellIronSkin": return Spell.makeSpell("Iron Skin",3,"Applies Resistance",5F);


            default: return null;
        }
    }
    public static ItemStack getSpellbyId(Integer id){
        switch(id){
            case 1: return Spell.makeSpell("Magic Fireball",1,"Launches a magic fireball",3F);
            case 2: return Spell.makeSpell("Heal",2,"Heals yourself.",5F);
            case 3: return Spell.makeSpell("Iron Skin",3,"Applies Resistance",5F);
            default: return null;
        }
    }
    public static List<ItemStack> getArmorSet(String name){
        switch(name){
            case "speedyarmor": return BaseArmor.createArmor("Mage's Speedy",1,descriptionsArmors(name),
                    Material.LEATHER_HELMET,Material.LEATHER_CHESTPLATE,Material.LEATHER_LEGGINGS,Material.LEATHER_BOOTS,1);
            case "speedyarmormythic": return BaseArmor.createArmor("Mage's MYTHIC Speedy",5,descriptionsArmors(name),
                    Material.LEATHER_HELMET,Material.LEATHER_CHESTPLATE,Material.LEATHER_LEGGINGS,Material.LEATHER_BOOTS,1);
        }
        return null;
    }
    public static List<List<String>> descriptionsArmors(String name){
        switch(name){
            case "speedyarmor":
                return Arrays.asList(
                        Arrays.asList("give speed"),
                        Arrays.asList("give speed"),
                        Arrays.asList("give speed"),
                        Arrays.asList("give speed")
                );
        }
        return Arrays.asList(
                Arrays.asList(""),
                Arrays.asList(""),
                Arrays.asList(""),
                Arrays.asList("")
        );
    }


}
