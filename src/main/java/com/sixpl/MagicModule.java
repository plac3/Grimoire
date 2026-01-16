package com.sixpl;

import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.SystemType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.component.Orbit;
import com.sixpl.system.OrbitSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class MagicModule extends JavaPlugin {
    private static MagicModule instance;
    private ComponentType<EntityStore, Orbit> orbitComponent;

    public static MagicModule get() { return instance; }

    public MagicModule(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;

    }

    protected void setup(){
        ComponentRegistryProxy<EntityStore> registryProxy = this.getEntityStoreRegistry();
        this.orbitComponent = registryProxy.registerComponent(Orbit.class, "OrbitMagic", Orbit.CODEC);
        registryProxy.registerSystem(new OrbitSystem());
    }

    public ComponentType<EntityStore, Orbit> getOrbitComponentType() {
        return this.orbitComponent;
    }

    public void tick(float deltaTime){

    }
}
