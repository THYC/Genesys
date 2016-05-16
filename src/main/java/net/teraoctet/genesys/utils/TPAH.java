package net.teraoctet.genesys.utils;

import org.spongepowered.api.entity.living.player.Player;

public class TPAH {
    private final Player fromPlayer;
    private final Player toPlayer;
    private final String Type;
    
    public TPAH(Player fromPlayer, Player toPlayer, String Type){
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        this.Type = Type;
    }
    
    public Player getFromPlayer(){
        return fromPlayer;
    }
    
    public Player getToPlayer(){
        return toPlayer;
    }
    
    public String getType(){
        return Type;
    }
}
