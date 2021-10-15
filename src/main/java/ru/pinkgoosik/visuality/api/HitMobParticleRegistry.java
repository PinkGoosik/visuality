package ru.pinkgoosik.visuality.api;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;

import java.util.ArrayList;

public class HitMobParticleRegistry {
    private static final ArrayList<Entry> ENTRIES = new ArrayList<>();

    public static void register(EntityType<?> entityType, ParticleEffect particleEffect, int height){
        ENTRIES.add(new Entry(entityType, particleEffect, height));
    }

    public static ArrayList<Entry> getEntries(){
        return ENTRIES;
    }

    public record Entry(EntityType<?> entityType, ParticleEffect particleEffect, int height){}
}
