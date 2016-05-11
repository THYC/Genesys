package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.addWorld;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_CREATED;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_ALREADY_EXIST;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_CREATION_ERROR;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_PROPERTIES_ERROR;
import net.teraoctet.genesys.world.GWorld;
import net.teraoctet.genesys.world.WorldManager;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
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
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if (src instanceof Player && src.hasPermission("genesys.admin.world.worldcreate")){
            Player player = null;
            player = (Player)src;
            if(!ctx.getOne("name").isPresent() || !ctx.getOne("environment").isPresent() || !ctx.getOne("gamemode").isPresent()) {
                src.sendMessage(USAGE("/worldcreate <name> <environnement> <gamemode> <difficulty>"));
                src.sendMessage(MESSAGE("&6<environment> = &7'overworld', 'flat', 'superflat', 'nether' ou 'end'"));
                src.sendMessage(MESSAGE("&6<gamemode> = &7survival, creative, adventure ou spectator"));
                src.sendMessage(MESSAGE("&6<Difficulty> = &7easy, hard, normal, peaceful"));
                return CommandResult.empty();
            }
            String name = ctx.<String> getOne("name").get();
            String environment = ctx.<String> getOne("environment").get();
            String gamemodeI = ctx.<String> getOne("gamemode").get();
            String difficultyI = ctx.<String> getOne("difficulty").get();
            if(getGame().getServer().getWorld(name).isPresent()) {src.sendMessage(WORLD_ALREADY_EXIST()); return CommandResult.success();}
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
                default: src.sendMessage(MESSAGE("&6<environment> = &7'overworld', 'flat', 'superflat', 'nether' ou 'end'"));return CommandResult.success();
            }
            switch (gamemodeI.toLowerCase()){
                case "survival": gamemode = GameModes.SURVIVAL; break;
                case "creative": gamemode = GameModes.CREATIVE; break;
                case "adventure": gamemode = GameModes.ADVENTURE; break;
                case "spectator": gamemode = GameModes.SPECTATOR; break;
                default: src.sendMessage(MESSAGE("&6<gamemode> = &7survival, creative, adventure ou spectator"));return CommandResult.success();
            }
            switch (difficultyI.toLowerCase()){
                case "easy": difficulty = Difficulties.EASY; break;
                case "hard": difficulty = Difficulties.HARD; break;
                case "normal": difficulty = Difficulties.NORMAL; break;
                case "peaceful": difficulty = Difficulties.PEACEFUL; break;
                default: src.sendMessage(MESSAGE("&6<Difficulty> = &7easy, hard, normal, peaceful"));return CommandResult.success();
            }
            src.sendMessage(MESSAGE("&cCr\351ation du monde en cours ..."));
            Optional<WorldCreationSettings> worldSettings = Optional.of(getGame().getRegistry().createBuilder(WorldCreationSettings.Builder.class)
                    .enabled(true)
                    .loadsOnStartup(true)
                    .keepsSpawnLoaded(true)
                    .dimension(dimension)
                    .generator(generator)
                    .gameMode(gamemode)
                    .build());
            Optional<WorldProperties> worldProperties = Optional.of(getGame().getServer().createWorldProperties(worldSettings.get()).get());
            if (worldProperties.isPresent()) {
                Optional<World> world = getGame().getServer().loadWorld(worldProperties.get());
                if(world.isPresent()) {
                    world.get().getProperties().setDifficulty(difficulty);
                    GWorld w = new GWorld(
                            name, world.get().getUniqueId().toString(),"Vous \352tes sur " + name,
                            "&c[" + name + "] ", Difficulties.EASY, gamemode, true, true,
                            true, world.get().getSpawnLocation(), 0, 2);
                    addWorld(name, w);
                    WorldManager.save(w);
                    src.sendMessage(WORLD_CREATED(player,name));
                    return CommandResult.success();
                } else {
                    src.sendMessage(WORLD_CREATION_ERROR());
                }
            } else {
                src.sendMessage(WORLD_PROPERTIES_ERROR());
            }
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
