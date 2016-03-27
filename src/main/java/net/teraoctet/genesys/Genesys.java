package net.teraoctet.genesys;

import net.teraoctet.genesys.player.PlayerListener;
import com.google.inject.Inject;

import java.io.File;
import net.teraoctet.genesys.commands.CommandRocket;
import net.teraoctet.genesys.parcel.ParcelListener;
import net.teraoctet.genesys.parcel.ParcelManager;
import net.teraoctet.genesys.portal.PortalListener;
import net.teraoctet.genesys.portal.PortalManager;
import net.teraoctet.genesys.utils.GConfig;
import net.teraoctet.genesys.utils.GData;
import net.teraoctet.genesys.utils.MessageManager;
import net.teraoctet.genesys.world.WorldListener;
import net.teraoctet.genesys.world.WorldManager;
import net.teraoctet.genesys.commands.CommandManager;

import org.slf4j.Logger;
import org.spongepowered.api.Game;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

@Plugin(id = "net.teraoctet.genesys", name = "genesys", version = "1.0", description = "manage your server", authors = {"THYC"})
public class Genesys {
    
    @Inject private Logger logger;
    private static PluginContainer plugin;
    public static ParcelManager parcelManager = new ParcelManager();
    public static PortalManager portalManager = new PortalManager();
    public Logger getLogger(){return logger;}  
    //public static PluginContainer getPlugin() {return plugin;} 
    public static Game thisGame() {return getGame();}
                
    @Listener
    public void onServerInit(GameInitializationEvent event) {
        
        //plugin = getGame().getPluginManager().getPlugin("genesys").get();
	        
        File folder = new File("config/genesys");
    	if(!folder.exists()) folder.mkdir();
    	GConfig.setup();
    	GData.setup();
    	GData.load();
        MessageManager.init();

        getGame().getEventManager().registerListeners(this, new ParcelListener());
        getGame().getEventManager().registerListeners(this, new PortalListener());
        getGame().getEventManager().registerListeners(this, new PlayerListener());
        getGame().getEventManager().registerListeners(this, new WorldListener());
         
	getGame().getCommandManager().register(this, new CommandManager().CommandKill, "kill", "tue");
	getGame().getCommandManager().register(this, new CommandManager().CommandSun, "sun", "soleil");
	getGame().getCommandManager().register(this, new CommandManager().CommandRain, "rain", "pluie");
	getGame().getCommandManager().register(this, new CommandManager().CommandStorm, "storm", "orage");
	getGame().getCommandManager().register(this, new CommandManager().CommandDay, "day", "timeday", "jour");
	getGame().getCommandManager().register(this, new CommandManager().CommandNight, "night", "timenight", "nuit");
        getGame().getCommandManager().register(this, new CommandManager().CommandParcel, "parcel", "parcelle", "p", "pl");
	getGame().getCommandManager().register(this, new CommandManager().CommandFly, "fly", "vole");
	getGame().getCommandManager().register(this, new CommandManager().CommandSetHome, "sethome", "homeset");
	getGame().getCommandManager().register(this, new CommandManager().CommandHome, "home");
	getGame().getCommandManager().register(this, new CommandManager().CommandBack, "back", "gsback", "reviens");
	getGame().getCommandManager().register(this, new CommandManager().CommandLevel, "level");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorldCreate, "worldcreate", "createworld");
	getGame().getCommandManager().register(this, new CommandManager().CommandWorldTP, "worldtp", "tpworld");
	getGame().getCommandManager().register(this, new CommandManager().CommandTest, "test");
        
        CommandSpec CommandRocket = CommandSpec.builder() 
                .description(Text.of("Rocket Command")) 
                //.permission("essentialcmds.rocket.use") 
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags() 
                        .flag("-hard", "h") 
                        .buildWith(GenericArguments.firstParsing(GenericArguments.optional(GenericArguments.player(Text.of("target"))), 
                    GenericArguments.optional(GenericArguments.string(Text.of("targets"))))))) 
                .executor(new CommandRocket()) 
                .build();
        getGame().getCommandManager().register(this, CommandRocket, "rocket");
       /* 
        
        
        game.getCommandDispatcher().register(this, new CommandPortal(game), "portal","portail");
        game.getCommandDispatcher().register(this, new CommandHead(), "head","skull","tete");
        */
        
        getLogger().info("-----------------------------"); 
 	getLogger().info("Genesys was made by thyc82!"); 
 	getLogger().info("-----------------------------"); 
	getLogger().info("Genesys loaded!"); 
    }
        
    @Listener
    public void onDisable(GameStoppingServerEvent event) {
    	GData.commit();
    }

    @Listener
    public void onServerLoadComplete(GameLoadCompleteEvent event)
    {
        getLogger().info("Genesys: charging is complete");
    }  
    
    @Listener
    public void onServerStarted(GameStartedServerEvent event)
    {
        WorldManager.init();
    }   
}