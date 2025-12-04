package mindustry.game;

import arc.struct.*;
import mindustry.type.*;
import mindustry.world.*;

/**
 * Statistics tracked per planet.
 * Tracks player activity on individual planets including deaths, builds, and other metrics.
 */
public class PlanetStats{
    /** Player unit deaths on this planet. */
    public int playerDeaths;
    /** Enemy (red team) units destroyed on this planet. */
    public int enemyUnitsDestroyed;
    /** Total waves lasted on this planet. */
    public int wavesLasted;
    /** Friendly buildings fully built on this planet. */
    public int buildingsBuilt;
    /** Friendly buildings fully deconstructed on this planet. */
    public int buildingsDeconstructed;
    /** Friendly buildings destroyed on this planet. */
    public int buildingsDestroyed;
    /** Total units created by any means on this planet. */
    public int unitsCreated;
    /** Record of blocks that have been placed by count on this planet. */
    public ObjectIntMap<Block> placedBlockCount = new ObjectIntMap<>();
    /** Total time played on this planet in milliseconds. */
    public long timePlayed;
    /** Total distance walked by player units on this planet. */
    public float distanceWalked;
    
    /** Resets all statistics to zero. */
    public void reset(){
        playerDeaths = 0;
        enemyUnitsDestroyed = 0;
        wavesLasted = 0;
        buildingsBuilt = 0;
        buildingsDeconstructed = 0;
        buildingsDestroyed = 0;
        unitsCreated = 0;
        placedBlockCount.clear();
        timePlayed = 0;
        distanceWalked = 0;
    }
    
    /** Adds stats from another PlanetStats object to this one. */
    public void add(PlanetStats other){
        if(other == null) return;
        
        playerDeaths += other.playerDeaths;
        enemyUnitsDestroyed += other.enemyUnitsDestroyed;
        wavesLasted += other.wavesLasted;
        buildingsBuilt += other.buildingsBuilt;
        buildingsDeconstructed += other.buildingsDeconstructed;
        buildingsDestroyed += other.buildingsDestroyed;
        unitsCreated += other.unitsCreated;
        timePlayed += other.timePlayed;
        distanceWalked += other.distanceWalked;
        
        for(Block block : other.placedBlockCount.keys()){
            int count = other.placedBlockCount.get(block, 0);
            placedBlockCount.increment(block, 0, count);
        }
    }
}

