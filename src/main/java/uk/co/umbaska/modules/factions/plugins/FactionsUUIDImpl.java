package uk.co.umbaska.modules.factions.plugins;

import ch.njol.yggdrasil.Fields;
import com.massivecraft.factions.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.IFactionsPlugin;
import uk.co.umbaska.modules.factions.skript.types.claim.Claim;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;

import javax.annotation.Nullable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author Andrew Tran
 */
public class FactionsUUIDImpl implements IFactionsPlugin {
    Factions factions;
    FPlayers fPlayers;
    Board board;
    @Override
    public boolean canBeUsed() {
        //FactionsUUID version always contains a `U`
        return FactionsModule.getInstance().classExists("com.massivecraft.factions.P")
                && Bukkit.getPluginManager().getPlugin("Factions").getDescription().getVersion().contains("U");
    }

    @Override
    public void initialize() {
        factions = Factions.getInstance();
        fPlayers = FPlayers.getInstance();
        board = Board.getInstance();
    }

    @Override
    public Faction getAtLocation(Location location) {
        return getUmbaskaFaction(board.getFactionAt(getFLocation(location)));
    }

    public FLocation getFLocation(Location location){
        return new FLocation(location);
    }

    @Override
    public Faction getByName(String name) {
        return getUmbaskaFaction(factions.getByTag(name));
    }

    @Override
    public Faction getFaction(Player player) {
        FPlayer fPlayer = getFPlayer(player);

        com.massivecraft.factions.Faction faction = fPlayer.getFaction();

        return getUmbaskaFaction(faction);
    }

    public Claim chunkToClaim(Chunk chunk){
        return new FactionsUUIDClaim(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
    }

    public Claim[] convertFLocationsToClaims(FLocation... fLocations){
        ArrayList<Claim> claims = new ArrayList<>();
        for (FLocation fLocation : fLocations){
            claims.add(new FactionsUUIDClaim(fLocation));
        }
        return claims.toArray(new Claim[claims.size()]);
    }

    @Override
    public Claim[] getClaims(Faction faction) {
        Set<FLocation> claims = board.getAllClaims(((FactionsUUIDFaction) faction).getFaction());
        return convertFLocationsToClaims(claims);
    }

    private Claim[] convertFLocationsToClaims(Set<FLocation> claims) {
        return convertFLocationsToClaims(claims.toArray(new FLocation[claims.size()]));
    }

    public static class FactionsUUIDClaim extends Claim{
        FLocation fLocation;
        Chunk chunk;
        public FactionsUUIDClaim(FLocation fLocation){
            this.fLocation = fLocation;
            this.chunk = new Location(fLocation.getWorld(), fLocation.getX(), 0, fLocation.getZ()).getChunk();
        }

        public FLocation getFLocation() {
            return fLocation;
        }

        @Override
        public Chunk getChunk() {
            return chunk;
        }

        @Override
        public Fields serialize() {
            Fields f = new Fields();

            f.putObject("world", chunk.getWorld().getName());
            f.putPrimitive("X", chunk.getX());
            f.putPrimitive("Z", chunk.getZ());
            return f;
        }
    }

    @Override
    public Claim deserializeClaim(Fields f) throws StreamCorruptedException {
        World w = Bukkit.getWorld((String) f.getObject("world"));
        Integer x = (Integer) f.getObject("X");
        Integer z = (Integer) f.getObject("Z");
        return chunkToClaim(w.getChunkAt(x, z));
    }

    @Override
    public void claim(Faction faction, Location location) {
        board.setFactionAt(((FactionsUUIDFaction) faction).getFaction(), getFLocation(location));
    }

    @Override
    public void removeClaim(Location location) {
        board.removeAt(getFLocation(location));
    }

    @Nullable
    public Faction getUmbaskaFaction(com.massivecraft.factions.Faction faction){
        if (faction == null || faction.isWilderness()){
            return null;
        }
        return new FactionsUUIDFaction(faction);
    }

    public static class FactionsUUIDFaction extends Faction{

        com.massivecraft.factions.Faction faction;

        public FactionsUUIDFaction(com.massivecraft.factions.Faction faction) {
            super(faction.getTag());
            this.faction = faction;
        }

        public com.massivecraft.factions.Faction getFaction() {
            return faction;
        }

        @Override
        public Fields serialize() {
            Fields f = new Fields();
            f.putObject("id", faction.getId());
            return f;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    @Override
    public Faction deserializeFaction(Fields f) throws StreamCorruptedException {
        if (f == null){
            return null;
        }
        if (!f.contains("id")){
            throw new StreamCorruptedException();
        }
        return getUmbaskaFaction(factions.getFactionById((String) f.getObject("id")));
    }

    public FPlayer getFPlayer(Player player){
        return fPlayers.getByPlayer(player);
    }

    public Factions getFactionsInstance() {
        return factions;
    }

    public FPlayers getFPlayersInstance() {
        return fPlayers;
    }
}
