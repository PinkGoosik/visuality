package ru.pinkgoosik.visuality.config;

import ru.pinkgoosik.goosikconfig.api.Config;

public class VisualityConfig extends Config {
    private static final int CURRENT_CONFIG_VERSION = 1;

    public VisualityConfig(String name) {
        super(name);
    }

    @Override
    public void init() {
        addInteger("version", CURRENT_CONFIG_VERSION);
        addBoolean("hit_particles", "particles", true);
        addBoolean("slime", "particles", true);
        addBoolean("charge", "particles", true);
        addBoolean("sparkle", "particles", true);
        addBoolean("water_circle", "particles", true);
        addStringsArray("hit_particles_entries", "hit_particles", BuiltinHitParticles.LIST);
        ConfigFixer.fix(this, CURRENT_CONFIG_VERSION);
    }
}
