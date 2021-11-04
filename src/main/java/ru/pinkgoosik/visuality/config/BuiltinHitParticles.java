package ru.pinkgoosik.visuality.config;

import java.util.ArrayList;

public class BuiltinHitParticles {
    public static final ArrayList<String> LIST = new ArrayList<>();

    static {
        LIST.add("minecraft:skeleton|visuality:bone");
        LIST.add("minecraft:skeleton_horse|visuality:bone");
        LIST.add("minecraft:stray|visuality:bone");
        LIST.add("minecraft:wither_skeleton|visuality:wither_bone");
        LIST.add("minecraft:chicken|visuality:feather");
        LIST.add("minecraft:villager|visuality:emerald");
    }
}
