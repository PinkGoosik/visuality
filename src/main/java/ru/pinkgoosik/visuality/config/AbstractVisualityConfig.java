package ru.pinkgoosik.visuality.config;

import java.util.ArrayList;

public class AbstractVisualityConfig {
    public boolean slimeEnabled = true;
    public boolean chargeEnabled = true;
    public boolean sparkleEnabled = true;
    public boolean soulEnabled = true;

    public VisualityClothConfig.WaterCirclesConfigOption waterCircles = new VisualityClothConfig.WaterCirclesConfigOption();

    public boolean hitParticlesEnabled = true;
    public ArrayList<String> hitParticleEntries = VisualityConfig.DEFAULT_HIT_PARTICLES;

    public boolean shinyArmorEnabled = true;
    public ArrayList<String> shinyArmor = VisualityConfig.DEFAULT_SHINY_ARMOR;

    public boolean shinyBlocksEnabled = true;
    public ArrayList<String> shinyBlocks = VisualityConfig.DEFAULT_SHINY_BLOCKS;

    public static class WaterCirclesConfigOption {
        public boolean enabled = true;
        public boolean colored = true;
    }
}
