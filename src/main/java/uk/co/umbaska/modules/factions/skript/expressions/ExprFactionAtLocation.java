package uk.co.umbaska.modules.factions.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.IFactionsPlugin;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.SimpleUmbaskaExpression;
import uk.co.umbaska.registration.UmbaskaPropertyExpression;
import uk.co.umbaska.registration.annotations.ExpressionsType;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.Syntaxes;

import javax.annotation.Nullable;

/**
 * @author Andrew Tran
 */
@Name("Faction at Location")
@Syntaxes("[the] (claim|faction|kingdom|guild) at %location%")
public class ExprFactionAtLocation extends SimpleUmbaskaExpression<Faction>{
    Expression<Location> location;

    @Override
    public Class<? extends Faction> getReturnType() {
        return Faction.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        location = (Expression<Location>) expressions[0];
        return true;
    }

    @Override
    @Nullable
    protected Faction[] get(Event event) {
        Location location = this.location.getSingle(event);
        if (location == null){
            return null;
        }
        Faction faction = FactionsModule.getInstance().getFactionsImplementation().getAtLocation(location);
        if (faction == null){
            return null;
        }
        return new Faction[]{faction};
    }
    /*
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        switch(mode){
            case DELETE:
            case REMOVE:
            case REMOVE_ALL:
            case RESET:
            case SET:
                return new Class[]{Faction.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        switch(mode){
            case DELETE:
            case REMOVE:
            case REMOVE_ALL:
            case RESET:
                FactionsModule.getInstance().getFactionsImplementation().removeClaim(location.getSingle(e));
                break;
            case SET:
                if (!(delta[0] instanceof Faction)){
                    return;
                }
                Faction faction = (Faction) delta[0];
                FactionsModule.getInstance().getFactionsImplementation().claim(faction, location.getSingle(e));
                break;
        }
    }*/
}
