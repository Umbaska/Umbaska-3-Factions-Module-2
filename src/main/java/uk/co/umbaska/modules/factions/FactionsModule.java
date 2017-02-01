package uk.co.umbaska.modules.factions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import uk.co.umbaska.Umbaska;
import uk.co.umbaska.modules.UmbaskaModule;
import uk.co.umbaska.modules.factions.skript.types.claim.Claim;
import uk.co.umbaska.registration.Register;
import uk.co.umbaska.registration.SkriptElementType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrew Tran
 */
public class FactionsModule extends UmbaskaModule{
    private static FactionsModule instance;

    IFactionsPlugin factionsImpl;
    FactionPlugin factionPlugin;
    FactionsModuleLogger log;

    public static FactionsModule getInstance(){
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;

        /*
         *
         * TEMPORARILY DISREGARD THIS BAD CODE, AS A SOLUTION IS BEING WORKED ON IN UMBASKA ITSELF
         *
         */

        log = new FactionsModuleLogger();

        registerSyntaxes(new SkriptElementType[]{SkriptElementType.TYPE});
        if(!initializeImpl()){
            Umbaska.info("Disabling Factions Module");
            disable();
        }
        Bukkit.getScheduler().runTaskLater(Umbaska.get(), new Runnable() {
            @Override
            public void run() {
                Umbaska.info(ChatColor.GREEN + "Factions Module by xXAndrew28Xx is finalizing loading!");
                ArrayList<SkriptElementType> skriptElementTypes = new ArrayList<>(Arrays.asList(SkriptElementType.values()));
                skriptElementTypes.remove(SkriptElementType.TYPE);
                registerSyntaxes(skriptElementTypes.toArray(new SkriptElementType[skriptElementTypes.size()]));
                Register.setLateRegister(true);
                Converters.registerConverter(Claim.class, Chunk.class, new Converter<Claim, Chunk>() {
                    @Override
                    public Chunk convert(Claim claim) {
                        if (claim == null){
                            return null;
                        }
                        return claim.getChunk();
                    }
                });
                Register.setLateRegister(false);
                reloadScripts();
            }
        }, 0);
    }

    public FactionsModuleLogger log(){
        return log;
    }

    public boolean initializeImpl(){
        for (FactionPlugin factionPlugin : FactionPlugin.values()){
            IFactionsPlugin factionPluginObject = null;
            if (factionPlugin.getPluginImplementation() != null){
                try {
                    factionPluginObject = factionPlugin.getPluginImplementation().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("Failed to instantiate the class " + factionPlugin.getPluginImplementation().getCanonicalName());
                    e.printStackTrace();
                }
            }
            log.info("Detecting if the implementation " + factionPlugin.name() + " can be used.");
            if (factionPluginObject != null && factionPluginObject.canBeUsed()){
                factionsImpl = factionPluginObject;
                log.important(ChatColor.GREEN + "Implementation " + factionPlugin.name() + " is being used! Initializing it...");
                factionsImpl.initialize();
                log().important("Initialized " + factionPlugin.name() + " Implementation");
                this.factionPlugin = factionPlugin;
                return true;
            }else{
                log.info("Implementation " + factionPlugin + " can not be used, trying next one if available.");
            }
        }
        log.error("Could not find a Factions plugins to HOOK INTO, **if you have one reply to the Module Thread requesting for me to add support for it.**");
        return false;
    }

    public boolean classExists(String clazz){
        try{
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public class FactionsModuleLogger{
        public String color(String s){
            return ChatColor.translateAlternateColorCodes('&', s);
        }
        public String[] color(String... ss){
            int i = 0;
            for (String s : ss){
                ss[i] = color(s);
                i++;
            }
            return ss;
        }

        private void log(String string){
            string = color(string);
            Bukkit.getConsoleSender().sendMessage("[" + ChatColor.AQUA + "Factions Module" +ChatColor.RESET + "] " + string);
        }
        public void info(String string){
            log(string);
        }
        public void important(String string){
            log("&2" + string);
        }
        public void warn(String string){
            log("&e" + string);
        }
        public void error(String string){
            log("&4" + string);
        }
        public void error(String string, Throwable e){
            error(string);
            e.printStackTrace();
        }
    }

    public FactionPlugin getFactionPlugin() {
        return factionPlugin;
    }

    public IFactionsPlugin getFactionsImplementation() {
        return factionsImpl;
    }
}
