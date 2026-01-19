package com.sixpl;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.sixpl.commands.SpawnOrbit;
import com.sixpl.component.ArcComponent;
import com.sixpl.component.OrbitComponent;
import com.sixpl.component.SpellComponent;
import com.sixpl.config.SpellConfig;
import com.sixpl.interactions.SpellInteraction;
import com.sixpl.system.OrbitSystem;
import com.sixpl.system.SpellSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Objects;
import java.util.UUID;

public class MagicModule extends JavaPlugin {
    private static MagicModule instance;
    private ComponentType<EntityStore, OrbitComponent> orbitComponent;
    private ComponentType<EntityStore, SpellComponent> spellComponent;
    private ComponentType<EntityStore, ArcComponent> arcComponent;
    private Config<SpellConfig> spellConfig;

    public static MagicModule get() { return instance; }

    public MagicModule(@NonNullDecl JavaPluginInit init) {
        super(init);
        instance = this;
        this.spellConfig = this.withConfig("SpellConfig", SpellConfig.CODEC);
        System.out.println("Init");
    }

    protected void setup(){
        System.out.println("Setting up");
        ComponentRegistryProxy<EntityStore> registryProxy = this.getEntityStoreRegistry();

        orbitComponent = registryProxy.registerComponent(OrbitComponent.class, "Orbit", OrbitComponent.CODEC);
        spellComponent = registryProxy.registerComponent(SpellComponent.class, "Spell", SpellComponent.CODEC);
        arcComponent = registryProxy.registerComponent(ArcComponent.class, ArcComponent::new);
        this.getCodecRegistry(Interaction.CODEC).register("SpellInteraction", SpellInteraction.class, SpellInteraction.CODEC);
        registryProxy.registerSystem(new OrbitSystem());
        registryProxy.registerSystem(new SpellSystem());
        getCommandRegistry().registerCommand(new SpawnOrbit());
    }

    public Holder<EntityStore> assembleSpellHolder(Ref<EntityStore> entity){
        Store<EntityStore> entityStore = entity.getStore();
        Holder<EntityStore> newHolder = EntityStore.REGISTRY.newHolder();
        UUID entityUUID = Objects.requireNonNull(entityStore.getComponent(entity, UUIDComponent.getComponentType())).getUuid();

        TransformComponent entityTransformComponent = entityStore.getComponent(entity, TransformComponent.getComponentType());

        assert entityTransformComponent != null;
        newHolder.addComponent(TransformComponent.getComponentType(), new TransformComponent(entityTransformComponent.getPosition(), entityTransformComponent.getRotation()));
        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("ice_bolt");
        assert modelAsset != null;
        Model model = Model.createScaledModel(modelAsset, 1);
        newHolder.addComponent(PersistentModel.getComponentType(), new PersistentModel(model.toReference()));
        newHolder.addComponent(ModelComponent.getComponentType(), new ModelComponent(model));
        newHolder.addComponent(UUIDComponent.getComponentType(), new UUIDComponent(UUID.randomUUID()));
        newHolder.addComponent(SpellComponent.getComponentType(), new SpellComponent(entityUUID));
        newHolder.putComponent(NetworkId.getComponentType(), new NetworkId(entityStore.getExternalData().takeNextNetworkId()));

        return newHolder;
    }

    public void spawnOrbitObject(Ref<EntityStore> entity){
        Store<EntityStore> entityStore = entity.getStore();
        World world = entityStore.getExternalData().getWorld();
        world.execute(() -> {
            Holder<EntityStore> newHolder = assembleSpellHolder(entity);
            OrbitComponent orbitComponent = new OrbitComponent();
            newHolder.addComponent(OrbitComponent.getComponentType(), orbitComponent);
            entityStore.addEntity(newHolder, AddReason.SPAWN);
        });
    }

    public ComponentType<EntityStore, OrbitComponent> getOrbitComponentType() {
        return this.orbitComponent;
    }
    public ComponentType<EntityStore, SpellComponent> getSpellComponentType() { return this.spellComponent; }
    public ComponentType<EntityStore, ArcComponent> getArcComponentType() { return this.arcComponent; }

    public void tick(float deltaTime){

    }
}
