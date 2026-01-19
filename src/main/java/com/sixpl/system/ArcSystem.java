package com.sixpl.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.component.ArcComponent;
import com.sixpl.component.SpellComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ArcSystem extends EntityTickingSystem<EntityStore> {
    Query<EntityStore> query = Query.and(ArcComponent.getComponentType(), TransformComponent.getComponentType(), SpellComponent.getComponentType());

    Vector3d start;
    Vector3d middle;
    Vector3d end;

    @Override
    public void tick(float deltaTime, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(i);

        ArcComponent arcComponent = store.getComponent(entityRef, ArcComponent.getComponentType());
        TransformComponent transformComponent = store.getComponent(entityRef, TransformComponent.getComponentType());

        assert (arcComponent != null && transformComponent != null);

        Vector3d start = arcComponent.getStart();
        Vector3d middle = arcComponent.getMiddle();
        Vector3d end = arcComponent.getEnd();
        double alpha = arcComponent.getAlpha();

        Vector3d startToMiddle = Vector3d.lerp(start, middle, alpha);
        Vector3d middleToEnd = Vector3d.lerp(middle, end, alpha);
        Vector3d bezierCurve = Vector3d.lerp(startToMiddle, middleToEnd, alpha);

        transformComponent.setPosition(bezierCurve);
        if (!arcComponent.paused) {
            arcComponent.setAlpha(Math.min(alpha+deltaTime, 1));
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return query;
    }
}
