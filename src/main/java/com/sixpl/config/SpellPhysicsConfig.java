package com.sixpl.config;

import com.hypixel.hytale.protocol.RotationMode;
import com.hypixel.hytale.server.core.modules.projectile.config.PhysicsConfig;
import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsConfig;

import javax.annotation.Nonnull;

public class SpellPhysicsConfig extends StandardPhysicsConfig implements PhysicsConfig {
    @Nonnull
    protected RotationMode rotationMode;
    protected boolean allowRolling;
    protected double rollingFrictionFactor;
    protected float rollingSpeed;
    protected double moveOutOfSolidSpeed;
    protected double terminalVelocityAir;
    protected double densityAir;
    protected double terminalVelocityWater;
    protected double densityWater;
    protected double hitWaterImpulseLoss;
    protected double rotationForce;
    protected float speedRotationFactor;
    protected double swimmingDampingFactor;

    public SpellPhysicsConfig() {
        this.rotationMode = RotationMode.VelocityDamped;
        this.allowRolling = false;
        this.rollingFrictionFactor = 0;
        this.rollingSpeed = 0F;
        this.terminalVelocityAir = (double)0F;
        this.densityAir = 0;
        this.terminalVelocityWater = (double)0F;
        this.densityWater = (double)0F;
        this.hitWaterImpulseLoss = 0;
        this.rotationForce = (double)0F;
        this.speedRotationFactor = 0F;
        this.swimmingDampingFactor = (double)0F;
    }

}
