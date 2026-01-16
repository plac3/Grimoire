package com.sixpl;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.sixpl.commands.SpawnOrbit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Magic extends JavaPlugin {
    private static MagicModule magicModule;
    public Magic(@NonNullDecl JavaPluginInit init) {
        super(init);
        magicModule = new MagicModule(init);
    }

    public void setup(){
        getCommandRegistry().registerCommand(new SpawnOrbit());
    }
}
