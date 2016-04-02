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
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
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
        
        public CommandSpec CommandPlotMsg = CommandSpec.builder()
                .description(Text.of("/plot msg [message]"))
                .permission("genesys.plot.msg")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("arguments")))
                .executor(new CommandPlotMsg())
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
                .child(CommandPlotMsg, "msg")
                .executor(new CommandPlot())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(Text.of("/fly"))
                .permission("genesys.fly")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandFly())
                .build();
        
        public CommandSpec CommandSetHome = CommandSpec.builder()
                .description(Text.of("/sethome [home]"))
                .permission("genesys.sethome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandSetHome())
                .build();
        
        public CommandSpec CommandHome = CommandSpec.builder()
                .description(Text.of("/home [home]"))
                .permission("genesys.home")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandHome())
                .build();
        
        public CommandSpec CommandDelhome = CommandSpec.builder()
                .description(Text.of("/delhome [home]"))
                .permission("genesys.delhome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandDelhome())
                .build();
        
        public CommandSpec CommandBack = CommandSpec.builder()
                .description(Text.of("/back"))
                .permission("genesys.back")
                .executor(new CommandBack())
                .build();
        
        public CommandSpec CommandLevel = CommandSpec.builder()
                .description(Text.of("/level <level> [player]"))
                .permission("genesys.admin.level")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("level"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("player")))))
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
                .permission("genesys.world.worldtp")
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("worldName"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("target")))))
                .executor(new CommandWorldTP())
                .build();
        
        public CommandSpec CommandClearinventory = CommandSpec.builder()
                .description(Text.of("/clearinventory [player]"))
                .permission("genesys.clearinventory")
                .arguments(GenericArguments.optional(GenericArguments.player(Text.of("player"))))
                .executor(new CommandClearinventory())
                .build();
        
        public CommandSpec CommandInvsee = CommandSpec.builder()
                .description(Text.of("/invsee <player>"))
                .permission("genesys.invsee")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("target"))))
                .executor(new CommandInvsee())
                .build();
        
        public CommandSpec CommandPlayerinfo = CommandSpec.builder()
                .description(Text.of("/playerinfo [player]"))
                .permission("genesys.playerinfo")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
                .executor(new CommandPlayerinfo())
                .build();
        
        public CommandSpec CommandBroadcastmessage = CommandSpec.builder()
                .description(Text.of("/broadcast [hide = 0:1] <message..>"))
                .permission("genesys.broadcastmessage")
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags()
                        .flag("-hide", "h")
                        .buildWith(GenericArguments.remainingJoinedStrings(Text.of("message")))))
                .executor(new CommandBroadcastmessage())
                .build();
         
        public CommandSpec CommandFactionCreate = CommandSpec.builder()
                .description(Text.of("/faction create <name>")) 
                .permission("genesys.faction.create") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionCreate()) 
                .build();
         
        public CommandSpec CommandFactionDelete = CommandSpec.builder()
                .description(Text.of("/faction delete <name>")) 
                .permission("genesys.faction.delete") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionDelete()) 
                .build();
        
        public CommandSpec CommandFactionRename = CommandSpec.builder()
                .description(Text.of("/faction rename <name>")) 
                .permission("genesys.faction.rename") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionRename()) 
                .build();
         
        public CommandSpec CommandFactionList = CommandSpec.builder()
                .description(Text.of("/faction list")) 
                .permission("genesys.faction.list")
                .executor(new CommandFactionList()) 
                .build();
         
        public CommandSpec CommandFactionMemberslist = CommandSpec.builder()
                .description(Text.of("/faction memberslist")) 
                .permission("genesys.faction.memberslist")
                .executor(new CommandFactionMemberslist()) 
                .build();
         
        public CommandSpec CommandFactionAddplayer = CommandSpec.builder()
                .description(Text.of("/faction addplayer <player>")) 
                .permission("genesys.faction.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandFactionAddplayer()) 
                .build();
         
        public CommandSpec CommandFactionRemoveplayer = CommandSpec.builder()
                .description(Text.of("/faction removeplayer <player>")) 
                .permission("genesys.faction.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandFactionRemoveplayer()) 
                .build();
         
        public CommandSpec CommandFactionSetplayergrade = CommandSpec.builder()
                .description(Text.of("/faction setplayergrade <player> <grade>")) 
                .permission("genesys.faction.setplayergrade") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("grade")))))
                .executor(new CommandFactionRemoveplayer()) 
                .build();
        
        public CommandSpec CommandFactionSetowner = CommandSpec.builder()
                .description(Text.of("/faction setowner <player>")) 
                .permission("genesys.faction.setowner") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new CommandFactionSetowner()) 
                .build();
        
        public CommandSpec CommandFactionDeposit = CommandSpec.builder()
                .description(Text.of("/faction depot <montant>")) 
                .permission("genesys.faction.deposit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount"))))
                .executor(new CommandFactionDeposit()) 
                .build();
        
        public CommandSpec CommandFactionWithdrawal = CommandSpec.builder()
                .description(Text.of("/faction retrait <montant>")) 
                .permission("genesys.faction.withdrawal") 
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))))
                .executor(new CommandFactionWithdrawal()) 
                .build();
         
        public CommandSpec CommandFaction = CommandSpec.builder()
                .description(Text.of("Affiche des informations sur votre faction"))
                .permission("genesys.faction")
                .child(CommandFactionCreate, "create")
                .child(CommandFactionDelete, "delete")
                .child(CommandFactionRename, "rename")
                .child(CommandFactionMemberslist, "memberslist")
                .child(CommandFactionList, "list")
                .child(CommandFactionAddplayer, "addplayer")
                .child(CommandFactionRemoveplayer, "removeplayer")
                .child(CommandFactionSetplayergrade, "setplayergrade")
                .child(CommandFactionSetowner, "setowner")
                .child(CommandFactionDeposit, "deposit", "depot")
                .child(CommandFactionDeposit, "withdrawal", "retrait")
                .arguments(GenericArguments.flags().flag("-displayaction", "a").buildWith(GenericArguments.none()))
                .executor(new CommandFaction())
                .build();
        
        public CommandSpec CommandTest = CommandSpec.builder()
                .description(Text.of("/test [arg0] [args1]"))
                .permission("genesys.admin.test")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("arg0"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("arg1")))))
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
        
        public CommandSpec CommandPortalMsg = CommandSpec.builder()
                .description(Text.of("/portal msg [message]"))
                .permission("genesys.admin.portal")
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                        GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandPortalMsg())
                .build();
        
        public CommandSpec CommandPortalTPFrom = CommandSpec.builder() 
                .description(Text.of("/portal TPFrom <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalTPFrom()) 
                .build(); 
        
        public CommandSpec CommandPortalList = CommandSpec.builder() 
                .description(Text.of("/portal list")) 
                .permission("genesys.admin.portal") 
                .executor(new CommandPortalList()) 
                .build(); 
        
        public CommandSpec CommandPortalRemove = CommandSpec.builder() 
                .description(Text.of("/portal remove <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalRemove()) 
                .build(); 
        
        public CommandSpec CommandPortalCreateOK = CommandSpec.builder() 
                .description(Text.of("/portal create <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreateOK()) 
                .build(); 
        
        public CommandSpec CommandPortalCreate = CommandSpec.builder()
                .description(Text.of("/portal create [name]"))
                .permission("genesys.admin.portal")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreate())
                .build();
        
        public CommandSpec CommandPortal = CommandSpec.builder()
                .description(Text.of("/plot")) 
                .permission("genesys.plot") 
                .child(CommandPortalCreate, "create", "add")
                .child(CommandPortalCreateOK, "createok")
                .child(CommandPortalRemove, "remove", "rem", "del")
                .child(CommandPortalList, "list")
                .child(CommandPortalTPFrom, "tpfrom", "tpf")
                .child(CommandPortalMsg, "msg")
                .executor(new CommandPlot())
                .build();
       
}
