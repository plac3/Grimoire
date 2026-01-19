package com.sixpl.system;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.component.SpellComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class SpellSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float deltaTime, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(i);
        SpellComponent spellComponent = store.getComponent(entityRef, SpellComponent.getComponentType());
        assert spellComponent != null;
        assert spellComponent.getCaster() != null;
        Ref<EntityStore> caster = store.getExternalData().getRefFromUUID(spellComponent.getCaster());
        if (entityRef != null && entityRef.isValid() && caster != null && caster.isValid()){
            spellComponent.setLifetime(spellComponent.getLifetime()-deltaTime);
            if (spellComponent.getLifetime() <= 0){
                store.removeEntity(entityRef, RemoveReason.REMOVE);
                return;
            }
        } else {
            store.removeEntity(entityRef, RemoveReason.REMOVE);
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return SpellComponent.getComponentType();
    }
}
