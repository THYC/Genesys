package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.addWorld;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_CREATED;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_EXIST;
import net.teraoctet.genesys.world.GWorld;
import net.teraoctet.genesys.world.WorldManager;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandWorldCreate implements CommandExecutor {
                
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        Player player = null;
        if (sender instanceof Player){
            player = (Player)sender;
            if(!player.hasPermission("genesys.admin.world.worldcreate")) { player.sendMessage(ChatTypes.CHAT,NO_PERMISSIONS()); return CommandResult.success();}
        }
                
        if(!ctx.getOne("name").isPresent()) { 
            sender.sendMessage(USAGE("/worldcreate <name> <environnement> <gamemode> <difficulty>"));
            return CommandResult.success();
        } 
        
        String name = ctx.<String> getOne("name").get();
	String environment = ctx.<String> getOne("environment").get();
	String gamemodeI = ctx.<String> getOne("gamemode").get();
	String difficultyI = ctx.<String> getOne("difficulty").get();
        
        if(getGame().getServer().getWorld(name).isPresent()) {sender.sendMessage(WORLD_EXIST()); return CommandResult.success();}

        Difficulty difficulty;
	DimensionType dimension;
	GeneratorType generator;
	GameMode gamemode;
        
        switch (environment.toLowerCase()){
            case "overworld": dimension = DimensionTypes.OVERWORLD;generator = GeneratorTypes.OVERWORLD;break;
            case "flat": dimension = DimensionTypes.OVERWORLD;generator = GeneratorTypes.FLAT;break;
            case "superflat": dimension = DimensionTypes.OVERWORLD; generator = GeneratorTypes.FLAT;break;
            case "nether": dimension = DimensionTypes.NETHER; generator = GeneratorTypes.NETHER; break;
            case "end": dimension = DimensionTypes.THE_END; generator = GeneratorTypes.THE_END; break;
            default: sender.sendMessage(MESSAGE("&6<environment> = &7'overworld', 'flat', 'superflat', 'nether' ou 'end'"));return CommandResult.success();
        }

        switch (gamemodeI.toLowerCase()){
            case "survival": gamemode = GameModes.SURVIVAL; break;
            case "creative": gamemode = GameModes.CREATIVE; break;
            case "adventure": gamemode = GameModes.ADVENTURE; break;
            case "spectator": gamemode = GameModes.SPECTATOR; break;
            default: sender.sendMessage(MESSAGE("&6<gamemode> = &7survival, creative, adventure ou spectator"));return CommandResult.success();
        }

        switch (difficultyI.toLowerCase()){
            case "easy": difficulty = Difficulties.EASY; break;
            case "hard": difficulty = Difficulties.HARD; break;
            case "normal": difficulty = Difficulties.NORMAL; break;
            case "peaceful": difficulty = Difficulties.PEACEFUL; break;
            default: sender.sendMessage(MESSAGE("&6<Difficulty> = &7easy, hard, normal, peaceful"));return CommandResult.success();
        }
		
        sender.sendMessage(MESSAGE("&7Creation du monde en cours ..."));
            
        WorldCreationSettings worldSettings = getGame().getRegistry().createBuilder(WorldCreationSettings.Builder.class)
                .name(name)
                .enabled(true)
                .loadsOnStartup(true)
                .keepsSpawnLoaded(true)
                .dimension(dimension)
                .generator(generator)
                .gameMode(gamemode)
                .build();
            
        Optional<WorldProperties> worldProperties = getGame().getServer().createWorldProperties(worldSettings);

        if (worldProperties.isPresent()) {
            Optional<World> world = getGame().getServer().loadWorld(worldProperties.get());
            if(world.isPresent()) {
                world.get().getProperties().setDifficulty(difficulty);
                GWorld w = new GWorld(
                name, world.get().getUniqueId().toString(),"Vous etes sur " + name,
                "&c[" + name + "] ", Difficulties.EASY, gamemode, true, true, 
                true, world.get().getSpawnLocation(), 0, 2);
                addWorld(name, w);
                WorldManager.save(w);
                sender.sendMessage(WORLD_CREATED(player,name));

            } else {
                sender.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world could not be created."));
            }
        } else {
            sender.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world properties could not be created."));
        }
        return CommandResult.success();
    }
}