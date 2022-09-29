package visuality.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
		return Registry.ENTITY_TYPE.getOrEmpty(new Identifier(id));
	}

	private static Optional<ParticleType<?>> getParticleFromString(String id) {
		return Registry.PARTICLE_TYPE.getOrEmpty(new Identifier(id));
	}

	public record Entry(EntityType<?> entity, ParticleEffect particle) {
	}
}
