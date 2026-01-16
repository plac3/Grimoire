package com.sixpl.config;

import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;

import java.util.Map;

public class SpellConfig {
    Map<String, Number> SPELLTYPE = Map.of("ORBIT", 1);

    private SpellPhysicsConfig physicsConfig;
    private Map<InteractionType, String> interactions;
    private String modelPath;
    private Model generatedModel;
    private int spellType;

}
