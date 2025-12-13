package visuality.registry;

import visuality.VisualityMod;

import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

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
		return BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.parse(id));
	}

	private static Optional<ParticleType<?>> getParticleFromString(String id) {
		return BuiltInRegistries.PARTICLE_TYPE.getOptional(Identifier.parse(id));
	}

	public record Entry(EntityType<?> entity, ParticleOptions particle) {
	}
}
