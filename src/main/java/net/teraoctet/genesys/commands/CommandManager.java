package net.teraoctet.genesys.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {
        
        public CommandSpec CommandKill = CommandSpec.builder()
                .description(Text.of("/kill"))
                .permission("genesys.kill")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandKill())
                .build();
        
        public CommandSpec CommandSun = CommandSpec.builder()
                .description(Text.of("/sun"))
                .permission("genesys.weather.sun")
                .executor(new CommandSun())
                .build();

        public CommandSpec CommandRain = CommandSpec.builder()
                .description(Text.of("Commande rain pluie"))
                .permission("genesys.weather.rain")
                .executor(new CommandRain())
                .build();
        
        public CommandSpec CommandStorm = CommandSpec.builder()
                .description(Text.of("Commande storm orage"))
                .permission("genesys.weather.storm")
                .executor(new CommandStorm())
                .build();
        
        public CommandSpec CommandDay = CommandSpec.builder()
                .description(Text.of("Commande day jour"))
                .permission("genesys.time.day")
                .executor(new CommandDay())
                .build();
        
        public CommandSpec CommandNight = CommandSpec.builder()
                .description(Text.of("Commande night nuit"))
                .permission("genesys.time.nuit")
                .executor(new CommandNight())
                .build();
                
        public CommandSpec CommandParcelCreate = CommandSpec.builder() 
                .description(Text.of("/parcel create <name> [strict]")) 
                .permission("genesys.parcel.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("strict")))))
                .executor(new CommandParcelCreate()) 
                .build(); 
        
        public CommandSpec CommandParcelCreateOK = CommandSpec.builder() 
                .description(Text.of("use /parcel create <name> [strict]")) 
                .permission("genesys.parcel.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.bool(Text.of("strict")))))
                .executor(new CommandParcelCreateOK()) 
                .build(); 
        
        public CommandSpec CommandParcelFlag = CommandSpec.builder()
                .description(Text.of("/parcel flag <flag> <0|1> [name]")) 
                .permission("genesys.parcel.flag") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("flag"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelFlag()) 
                .build(); 
        
        public CommandSpec CommandParcelFlaglist = CommandSpec.builder()
                .description(Text.of("/parcel flaglist [name]")) 
                .permission("genesys.parcel.flag")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelFlaglist()) 
                .build(); 
        
        public CommandSpec CommandParcelRemove = CommandSpec.builder()
                .description(Text.of("/parcel remove [name]")) 
                .permission("genesys.parcel.remove")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelRemove()) 
                .build(); 
        
        public CommandSpec CommandParcelSale = CommandSpec.builder()
                .description(Text.of("/parcel sale <price> [name]")) 
                .permission("genesys.parcel.sale") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("price"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelSale()) 
                .build(); 
        
        public CommandSpec CommandParcelAddplayer = CommandSpec.builder()
                .description(Text.of("/parcel addplayer <player> [name]")) 
                .permission("genesys.parcel.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelAddplayer()) 
                .build();
        
        public CommandSpec CommandParcelRemoveplayer = CommandSpec.builder()
                .description(Text.of("/parcel removeplayer <player> [name]")) 
                .permission("genesys.parcel.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelRemoveplayer()) 
                .build(); 
        
        public CommandSpec CommandParcelOwnerset = CommandSpec.builder()
                .description(Text.of("/parcel ownerset <player> [name]")) 
                .permission("genesys.parcel.ownerset") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandParcelOwnerset()) 
                .build(); 
        
        public CommandSpec CommandParcelList = CommandSpec.builder()
                .description(Text.of("/parcel list [player]")) 
                .permission("genesys.parcel.list") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("player")))))
                .executor(new CommandParcelList()) 
                .build(); 
        
        public CommandSpec CommandParcel = CommandSpec.builder()
                .description(Text.of("/parcel")) 
                .permission("genesys.parcel") 
                .child(CommandParcelCreate, "create")
                .child(CommandParcelCreateOK, "createok")
                .child(CommandParcelList, "list")
                .child(CommandParcelFlag, "flag")
                .child(CommandParcelFlaglist, "flaglist")
                .child(CommandParcelRemove, "remove")
                .child(CommandParcelSale, "sale")
                .child(CommandParcelAddplayer, "addplayer")
                .child(CommandParcelRemoveplayer, "removeplayer")
                .child(CommandParcelOwnerset, "ownerset")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arg")))))
                .executor(new CommandParcel())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(Text.of("/fly"))
                .permission("genesys.fly")
                .executor(new CommandFly())
                .build();
        
        public CommandSpec CommandSetHome = CommandSpec.builder()
                .description(Text.of("/sethome [home]"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .permission("genesys.sethome")
                .executor(new CommandSetHome())
                .build();
        
        public CommandSpec CommandHome = CommandSpec.builder()
                .description(Text.of("/home [home]"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .permission("genesys.home")
                .executor(new CommandHome())
                .build();
        
        public CommandSpec CommandBack = CommandSpec.builder()
                .description(Text.of("/back"))
                .permission("genesys.back")
                .executor(new CommandBack())
                .build();
        
        public CommandSpec CommandLevel = CommandSpec.builder()
                .description(Text.of("/level <level> [player]"))
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("level"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("player")))))
                .permission("genesys.admin.level")
                .executor(new CommandLevel())
                .build();
        
        public CommandSpec CommandWorldCreate = CommandSpec.builder()
                .description(Text.of("/worldCreate <name> <environment> <gamemode> <difficulty>"))
                .permission("genesys.admin.world.worldcreate")
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("environment"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("gamemode"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("difficulty")))))
                .executor(new CommandWorldCreate())
                .build();
        
        public CommandSpec CommandWorldTP = CommandSpec.builder()
                .description(Text.of("Commande worldtp"))
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("worldName"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("target")))))
                .permission("genesys.world.worldtp")
                .executor(new CommandWorldTP())
                .build();
        
        public CommandSpec CommandTest = CommandSpec.builder()
                .description(Text.of("/test [arg0] [args1]"))
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("arg0"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("arg1")))))
                .permission("genesys.admin.test")
                .executor(new CommandTest())
                .build();

        
        public CommandSpec CommandRocket = CommandSpec.builder() 
                .description(Text.of("Rocket Command")) 
                .permission("genesys.rocket.use") 
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags() 
                        .flag("-hard", "h") 
                        .buildWith(GenericArguments.firstParsing(GenericArguments.optional(GenericArguments.player(Text.of("target"))), 
                    GenericArguments.optional(GenericArguments.string(Text.of("targets"))))))) 
                .executor(new CommandRocket()) 
                .build();
       
}
