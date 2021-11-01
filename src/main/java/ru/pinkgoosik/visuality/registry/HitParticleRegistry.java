package ru.pinkgoosik.visuality.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.pinkgoosik.visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class HitParticleRegistry {
    public static final ArrayList<Entry> ENTRIES = new ArrayList<>();

    public static void reload(){
        ENTRIES.clear();
        ArrayList<Entry> entries = new ArrayList<>();
        VisualityMod.config.hit_particles.forEach(configuredHitParticle -> {
            EntityType<?> entity = getEntityFromString(configuredHitParticle.entity);
            ParticleEffect particle = getParticleFromString(configuredHitParticle.particle);
            float height = configuredHitParticle.height;
            if(entity != null && particle != null && height > 0){
                entries.add(new Entry(entity, particle, height));
            }
        });
        ENTRIES.addAll(entries);
    }

    private static EntityType<?> getEntityFromString(String id){
        Optional<EntityType<?>> optional = Registry.ENTITY_TYPE.getOrEmpty(new Identifier(id));
        return optional.orElse(null);
    }

    private static DefaultParticleType getParticleFromString(String id){
        Optional<ParticleType<?>> optional = Registry.PARTICLE_TYPE.getOrEmpty(new Identifier(id));
        return (DefaultParticleType)optional.orElse(null);
    }

    public record Entry(EntityType<?> entity, ParticleEffect particle, float height){}
}
