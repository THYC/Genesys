package net.teraoctet.genesys.commands;

import java.util.Optional;
import net.teraoctet.genesys.utils.GServer;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
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

public class CommandHead implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        
                   
        if(src instanceof Player && src.hasPermission("genesys.head")) { 
            Player player = (Player) src;           
            Optional<String> head = ctx.<String> getOne("head");
            
            if (head.isPresent()){
                Player Target = GServer.getPlayer(head.get());

                if (Target != null){
                    ItemStack.Builder builder = getGame().getRegistry().createBuilder(ItemStack.Builder.class);
                    ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();
                    skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

                    RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
                    playerData = playerData.set(playerData.owner().set(player.getProfile()));
                    skullStack.offer(playerData);
                    player.setItemInHand(skullStack);

                    player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of your head. Enjoy!"));
                } else {
                    ItemStack.Builder builder = getGame().getRegistry().createBuilder(ItemStack.Builder.class);
                    ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();
                    skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

                    RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
                    playerData = playerData.set(playerData.owner().set(player.getProfile()));
                    skullStack.offer(playerData);

                    player.setItemInHand(skullStack);

                    player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of player's head. Enjoy!"));
                }
            }   
        } 
        
        else if (src instanceof ConsoleSource){
           src.sendMessage(NO_CONSOLE());  
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }

        return CommandResult.success();
    }
}