package net.teraoctet.genesys.world;

import java.util.ArrayList;
import java.util.List;
import net.teraoctet.genesys.utils.GData;
import static org.spongepowered.api.Sponge.getGame;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulty;

public class GWorld {

    private final String name;
    private final String uuid;
    private String message;
    private String prefix;
    private Difficulty difficulty;
    private GameMode gamemode;
    private boolean monsters;
    private boolean animals;
    private boolean pvp;
    private Location<World> spawn;
    private double border;
    private double borderdamage;

    public GWorld(String name, String uuid, String message, String prefix, Difficulty difficulty, GameMode gamemode, boolean monsters, boolean animals, boolean pvp, Location<World> spawn, double border, double borderdamage) {
        this.name = name;
        this.uuid = uuid;
        this.message = message;
        this.prefix = prefix;
        this.difficulty = difficulty;
        this.gamemode = gamemode;
        this.monsters = monsters;
        this.animals = animals;
        this.pvp = pvp;
        this.spawn = spawn;
        this.border = border;
        this.borderdamage = borderdamage;
    }

    public void update() {
        GData.addWorld(name, this);
        WorldManager.save(this);
        if(!getGame().getServer().getWorld(name).isPresent()) return;
        World world = getGame().getServer().getWorld(name).get();
        world.getProperties().setDifficulty(difficulty);
        world.getProperties().setGameMode(gamemode);
        world.getProperties().setSpawnPosition(spawn.getBlockPosition());
    }
    
    public void setMessage(String message) { this.message = message; update(); }
    public void setPefix(String prefix) { this.prefix = prefix; update(); }
    public void setSpawn(Location<World> spawn) { this.spawn = spawn; update(); }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; update(); }
    public void setGamemode(GameMode gamemode) { this.gamemode = gamemode; update(); }
    public void setBorder(double border) { this.border = border; update(); }
    public void setBorderDamage(double damage) { this.borderdamage = damage; }
    public void allowMonster(boolean state) { this.monsters = state; update(); }
    public void allowAnimal(boolean state) { this.animals = state; update(); }
    public void allowPVP(boolean state) { this.pvp = state; update(); }
    
    public String getName() { return name; }
    public String getUUID() { return uuid; }
    public String getMessage() { return message; }
    public String getPrefix() { return prefix; }
    public Location<World> getSpawn() { return spawn; }
    public Difficulty getDifficulty() { return difficulty; }
    public GameMode getGamemode() { return gamemode; }
    public boolean getMonster() { return monsters; }
    public boolean getAnimal() { return animals; }
    public boolean getPVP() { return pvp; }
    public double getBorder() { return border; }
    public double getBorderDamage() { return borderdamage; }

    public boolean hasBorder() {
            return border > 0;
    }
    public void teleport(Player player) {
            player.setLocation(spawn);
    }
    public List<Player> getPlayers() { 
            List<Player> players = new ArrayList<>();
            for(Player player : getGame().getServer().getOnlinePlayers()) {
                if(!player.getWorld().getName().equalsIgnoreCase(name)) continue;
                players.add(player);
            }
            return players;
    }
}
