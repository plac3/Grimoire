package com.sixpl.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.sixpl.MagicModule;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SpawnOrbit extends AbstractCommand {
    public SpawnOrbit() {
        super("spawnOrbit", "Spawns an orbiting projectile for debug purposes");
    }

    @NullableDecl
    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        assert commandContext.isPlayer();
        UUID caster = commandContext.sender().getUuid();
        Universe universe = Universe.get();
        PlayerRef casterPlayer = universe.getPlayer(caster);
        commandContext.sender().sendMessage(Message.raw("Creating orbit!"));
        assert casterPlayer != null;
        UUID worldUUID = casterPlayer.getWorldUuid();
        assert worldUUID != null;
        World world = universe.getWorld(worldUUID);
        assert world != null;


        MagicModule.get().spawnOrbitObject(Objects.requireNonNull(world.getEntityRef(caster)));

        return CompletableFuture.completedFuture(null);
    }
}
