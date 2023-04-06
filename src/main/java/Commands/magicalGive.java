package Commands;

import GUIs.SpellInventory;
import Wands.BaseWand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import WandUtils.getItemStack;
public class magicalGive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && command.getName().equalsIgnoreCase("magicalgive")){

            Player p = (Player) sender;
            if (sender.hasPermission("wands.givewand")){
                Player targetPlayer;
                if(args.length == 0){
                    p.sendMessage(ChatColor.RED+"Please enter a target player.");
                }
                else{
                    String itemName;
                    if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
                        targetPlayer = Bukkit.getPlayerExact(args[0]);
                        itemName = args[1];
                    }
                    else{
                        targetPlayer = p;
                        itemName = args[0];
                    }

                    ItemStack itemStack = getItemStack.getItemStack(itemName);
                    if(itemStack==null){
                        p.sendMessage(ChatColor.RED + "This item doesn't exist.");

                    }else{
                        targetPlayer.getInventory().addItem(itemStack);
                    }
                }


            }
        }
        return true;
    }
}
