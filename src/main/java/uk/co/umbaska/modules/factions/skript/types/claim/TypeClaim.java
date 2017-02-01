package uk.co.umbaska.modules.factions.skript.types.claim;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.YggdrasilSerializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.yggdrasil.Fields;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.UmbaskaParser;
import uk.co.umbaska.registration.UmbaskaType;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
public class TypeClaim extends UmbaskaType<Claim>{
    public ClassInfo<Claim> getClassInfo() {
        return new ClassInfo<>(Claim.class, "claim")
                .name("claim").parser(new Parser<Claim>() {
                    @Override
                    public Claim parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(Claim claim, int i) {
                        return String.format(":CLAIM: X: %s Z: %s", claim.getChunk().getX(), claim.getChunk().getZ());
                    }

                    @Override
                    public String toVariableNameString(Claim claim) {
                        return toString(claim, 0);
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ":CLAIM: X: [+-]?((\\d+(\\.\\d*)?)|(\\.\\d+)) Z: [+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))";
                    }
                }).serializer(new YggdrasilSerializer<Claim>(){
                    @Override
                    public Fields serialize(Claim o) throws NotSerializableException {
                        return o.serialize();
                    }

                    @Override
                    public void deserialize(Claim o, Fields f) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    public boolean canBeInstantiated() {
                        return false;
                    }

                    @Override
                    protected Claim deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        return FactionsModule.getInstance().getFactionsImplementation().deserializeClaim(fields);
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }
                });
    }
}
