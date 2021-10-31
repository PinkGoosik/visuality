package ru.pinkgoosik.visuality.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import ru.pinkgoosik.visuality.api.HitParticleRegistry;

public class ModHitParticles {

    public static void register(){
        add(EntityType.SKELETON, VisualityParticles.BONE, 15);
        add(EntityType.SKELETON_HORSE, VisualityParticles.BONE, 15);
        add(EntityType.STRAY, VisualityParticles.BONE, 15);
        add(EntityType.WITHER_SKELETON, VisualityParticles.WITHER_BONE, 20);
        add(EntityType.CHICKEN, VisualityParticles.FEATHER, 3);
    }

    private static void add(EntityType<?> entity, ParticleEffect particle, int height){
        HitParticleRegistry.register(entity, particle, height);
    }
}
