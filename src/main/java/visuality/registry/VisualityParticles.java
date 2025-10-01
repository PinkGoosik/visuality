package visuality.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import visuality.VisualityMod;
import visuality.particle.*;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class VisualityParticles {
	public static final Map<SimpleParticleType, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType>> FACTORIES = new LinkedHashMap<>();

	public static final SimpleParticleType SPARKLE = add("sparkle", SparkleParticle.Factory::new);
	public static final SimpleParticleType BONE = add("bone", SolidFallingParticle.Factory::new);
	public static final SimpleParticleType WITHER_BONE = add("wither_bone", SolidFallingParticle.Factory::new);
	public static final SimpleParticleType FEATHER = add("feather", FeatherParticle.Factory::new);
	public static final SimpleParticleType COLD_FEATHER = add("cold_feather", FeatherParticle.Factory::new);
	public static final SimpleParticleType WARM_FEATHER = add("warm_feather", FeatherParticle.Factory::new);
	public static final SimpleParticleType SMALL_SLIME_BLOB = add("small_slime_blob", SlimeParticle.Factory::new);
	public static final SimpleParticleType MEDIUM_SLIME_BLOB = add("medium_slime_blob", SlimeParticle.Factory::new);
	public static final SimpleParticleType BIG_SLIME_BLOB = add("big_slime_blob", SlimeParticle.Factory::new);
	public static final SimpleParticleType CHARGE = add("charge", ChargeParticle.Factory::new);
	public static final SimpleParticleType WATER_CIRCLE = add("water_circle", WaterCircleParticle.Factory::new);
	public static final SimpleParticleType EMERALD = add("emerald", SolidFallingParticle.Factory::new);
	public static final SimpleParticleType SOUL = add("soul", SoulParticle.Factory::new);

	public static void init() {
		ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
		FACTORIES.forEach(registry::register);
	}

	private static SimpleParticleType add(String name, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType> constructor) {
		var particle = Registry.register(Registries.PARTICLE_TYPE, VisualityMod.id(name), FabricParticleTypes.simple());
		FACTORIES.put(particle, constructor);
		return particle;
	}
}
