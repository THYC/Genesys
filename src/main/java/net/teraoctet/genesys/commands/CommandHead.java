package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.serverManager;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.RepresentedPlayerData;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.profile.GameProfile;

public class CommandHead implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.head")) { 
            Player player = (Player) src;           
            Optional<String> head = ctx.<String> getOne("head");
            ItemStack.Builder builder = getGame().getRegistry().createBuilder(ItemStack.Builder.class);
            ItemStack skull = builder.itemType(ItemTypes.SKULL).quantity(1).build();
            skull.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
            RepresentedPlayerData playerData = skull.getOrCreate(RepresentedPlayerData.class).get();
                
            if (head.isPresent()){
                Optional<GameProfile> gpOpt = serverManager.getPlayerProfile(head.get());    
                if (gpOpt.isPresent()){
                    playerData = playerData.set(playerData.owner().set(gpOpt.get()));
                    skull.offer(playerData);
                    player.setItemInHand(skull);
                    player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of your head. Enjoy!"));
                    return CommandResult.success();
                } else {
                    return CommandResult.empty();
                }
            } else {    
                playerData = playerData.set(playerData.owner().set(player.getProfile()));
                skull.offer(playerData);
                player.setItemInHand(skull);
                player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of player's head. Enjoy!"));
            }  
        } 
        
        else if (src instanceof ConsoleSource){
           src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }

        return CommandResult.empty();
    }
}