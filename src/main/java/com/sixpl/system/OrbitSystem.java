package com.sixpl.system;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import com.sixpl.component.OrbitComponent;
import com.sixpl.component.SpellComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Objects;

public class OrbitSystem extends EntityTickingSystem<EntityStore> {
    Query<EntityStore> query = Query.and(OrbitComponent.getComponentType(), TransformComponent.getComponentType(), SpellComponent.getComponentType());

    public OrbitSystem() {

    }

    @Override
    public void tick(float deltaTime, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
        TransformComponent transform = store.getComponent(entityRef, TransformComponent.getComponentType());
        assert transform != null;
        OrbitComponent orbit = store.getComponent(entityRef, OrbitComponent.getComponentType());
        assert orbit != null;
        SpellComponent spellComponent = store.getComponent(entityRef, SpellComponent.getComponentType());
        assert spellComponent != null;
        orbit.addTime(deltaTime);

        Ref<EntityStore> caster = entityRef.getStore().getExternalData().getRefFromUUID(spellComponent.getCaster());

        if (entityRef.isValid() && caster != null && caster.isValid()){
            Vector3d PlayerPosition = Objects.requireNonNull(store.getComponent(caster, TransformComponent.getComponentType())).getTransform().getPosition();
            double currentAngle = orbit.getTime()*orbit.getSpeed();
            double offsetX = Math.cos(currentAngle)*deltaTime * orbit.getAmplitude();
            double offsetY = Math.sin(currentAngle)*deltaTime * orbit.getAmplitude();

            Vector3d OrbitingPosition = PlayerPosition.clone().add(offsetX, 1, offsetY);
            transform.setPosition(OrbitingPosition);
        } else {
            store.removeEntity(entityRef, RemoveReason.REMOVE);
        }

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return query;
    }
}
