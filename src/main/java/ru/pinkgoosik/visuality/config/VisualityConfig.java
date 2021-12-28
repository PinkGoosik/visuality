package ru.pinkgoosik.visuality.config;

import ru.pinkgoosik.goosikconfig.api.Config;

public class VisualityConfig extends Config {

    public VisualityConfig(String name) {
        super(name);
    }

    @Override
    public void init() {
        addBoolean("slime", "particles", true);
        addBoolean("charge", "particles", true);
        addBoolean("crystal_sparkle", "particles", true);
        addBoolean("soul", "particles", true);

        addBoolean("enabled", "water_circles", true);
        addBoolean("colored", "water_circles", true);

        addBoolean("enabled", "hit_particles", true);
        addStringsArray("entries", "hit_particles", BuiltinHitParticles.LIST);

        addBoolean("enabled", "shiny_armor", true);
        addStringsArray("entries", "shiny_armor", BuiltinShinyArmor.LIST);

        addBoolean("enabled", "shiny_blocks", true);
        addStringsArray("entries", "shiny_blocks", BuiltinShinyBlocks.LIST);
    }
}
