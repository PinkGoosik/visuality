package ru.pinkgoosik.visuality.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import ru.pinkgoosik.visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class HitParticleRegistry {
    public static final ArrayList<Entry> ENTRIES = new ArrayList<>();

    public static void reload(){
        ENTRIES.clear();
        ArrayList<Entry> entries = new ArrayList<>();
        VisualityMod.CONFIG.getStringsArray("entries", "hit_particles").forEach(entry -> {
            String[] args = entry.split("\\|");
            EntityType<?> entity = getEntityFromString(args[0]);
            ParticleOptions particle = getParticleFromString(args[1]);
            if(entity != null && particle != null){
                entries.add(new Entry(entity, particle));
            }
        });
        ENTRIES.addAll(entries);
    }

    private static EntityType<?> getEntityFromString(String id){
        Optional<EntityType<?>> optional = Registry.ENTITY_TYPE.getOptional(new ResourceLocation(id));
        return optional.orElse(null);
    }

    private static SimpleParticleType getParticleFromString(String id){
        Optional<ParticleType<?>> optional = Registry.PARTICLE_TYPE.getOptional(new ResourceLocation(id));
        return (SimpleParticleType)optional.orElse(null);
    }

    public record Entry(EntityType<?> entity, ParticleOptions particle){}
}
