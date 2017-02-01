package uk.co.umbaska.modules.factions;

import uk.co.umbaska.modules.factions.plugins.FactionsUUIDImpl;

/**
 * @author Andrew Tran
 */
public enum FactionPlugin {
    ORIGINAL(null),
    FACTIONS_ONE(null),
    FACTIONS_UUID(FactionsUUIDImpl.class),
    KINGDOMS(null);
    Class<? extends IFactionsPlugin> pluginImpl;
    FactionPlugin(Class<? extends IFactionsPlugin> pluginImpl){
        this.pluginImpl = pluginImpl;
    }

    public Class<? extends IFactionsPlugin> getPluginImplementation() {
        return pluginImpl;
    }
}
