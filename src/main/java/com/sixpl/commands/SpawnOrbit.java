package com.sixpl.commands;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.protocol.Model;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.component.Orbit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SpawnOrbit extends AbstractCommand {
    public SpawnOrbit() {
        super("spawnOrbit", "Spawns an orbiting projectile for debug purposes");
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        assert commandContext.isPlayer();
        UUID caster = commandContext.sender().getUuid();
        Universe universe = Universe.get();
        PlayerRef casterPlayer = universe.getPlayer(caster);
        commandContext.sender().sendMessage(Message.raw("Creating orbit!"));
        assert casterPlayer != null;
        UUID worldUUID = casterPlayer.getWorldUuid();
        assert worldUUID != null;
        World world = universe.getWorld(worldUUID);
        assert world != null;
        world.execute(()->{
            EntityStore entityStores = world.getEntityStore();
            Store<EntityStore> entityStore = entityStores.getStore();


            Holder<EntityStore> tempHolder = entityStore.getRegistry().newHolder();
            Orbit entityOrbit = new Orbit();
            entityOrbit.setCaster(caster);
            tempHolder.addComponent(Orbit.getComponentType(), entityOrbit);

            TransformComponent entityPosition = new TransformComponent();
            entityPosition.setPosition(casterPlayer.getTransform().getPosition());
            tempHolder.addComponent(TransformComponent.getComponentType(), entityPosition);

            BoundingBox boundingBox = new BoundingBox();
            tempHolder.addComponent(BoundingBox.getComponentType(), boundingBox);

            tempHolder.ensureComponent(ProjectileComponent.getComponentType());

            commandContext.sender().sendMessage(Message.raw("Created holder, spawning!"));

            //Archetype<EntityStore> newEntityArchetype = Archetype.of(Orbit.getComponentType(), TransformComponent.getComponentType(), BoundingBox.getComponentType(), ProjectileComponent.getComponentType());
            Ref<EntityStore> newEntity = entityStore.addEntity(tempHolder, AddReason.SPAWN);

        });

        return CompletableFuture.completedFuture(null);
    }
}
