package com.sixpl.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.component.MagicCircleComponent;
import com.sixpl.component.SpellComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class MagicCircleSystem extends EntityTickingSystem<EntityStore> {
    Query<EntityStore> query = Query.and(MagicCircleComponent.getComponentType(), TransformComponent.getComponentType(), SpellComponent.getComponentType());

    private Vector3f deltaRotation;

    @Override
    public void tick(float deltaTime, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(i);

        TransformComponent transformComponent = store.getComponent(entityRef, TransformComponent.getComponentType());
        MagicCircleComponent magicCircleComponent = store.getComponent(entityRef, MagicCircleComponent.getComponentType());

        assert (transformComponent != null && magicCircleComponent != null);
        deltaRotation = transformComponent.getRotation().add(0, magicCircleComponent.getRotationIncrement()*deltaTime, 0);
        transformComponent.setRotation(deltaRotation);

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return query;
    }
}
