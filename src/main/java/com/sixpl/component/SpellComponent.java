package com.sixpl.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.UUID;

public class SpellComponent implements Component<EntityStore> {
    public static BuilderCodec<SpellComponent> CODEC;
    private float lifetime;
    private String spellType;
    private UUID caster;

    private SpellComponent(){}

    public SpellComponent(UUID entityUUID) {
        this.setCaster(entityUUID);
    }

    public void setLifetime(float newLifetime){
        this.lifetime = newLifetime;
    }
    public void setSpellType(String newSpellType){
        this.spellType = newSpellType;
    }
    public void setCaster(UUID newCaster){
        this.caster = newCaster;
    }

    public float getLifetime(){
        return this.lifetime;
    }
    public String getSpellType(){
        return this.spellType;
    }
    public UUID getCaster(){
        return this.caster;
    }

    public static ComponentType<EntityStore, SpellComponent> getComponentType(){
        return MagicModule.get().getSpellComponentType();
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return this;
    }

    static {
        CODEC = BuilderCodec.builder(SpellComponent.class, SpellComponent::new).append(
            new KeyedCodec<>("Lifetime", Codec.FLOAT),
                    ((spellComponent, newLifetime) -> spellComponent.lifetime = newLifetime),
                    (spellComponent -> spellComponent.lifetime)
            ).documentation("The time before the spell expires and disappears. Set to 0 for a permanent spell.").add()
            .append(new KeyedCodec<>("SpellType", Codec.STRING),
                    ((spellComponent, s) -> spellComponent.spellType = s),
                    (spellComponent -> spellComponent.spellType)
            ).documentation("The type of the spell, mostly so you can name your attribute.").add()
            .append(new KeyedCodec<>("Caster", Codec.UUID_BINARY),
                    ((spellComponent, uuid) -> spellComponent.caster = uuid),
                    (spellComponent -> spellComponent.caster)
            ).documentation("The caster of the spell, this will be set by the interaction.")
            .add().build();
    }
}
