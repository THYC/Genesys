package net.teraoctet.genesys;

import net.teraoctet.genesys.player.PlayerListener;
import com.google.inject.Inject;

import java.io.File;
import net.teraoctet.genesys.plot.PlotListener;
import net.teraoctet.genesys.plot.PlotManager;
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
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = "net.teraoctet.genesys", name = "genesys", version = "1.0", description = "manage your server", authors = {"THYC"})
public class Genesys {
    
    @Inject private Logger logger;
    private static PluginContainer plugin;
    public static PlotManager plotManager = new PlotManager();
    public static PortalManager portalManager = new PortalManager();
    public Logger getLogger(){return logger;}  
    public static Game thisGame() {return getGame();}
                
    @Listener
    public void onServerInit(GameInitializationEvent event) {
	        
        File folder = new File("config/genesys");
    	if(!folder.exists()) folder.mkdir();
    	GConfig.setup();
    	GData.setup();
    	GData.load();
        MessageManager.init();

        getGame().getEventManager().registerListeners(this, new PlotListener());
        getGame().getEventManager().registerListeners(this, new PortalListener());
        getGame().getEventManager().registerListeners(this, new PlayerListener());
        getGame().getEventManager().registerListeners(this, new WorldListener());
         
	getGame().getCommandManager().register(this, new CommandManager().CommandKill, "kill", "tue");
	getGame().getCommandManager().register(this, new CommandManager().CommandSun, "sun", "soleil");
	getGame().getCommandManager().register(this, new CommandManager().CommandRain, "rain", "pluie");
	getGame().getCommandManager().register(this, new CommandManager().CommandStorm, "storm", "orage");
	getGame().getCommandManager().register(this, new CommandManager().CommandDay, "day", "timeday", "jour");
	getGame().getCommandManager().register(this, new CommandManager().CommandNight, "night", "timenight", "nuit");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlot, "plot", "parcel", "parcelle", "p");
	getGame().getCommandManager().register(this, new CommandManager().CommandFly, "fly", "vole");
	getGame().getCommandManager().register(this, new CommandManager().CommandSetHome, "sethome", "homeset");
	getGame().getCommandManager().register(this, new CommandManager().CommandHome, "home");
        getGame().getCommandManager().register(this, new CommandManager().CommandDelhome, "delhome");
	getGame().getCommandManager().register(this, new CommandManager().CommandBack, "back", "gsback", "reviens");
	getGame().getCommandManager().register(this, new CommandManager().CommandLevel, "level");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorldCreate, "worldcreate", "createworld");
	getGame().getCommandManager().register(this, new CommandManager().CommandWorldTP, "worldtp", "tpworld");
        getGame().getCommandManager().register(this, new CommandManager().CommandClearinventory, "clearinventory", "ci", "clear");
        getGame().getCommandManager().register(this, new CommandManager().CommandInvsee, "invsee", "is");
	getGame().getCommandManager().register(this, new CommandManager().CommandTest, "test");
        getGame().getCommandManager().register(this, new CommandManager().CommandRocket, "rocket");

        getLogger().info("-----------------------------"); 
	getLogger().info("Genesys loaded!"); 
        getLogger().info("-----------------------------"); 
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