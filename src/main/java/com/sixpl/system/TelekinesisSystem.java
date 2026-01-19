package com.sixpl.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.sixpl.component.MagicCircleComponent;
import com.sixpl.component.SpellComponent;
import com.sixpl.component.TelekinesisComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.UUID;

public class TelekinesisSystem extends EntityTickingSystem<EntityStore> {
    Query<EntityStore> query = Query.and(TelekinesisComponent.getComponentType(), TransformComponent.getComponentType(), SpellComponent.getComponentType());

    @Override
    public void tick(float deltaTime, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(i);
        SpellComponent spellComponent = store.getComponent(entityRef, SpellComponent.getComponentType());
        TransformComponent transformComponent = store.getComponent(entityRef, TransformComponent.getComponentType());
        TelekinesisComponent telekinesisComponent = store.getComponent(entityRef, TelekinesisComponent.getComponentType());

        assert (spellComponent != null && transformComponent != null && telekinesisComponent != null);

        UUID casterUUID = spellComponent.getCaster();
        Ref<EntityStore> caster = store.getExternalData().getRefFromUUID(casterUUID);

        assert caster != null;
        TransformComponent casterTransformComponent = store.getComponent(caster, TransformComponent.getComponentType());
        Transform lookAt = TargetUtil.getLook(caster, store);

        assert casterTransformComponent != null;
        transformComponent.setPosition(casterTransformComponent.getPosition().add(lookAt.getDirection().scale(5)));
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return query;
    }
}
