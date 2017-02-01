package uk.co.umbaska.modules.factions;

import ch.njol.yggdrasil.Fields;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import uk.co.umbaska.modules.factions.skript.types.claim.Claim;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public interface IFactionsPlugin {
    public boolean canBeUsed();
    public void initialize();

    public Faction getAtLocation(Location location);
    public Faction getByName(String name);
    public Faction getFaction(Player player);

    public Claim[] getClaims(Faction faction);
    public void claim(Faction faction, Location location);
    public void removeClaim(Location location);

    public Claim deserializeClaim(Fields f) throws StreamCorruptedException;
    public Faction deserializeFaction(Fields f) throws StreamCorruptedException;
}
