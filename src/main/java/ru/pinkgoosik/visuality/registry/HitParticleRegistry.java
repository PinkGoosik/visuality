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
        VisualityMod.CONFIG.getStringsArray("hit_particles_entries").forEach(entry -> {
            String[] args = entry.split("\\|");
            EntityType<?> entity = getEntityFromString(args[0]);
            ParticleEffect particle = getParticleFromString(args[1]);
            if(entity != null && particle != null){
                entries.add(new Entry(entity, particle));
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

    public record Entry(EntityType<?> entity, ParticleEffect particle){}
}
