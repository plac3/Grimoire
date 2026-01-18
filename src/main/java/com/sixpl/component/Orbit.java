package com.sixpl.component;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.math.shape.Box;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.collision.BlockContactData;
import com.hypixel.hytale.server.core.modules.collision.BlockData;
import com.hypixel.hytale.server.core.modules.collision.IBlockCollisionConsumer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;

import java.util.UUID;

public class Orbit implements Component<EntityStore>, IBlockCollisionConsumer {
    private UUID caster = new UUID(0, 0);
    private double timer = 0;
    private double amplitude = 5;
    private double speed = 20;
    public static BuilderCodec<Orbit> CODEC;

    public static ComponentType<EntityStore, Orbit> getComponentType(){
        return MagicModule.get().getOrbitComponentType();
    }

    public Component<EntityStore> clone(){
        Orbit newItem = new Orbit();
        newItem.caster = this.caster;
        newItem.amplitude = this.amplitude;
        newItem.speed = this.speed;
        newItem.timer = this.timer;

        return newItem;
    }



    public void addTime(double amount){
        this.timer += amount;
    }
    public double getTime(){
        return this.timer;
    }
    public void setCaster(UUID uuid){
        this.caster = uuid;
    }
    public UUID getCaster() {
        return this.caster;
    }
    public void setAmplitude(double Amplitude){
        this.amplitude += Amplitude;
    }
    public double getAmplitude(){
        return this.amplitude;
    }
    public void setSpeed(double Speed){
        this.speed += Speed;
    }
    public double getSpeed(){
        return this.speed;
    }


    @Override
    public Result onCollision(int i, int i1, int i2, Vector3d vector3d, BlockContactData blockContactData, BlockData blockData, Box box) {
        return Result.STOP;
    }

    @Override
    public Result probeCollisionDamage(int i, int i1, int i2, Vector3d vector3d, BlockContactData blockContactData, BlockData blockData) {
        return Result.CONTINUE;
    }

    @Override
    public void onCollisionDamage(int i, int i1, int i2, Vector3d vector3d, BlockContactData blockContactData, BlockData blockData) {

    }

    @Override
    public Result onCollisionSliceFinished() {
        return Result.CONTINUE;
    }

    @Override
    public void onCollisionFinished() {

    }
    static {
        CODEC = ((((BuilderCodec.builder(Orbit.class, Orbit::new)
                .append(
                        new KeyedCodec<>("CasterId", BuilderCodec.STRING),
                        ((orbit, casterId) -> orbit.caster = UUID.fromString(casterId)),
                        ((orbit) -> orbit.caster.toString())
                ).add()).append(
                new KeyedCodec<>("Timer", BuilderCodec.DOUBLE),
                ((orbit, aDouble) -> orbit.timer = aDouble),
                (orbit) -> orbit.timer
        ).add()).append(
                new KeyedCodec<>("Amplitude", BuilderCodec.DOUBLE),
                (((orbit, aDouble) -> orbit.amplitude = aDouble)),
                ((orbit) -> orbit.amplitude)
        ).add()).append(
                new KeyedCodec<>("Speed", BuilderCodec.DOUBLE),
                ((orbit, aDouble) -> orbit.speed = aDouble),
                (orbit -> orbit.speed)
        ).add()).build();
    }
}
