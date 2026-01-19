package com.sixpl.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.sixpl.MagicModule;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;


public class ArcComponent implements Component<EntityStore> {
    private Vector3d start;
    private Vector3d middle;
    private Vector3d end;
    private double alpha;
    public boolean paused;

    public ArcComponent(){

    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    public void setStart(Vector3d start) {
        this.start = start;
    }
    public void setEnd(Vector3d end) {
        this.end = end;
    }
    public void setMiddle(Vector3d middle) {
        this.middle = middle;
    }

    public double getAlpha() {
        return this.alpha;
    }
    public Vector3d getStart() {
        return this.start;
    }
    public Vector3d getMiddle() {
        return this.middle;
    }
    public Vector3d getEnd() {
        return this.end;
    }

    public static ComponentType<EntityStore, ArcComponent> getComponentType(){
        return MagicModule.get().getArcComponentType();
    }
    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        ArcComponent clonedArcComponent = new ArcComponent();
        clonedArcComponent.setAlpha(this.getAlpha());
        clonedArcComponent.setStart(this.getStart());
        clonedArcComponent.setMiddle(this.getMiddle());
        clonedArcComponent.setEnd(this.getEnd());
        return clonedArcComponent;
    }
}
