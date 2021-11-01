package ru.pinkgoosik.visuality.config;

import java.util.ArrayList;

public class Config {
    public int version = 0;
    public Particles particles = new Particles();
    public ArrayList<ConfiguredHitParticle> hit_particles = BuiltinHitParticles.LIST;

    public static class Particles {
        public boolean hit_particles = true;
        public boolean slime = true;
        public boolean charge = true;
        public boolean sparkle = true;
        public boolean water_circle = true;
    }

    public static class ConfiguredHitParticle {
        public String entity = "";
        public String particle = "";
        public float height = 1;
    }
}
