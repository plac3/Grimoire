package com.sixpl.config;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.JsonAsset;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class SpellConfig implements JsonAssetWithMap<String, DefaultAssetMap<String, SpellConfig>> {
    private double speed = 5;
    private double amplitude = 5;

    public static AssetStore<String, SpellConfig, DefaultAssetMap<String, SpellConfig>> getAssetStore(){
        return AssetRegistry.getAssetStore(SpellConfig.class);
    }

    public static DefaultAssetMap<String, SpellConfig> getAssetMap(){
        return getAssetStore().getAssetMap();
    }
    public static final BuilderCodec<SpellConfig> CODEC = BuilderCodec.builder(SpellConfig.class, SpellConfig::new
    ).append(
            new KeyedCodec<>("Speed", Codec.DOUBLE),
            ((spellConfig, aDouble) -> spellConfig.speed = aDouble),
            (spellConfig -> spellConfig.speed)
    ).documentation("The speed in which the orbit rotates every instance. [DEFAULT 5]")
    .add().append(
            new KeyedCodec<>("Amplitude", Codec.DOUBLE),
            ((spellConfig, aDouble) -> spellConfig.amplitude = aDouble),
            (spellConfig -> spellConfig.amplitude)
    ).documentation("The distance from the player to the circle. [DEFAULT 5]")
    .add().build();

    @Override
    public String getId() {
        return "Spell";
    }
}
