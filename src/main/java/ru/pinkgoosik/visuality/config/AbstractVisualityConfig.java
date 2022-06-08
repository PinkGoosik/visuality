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
    public ArrayList<String> shinyArmorEntries = VisualityConfig.DEFAULT_SHINY_ARMOR;

    public boolean shinyBlocksEnabled = true;
    public ArrayList<String> shinyBlockEntries = VisualityConfig.DEFAULT_SHINY_BLOCKS;

    public static class WaterCirclesConfigOption {
        public int density = 10;
        public int radius = 16;
        public boolean enabled = true;
        public boolean colored = true;
    }
}
