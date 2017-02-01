package uk.co.umbaska.modules.factions.skript.types.factions;

import ch.njol.yggdrasil.Fields;
import ch.njol.yggdrasil.YggdrasilSerializable;

import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public abstract class Faction{
    String name;
    public Faction(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public abstract Fields serialize();
}
