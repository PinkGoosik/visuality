package ru.pinkgoosik.visuality.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.particle.*;

public class VisualityParticles {
    public static final DefaultParticleType SPARKLE = add("sparkle");
    public static final DefaultParticleType BONE = add("bone");
    public static final DefaultParticleType WITHER_BONE = add("wither_bone");
    public static final DefaultParticleType FEATHER = add("feather");
    public static final DefaultParticleType UNDERWATER_BUBBLE = add("underwater_bubble");
    public static final DefaultParticleType SMALL_SLIME_BLOB = add("small_slime_blob");
    public static final DefaultParticleType MEDIUM_SLIME_BLOB = add("medium_slime_blob");
    public static final DefaultParticleType BIG_SLIME_BLOB = add("big_slime_blob");
    public static final DefaultParticleType CHARGE = add("charge");
    public static final DefaultParticleType WATER_CIRCLE = add("water_circle");
    public static final DefaultParticleType EMERALD = add("emerald");

    public static void register() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
        registry.register(SPARKLE, SparkleParticle.Factory::new);
        registry.register(BONE, BoneParticle.Factory::new);
        registry.register(WITHER_BONE, BoneParticle.Factory::new);
        registry.register(FEATHER, FeatherParticle.Factory::new);
        registry.register(UNDERWATER_BUBBLE, UnderwaterBubbleParticle.Factory::new);
        registry.register(SMALL_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(MEDIUM_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(BIG_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(CHARGE, ChargeParticle.Factory::new);
        registry.register(WATER_CIRCLE, WaterCircleParticle.Factory::new);
        registry.register(EMERALD, EmeraldParticle.Factory::new);
    }

    private static DefaultParticleType add(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, VisualityMod.newId(name), FabricParticleTypes.simple());
    }
}
