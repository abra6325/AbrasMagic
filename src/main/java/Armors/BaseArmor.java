package Armors;

import WandUtils.Rarities.*;
import WandUtils.getRarity;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

public class BaseArmor {
    public static ArrayList<ItemStack> createArmor(String armorName, Integer rarity, List<List<String>> descs, Material a, Material b, Material c, Material d,Integer setID){
        List<List<String>> realDesks = new ArrayList<>();
        for(List<String> i:descs) realDesks.add(i);
        if((4-descs.size())>=1){
            for(int i=0;i<(4-descs.size());i++){
                realDesks.add(new ArrayList<>());
            }
        }

        ItemStack helmet = createPiece(armorName+" Helmet",rarity,realDesks.get(0),a,setID);
        ItemStack chestPlate = createPiece(armorName + " Chestplate",rarity,realDesks.get(1),b,setID);
        ItemStack leggings = createPiece(armorName + " Leggings",rarity,realDesks.get(2),c,setID);
        ItemStack boots = createPiece(armorName + " Boots",rarity,realDesks.get(3),d,setID);

        return new ArrayList<ItemStack>(Arrays.asList(helmet,chestPlate,leggings,boots));


    }
    public static ItemStack createPiece(String pieceName, Integer rarity, List<String> desc, Material material, List<Dictionary<String,Object>> tags){
        ItemStack piece= new ItemStack(material);
        ItemMeta piecemeta = piece.getItemMeta();
        piecemeta.setDisplayName(pieceName);
        desc.add(getRarity.withRarity(getRarity.rarityName(rarity),rarity));
        piecemeta.setLore(desc);
        piece.setItemMeta(piecemeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsPiece = CraftItemStack.asNMSCopy(piece);
        NBTTagCompound pieceCompound = (nmsPiece.hasTag())?nmsPiece.getTag():new NBTTagCompound();
        for(int i=0;i<tags.size();i++){
            String curKey = tags.get(i).keys().nextElement();
            Object thing = tags.get(i).get(curKey);

            pieceCompound.set(curKey, (NBTBase) thing);

        }
        nmsPiece.setTag(pieceCompound);


        return CraftItemStack.asBukkitCopy(nmsPiece);
    }
    public static ItemStack createPiece(String pieceName, Integer rarity, List<String> desc, Material material,Integer setID){
        ItemStack piece= new ItemStack(material);
        ItemMeta piecemeta = piece.getItemMeta();
        piecemeta.setDisplayName(getRarity.withRarity(pieceName,rarity));
        piecemeta.setLore(desc);
        piece.setItemMeta(piecemeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsPiece = CraftItemStack.asNMSCopy(piece);
        NBTTagCompound pieceCompound = (nmsPiece.hasTag())?nmsPiece.getTag():new NBTTagCompound();
        pieceCompound.setInt("setID",setID);
        pieceCompound.setInt("rarity",rarity);
        nmsPiece.setTag(pieceCompound);


        return CraftItemStack.asBukkitCopy(nmsPiece);
    }
}
