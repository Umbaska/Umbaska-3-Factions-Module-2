package uk.co.umbaska.modules.factions.skript.types.claim;

import ch.njol.yggdrasil.Fields;
import ch.njol.yggdrasil.YggdrasilSerializable;
import org.bukkit.Chunk;

/**
 * @author Andrew Tran
 */
public abstract class Claim implements YggdrasilSerializable {
    public abstract Chunk getChunk();
    public abstract Fields serialize();
}
