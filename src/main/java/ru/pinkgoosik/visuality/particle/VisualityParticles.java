package ru.pinkgoosik.visuality.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.NoteParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.pinkgoosik.visuality.VisualityMod;

public class VisualityParticles {

    public static final DefaultParticleType SPARKLE = add("sparkle");
    public static final DefaultParticleType BONE = add("bone");
    public static final DefaultParticleType WITHER_BONE = add("wither_bone");
    public static final DefaultParticleType FEATHER = add("feather");
    public static final DefaultParticleType ROCK = add("rock");
    public static final DefaultParticleType UNDERWATER_BUBBLE = add("underwater_bubble");
    public static final DefaultParticleType SLIME = add("slime");
    public static final DefaultParticleType NOTE = add("note");

    public static void register() {
        ParticleFactoryRegistry.getInstance().register(SPARKLE, SparkleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BONE, BoneParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WITHER_BONE, BoneParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FEATHER, FeatherParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ROCK, FallingRockParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(UNDERWATER_BUBBLE, UnderwaterBubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SLIME, SlimeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(NOTE, NoteParticle.Factory::new);
    }

    private static DefaultParticleType add(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(VisualityMod.MOD_ID, name), FabricParticleTypes.simple());
    }
}
