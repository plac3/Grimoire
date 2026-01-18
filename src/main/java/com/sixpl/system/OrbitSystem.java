package com.sixpl.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import com.sixpl.component.Orbit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class OrbitSystem extends EntityTickingSystem<EntityStore> {
    Query<EntityStore> query = Query.and(new Query[]{Orbit.getComponentType(), TransformComponent.getComponentType(), BoundingBox.getComponentType()});

    public OrbitSystem() {

    }

    @Override
    public void tick(float deltaTime, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
        TransformComponent transform = store.getComponent(entityRef, TransformComponent.getComponentType());
        assert transform != null;
        Orbit orbit = store.getComponent(entityRef, Orbit.getComponentType());
        assert orbit != null;
        PlayerRef player = Universe.get().getPlayer(orbit.getCaster());
        assert player != null;
        Ref<EntityStore> playerReference = player.getReference();

        if (playerReference != null && playerReference.isValid()){
            Vector3d PlayerPosition = player.getTransform().getPosition();
            double currentAngle = orbit.getTime()*orbit.getSpeed();
            double offsetX = Math.cos(currentAngle) * orbit.getAmplitude();
            double offsetY = Math.sin(currentAngle) * orbit.getAmplitude();

            Vector3d OrbitingPosition = PlayerPosition.clone().add(offsetX, 0, offsetY);
            transform.setPosition(OrbitingPosition);
            System.out.println("Updated position to orbit player.");
        }

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return MagicModule.get().getOrbitComponentType();
    }
}
