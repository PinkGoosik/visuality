package ru.pinkgoosik.visuality.config;

import java.util.ArrayList;

public class BuiltinHitParticles {
    public static final ArrayList<Config.ConfiguredHitParticle> LIST = new ArrayList<>();

    static {
        add("minecraft:skeleton", "visuality:bone", 2F);
        add("minecraft:skeleton_horse", "visuality:bone", 2F);
        add("minecraft:stray", "visuality:bone", 2F);
        add("minecraft:wither_skeleton", "visuality:wither_bone", 2.5F);
        add("minecraft:chicken", "visuality:feather", 1F);
        add("minecraft:villager", "visuality:emerald", 2F);
    }

    private static void add(String entity, String particle, float height){
        var entry = new Config.ConfiguredHitParticle();
        entry.entity = entity;
        entry.particle = particle;
        entry.height = height;
        LIST.add(entry);
    }
}
