package visuality.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;

public class HitParticleRegistry {
	public static final ArrayList<Entry> ENTRIES = new ArrayList<>();

	public static void reload() {
		ENTRIES.clear();
		ArrayList<Entry> entries = new ArrayList<>();
		VisualityMod.config.hitParticleEntries.forEach(entry -> {
			String[] args = entry.split("/");

			Optional<EntityType<?>> entity = getEntityFromString(args[0]);
			Optional<ParticleType<?>> particle = getParticleFromString(args[1]);
			if(entity.isPresent() && particle.isPresent()) {
				entries.add(new Entry(entity.get(), (ParticleOptions) particle.get()));
			}
		});
		ENTRIES.addAll(entries);
	}

	private static Optional<EntityType<?>> getEntityFromString(String id) {
		return Registry.ENTITY_TYPE.getOptional(new ResourceLocation(id));
	}

	private static Optional<ParticleType<?>> getParticleFromString(String id) {
		return Registry.PARTICLE_TYPE.getOptional(new ResourceLocation(id));
	}

	public record Entry(EntityType<?> entity, ParticleOptions particle) {
	}
}
