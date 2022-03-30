package ru.pinkgoosik.visuality.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.particle.*;

public class VisualityParticles {
    public static final SimpleParticleType SPARKLE = add("sparkle");
    public static final SimpleParticleType BONE = add("bone");
    public static final SimpleParticleType WITHER_BONE = add("wither_bone");
    public static final SimpleParticleType FEATHER = add("feather");
    public static final SimpleParticleType SMALL_SLIME_BLOB = add("small_slime_blob");
    public static final SimpleParticleType MEDIUM_SLIME_BLOB = add("medium_slime_blob");
    public static final SimpleParticleType BIG_SLIME_BLOB = add("big_slime_blob");
    public static final SimpleParticleType CHARGE = add("charge");
    public static final SimpleParticleType WATER_CIRCLE = add("water_circle");
    public static final SimpleParticleType EMERALD = add("emerald");
    public static final SimpleParticleType SOUL = add("soul");
    public static final SimpleParticleType NOTE = add("note");

    public static void register() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
        registry.register(SPARKLE, SparkleParticle.Factory::new);
        registry.register(BONE, SolidFallingParticle.Factory::new);
        registry.register(WITHER_BONE, SolidFallingParticle.Factory::new);
        registry.register(FEATHER, FeatherParticle.Factory::new);
        registry.register(SMALL_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(MEDIUM_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(BIG_SLIME_BLOB, SlimeParticle.Factory::new);
        registry.register(CHARGE, ChargeParticle.Factory::new);
        registry.register(WATER_CIRCLE, WaterCircleParticle.Factory::new);
        registry.register(EMERALD, SolidFallingParticle.Factory::new);
        registry.register(SOUL, SoulParticle.Provider::new);
        registry.register(NOTE, JukeboxNoteParticle.Factory::new);
    }

    private static SimpleParticleType add(String name) {
        return Registry.register(Registry.PARTICLE_TYPE, VisualityMod.locate(name), FabricParticleTypes.simple());
    }
}
