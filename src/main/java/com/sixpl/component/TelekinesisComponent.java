package com.sixpl.component;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TelekinesisComponent implements Component<EntityStore> {
    private double distance;

    public TelekinesisComponent(){

    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static ComponentType<EntityStore, TelekinesisComponent> getComponentType(){
        return MagicModule.get().getTelekinesisComponentType();
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        TelekinesisComponent newTelekinesisComponent = new TelekinesisComponent();
        newTelekinesisComponent.setDistance(this.getDistance());

        return newTelekinesisComponent;
    }
}
