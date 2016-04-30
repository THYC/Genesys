package net.teraoctet.genesys.commands.economy;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.economy.ItemShop;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.item.ItemTypes.EMERALD;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandShopPurchase implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.admin.shop")) {
            Player player = (Player) src;
            
            Optional<String> locationString = ctx.<String> getOne("locationstring");
            Optional<ItemShop> itemShop = itemShopManager.getItemShop(locationString.get());
            if(itemShop.isPresent()){
                ItemStack is = itemShop.get().getItemStack();
                int price = itemShop.get().getPriceInt();
                
                Inventory items = player.getInventory().query(EMERALD);
                if(player.getItemInHand().isPresent()){
                    if(itemShopManager.hasCoinPurses(player.getItemInHand().get())){
                        Optional<ItemStack> coin = itemShopManager.removeCoin(price,player.getItemInHand().get());
                        if(coin.isPresent()){
                            player.setItemInHand(coin.get());
                            player.getInventory().offer(is);
                            return CommandResult.success();
                        } else {
                            player.sendMessage(MESSAGE("&bPas assez de monnaie dans votre bourse"));
                        }
                    }
                }
                if (items.peek(price).filter(stack -> stack.getQuantity() == price).isPresent()) {
                    items.poll(price);
                    player.getInventory().offer(is);
                } else {
                    player.sendMessage(MESSAGE("&eTransaction annul\351e, vous n'avez pas le nombre d'emeraudes suffisant sur vous"));  
                }
                return CommandResult.success();
            }
                        
        }    
                   
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
