package com.sixpl.component;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

//No model will be provided, just rotating on itself, and some interactions.
public class MagicCircleComponent implements Component<EntityStore> {

    private float rotation;
    private float rotationIncrement;
    private double timeAlive;


    public void setRotationIncrement(float rotationIncrement) {
        this.rotationIncrement = rotationIncrement;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public void setTimeAlive(double timeAlive) {
        this.timeAlive = timeAlive;
    }

    public float getRotationIncrement() {
        return rotationIncrement;
    }
    public float getRotation() {
        return this.rotation;
    }
    public double getTimeAlive() {
        return timeAlive;
    }

    public static ComponentType<EntityStore, MagicCircleComponent> getComponentType(){
        return MagicModule.get().getMagicCircleComponentType();
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        MagicCircleComponent newCircleComponent = new MagicCircleComponent();

        newCircleComponent.setRotation(this.getRotation());
        newCircleComponent.setRotationIncrement(this.getRotationIncrement());
        newCircleComponent.setTimeAlive(this.getTimeAlive());

        return newCircleComponent;
    }
}
