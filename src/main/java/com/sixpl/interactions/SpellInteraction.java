package com.sixpl.interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import com.sixpl.config.SpellConfig;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SpellInteraction extends SimpleInstantInteraction {
    private String config;
    public static final BuilderCodec<SpellInteraction> CODEC = BuilderCodec.builder(SpellInteraction.class, SpellInteraction::new, SimpleInstantInteraction.CODEC)
            .documentation("Spawn a spell.").append(new KeyedCodec<>("Config", Codec.STRING),
                    ((spellInteraction, s) -> spellInteraction.config = s),
                    (spellInteraction -> spellInteraction.config)).add().build();

    private SpellConfig getConfig(){
        return (SpellConfig) SpellConfig.getAssetMap().getAsset(this.config);
    }

    @Override
    protected void firstRun(@NonNullDecl InteractionType interactionType, @NonNullDecl InteractionContext interactionContext, @NonNullDecl CooldownHandler cooldownHandler) {
        Ref<EntityStore> entityStoreRef = interactionContext.getEntity();
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        interactionContext.getState().state = InteractionState.Failed;

        if (commandBuffer == null) {
            return;
        }

        interactionContext.getState().state = InteractionState.Finished;
        MagicModule.get().spawnOrbitObject(interactionContext.getEntity());
    }

}
