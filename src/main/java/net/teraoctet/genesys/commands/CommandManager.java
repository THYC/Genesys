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
                
        public CommandSpec CommandPlotCreate = CommandSpec.builder() 
                .description(Text.of("/plot create <name> [strict]")) 
                .permission("genesys.plot.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("strict")))))
                .executor(new CommandPlotCreate()) 
                .build(); 
        
        public CommandSpec CommandPlotCreateOK = CommandSpec.builder() 
                .description(Text.of("use /plot create <name> [strict]")) 
                .permission("genesys.plot.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.bool(Text.of("strict")))))
                .executor(new CommandPlotCreateOK()) 
                .build(); 
        
        public CommandSpec CommandPlotFlag = CommandSpec.builder()
                .description(Text.of("/plot flag <flag> <0|1> [name]")) 
                .permission("genesys.plot.flag") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("flag"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlag()) 
                .build(); 
        
        public CommandSpec CommandPlotFlaglist = CommandSpec.builder()
                .description(Text.of("/plot flaglist [name]")) 
                .permission("genesys.plot.flag")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlaglist()) 
                .build(); 
        
        public CommandSpec CommandPlotRemove = CommandSpec.builder()
                .description(Text.of("/plot remove [name]")) 
                .permission("genesys.plot.remove")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemove()) 
                .build(); 
        
        public CommandSpec CommandPlotSale = CommandSpec.builder()
                .description(Text.of("/plot sale <price> [name]")) 
                .permission("genesys.plot.sale") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("price"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotSale()) 
                .build(); 
        
        public CommandSpec CommandPlotAddplayer = CommandSpec.builder()
                .description(Text.of("/plot addplayer <player> [name]")) 
                .permission("genesys.plot.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotAddplayer()) 
                .build();
        
        public CommandSpec CommandPlotRemoveplayer = CommandSpec.builder()
                .description(Text.of("/plot removeplayer <player> [name]")) 
                .permission("genesys.plot.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemoveplayer()) 
                .build(); 
        
        public CommandSpec CommandPlotOwnerset = CommandSpec.builder()
                .description(Text.of("/plot ownerset <player> [name]")) 
                .permission("genesys.plot.ownerset") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotOwnerset()) 
                .build(); 
        
        public CommandSpec CommandPlotList = CommandSpec.builder()
                .description(Text.of("/plot list [player]")) 
                .permission("genesys.plot.list") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("player")))))
                .executor(new CommandPlotList()) 
                .build(); 
        
        public CommandSpec CommandPlotExtand = CommandSpec.builder()
                .description(Text.of("/plot extand <value>")) 
                .permission("genesys.plot.extand") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("point"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("axe")))))
                .executor(new CommandPlotExtand()) 
                .build(); 
        
        public CommandSpec CommandPlot = CommandSpec.builder()
                .description(Text.of("/plot")) 
                .permission("genesys.plot") 
                .child(CommandPlotCreate, "create")
                .child(CommandPlotCreateOK, "createok")
                .child(CommandPlotExtand, "extand")
                .child(CommandPlotList, "list")
                .child(CommandPlotFlag, "flag")
                .child(CommandPlotFlaglist, "flaglist")
                .child(CommandPlotRemove, "remove")
                .child(CommandPlotSale, "sale")
                .child(CommandPlotAddplayer, "addplayer")
                .child(CommandPlotRemoveplayer, "removeplayer")
                .child(CommandPlotOwnerset, "ownerset")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arg")))))
                .executor(new CommandPlot())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(Text.of("/fly"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
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
        
        public CommandSpec CommandDelhome = CommandSpec.builder()
                .description(Text.of("/delhome [home]"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .permission("genesys.delhome")
                .executor(new CommandDelhome())
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
        
        public CommandSpec CommandClearinventory = CommandSpec.builder()
                .description(Text.of("/clearinventory [player]"))
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.player(Text.of("player")))))
                .permission("genesys.clearinventory")
                .executor(new CommandClearinventory())
                .build();
        
        public CommandSpec CommandInvsee = CommandSpec.builder()
                .description(Text.of("/invsee [player]"))
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))))
                .permission("genesys.invsee")
                .executor(new CommandInvsee())
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
