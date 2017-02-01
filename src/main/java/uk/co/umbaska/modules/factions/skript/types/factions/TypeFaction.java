package uk.co.umbaska.modules.factions.skript.types.factions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.classes.YggdrasilSerializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.yggdrasil.Fields;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.IFactionsPlugin;
import uk.co.umbaska.registration.UmbaskaParser;
import uk.co.umbaska.registration.UmbaskaType;
import uk.co.umbaska.registration.annotations.Name;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
@Name("Faction")
public class TypeFaction extends UmbaskaType<Faction>{
    @Override
    public ClassInfo<Faction> getClassInfo() {
        return new ClassInfo<>(Faction.class, "umbfaction")
                .name("umbfaction").parser(new UmbaskaParser<Faction>() {
                    @Override
                    public Faction parse(String s, ParseContext parseContext) {
                        return FactionsModule.getInstance().getFactionsImplementation().getByName(s);
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(Faction faction, int i) {
                        return faction.getName();
                    }

                    @Override
                    public String toVariableNameString(Faction faction) {
                        return faction.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".*";
                    }
                }).serializer(new Serializer<Faction>(){
                    @Override
                    public Fields serialize(Faction o) throws NotSerializableException {
                        System.out.println("SERIALIZE: " + o.getName());
                        return o.serialize();
                    }

                    @Override
                    public void deserialize(Faction o, Fields f) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    protected Faction deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        System.out.println("DESERIALIZE: " + fields.size());
                        return FactionsModule.getInstance().getFactionsImplementation().deserializeFaction(fields);
                    }
                });
    }
}
