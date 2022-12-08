package visuality.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
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
				entries.add(new Entry(entity.get(), (ParticleEffect) particle.get()));
			}
		});
		ENTRIES.addAll(entries);
	}

	private static Optional<EntityType<?>> getEntityFromString(String id) {
		return Registries.ENTITY_TYPE.getOrEmpty(new Identifier(id));
	}

	private static Optional<ParticleType<?>> getParticleFromString(String id) {
		return Registries.PARTICLE_TYPE.getOrEmpty(new Identifier(id));
	}

	public record Entry(EntityType<?> entity, ParticleEffect particle) {
	}
}
