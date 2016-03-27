package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.parcelManager;
import net.teraoctet.genesys.parcel.GParcel;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.RESERVED_PARCEL;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.formatText;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandParcelFlag implements CommandExecutor {
       
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        if(!player.hasPermission("genesys.parcel.flag")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        GParcel gparcel = null;
  
        if(ctx.getOne("name").isPresent()){
            String parcelName = ctx.<String> getOne("name").get();
            gparcel = parcelManager.getParcel(parcelName);                
        } else {
            gparcel = parcelManager.getParcel(player.getLocation());
        }

        if (gparcel == null){
            player.sendMessage(ChatTypes.CHAT,NO_PARCEL());
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flag <flag> <0|1> : modifie la valeur &6Oui = 1 Non = 0, vous devez être sur la parcelle"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flag> : liste les flags de la parcelle, vous devez être sur la parcelle"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flaglist <NomParcelle> : liste les flags de la parcelle nommee"));
            return CommandResult.success(); 
        } else if (!gparcel.getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,RESERVED_PARCEL());
            return CommandResult.success();   
        }
        
        if(!ctx.getOne("flag").isPresent()){
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();

            builder.title(formatText("&6Flag Parcel"))
                .contents(  formatText("&e/parcel flag <flag> <0|1> : &7modifie la valeur d'un flag"),
                            formatText("&enoEnter : &b" + gparcel.getNoEnter() + " &7ils peuvent entrer sur la parcelle"),
                            formatText("&enoFly : &b" + gparcel.getNoFly() + " &7ils peuvent pas voler au dessus"),
                            formatText("&enoBuild : &b" + gparcel.getNoBuild() + " &7ils peuvent construirent"),
                            formatText("&enoBreak : &b" + gparcel.getNoBreak() + " &7ils peuvent casser"),
                            formatText("&enoInteract : &b" + gparcel.getNoInteract() + " &7ils peuvent ouvrir les portes,coffres..."),
                            formatText("&enoTeleport : &b" + gparcel.getNoTeleport() + " &7ils peuvent se téléporter"),
                            formatText("&enoFire : &b" + gparcel.getNoFire() + " &7mettre le feu"),
                            formatText("&enoMob : &b" + gparcel.getNoMob() + " &7les monstres spawn"),
                            formatText("&enoTNT : &b" + gparcel.getNoTNT() + " &7activation de la TNT"),
                            formatText("&enoCommand : &b" + gparcel.getNoCommand() + " &7ils peuvent taper des commandes"))
                .header(formatText("&eParcel " + gparcel.getName() + " : &7Droits accordés aux autres joueurs, 0 = Oui, 1 = Non"))
                .padding(Text.of("-"))
                .sendTo(sender);
            return CommandResult.success();	
        } else {
            if(!ctx.getOne("value").isPresent()){
                player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
                return CommandResult.success(); 
            }
            
            String flag = ctx.<String> getOne("flag").get();
            Integer value = ctx.<Integer> getOne("value").get(); 
            
            if (value != 1 || value != 0){
                player.sendMessage(ChatTypes.CHAT,USAGE("/parcel flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
                return CommandResult.success();	
            }
                                                
            switch (flag.toLowerCase()){
                case "noenter":
                    gparcel.setNoEnter(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoEnter = " + value));
                    break;
                case "nofly":
                    gparcel.setNoFly(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoFly = " + value));
                    break;
                case "nobuild":
                    gparcel.setNoBuild(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoBuild = " + value));
                    break;
                case "nobreak":
                    gparcel.setNoBreak(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoBreak = " + value));
                    break;
                case "nointeract":
                    gparcel.setNoInteract(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoInteract = " + value));
                    break;
                case "noteleport":
                    gparcel.setNoTeleport(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoTeleport = " + value));
                    break;
                case "nofire":
                    gparcel.setNoFire(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoFire = " + value));
                    break;
                case "nomob":
                    gparcel.setNoMob(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoMob = " + value));
                    break;
                case "notnt":
                    gparcel.setNoTNT(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoTNT = " + value));
                    break;
                case "nocommand":
                    gparcel.setNoCommand(value);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Flag enregistré : &enoCommand = " + value));
                    break;
            }
            gparcel.update();
            GData.commit();
            return CommandResult.success();	
        }
    }
}