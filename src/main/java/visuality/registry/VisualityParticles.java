package visuality.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;
import visuality.VisualityMod;
import visuality.particle.*;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class VisualityParticles {
	public static final Map<DefaultParticleType, ParticleFactoryRegistry.PendingParticleFactory<DefaultParticleType>> FACTORIES = new LinkedHashMap<>();

	public static final DefaultParticleType SPARKLE = add("sparkle", SparkleParticle.Factory::new);
	public static final DefaultParticleType BONE = add("bone", SolidFallingParticle.Factory::new);
	public static final DefaultParticleType WITHER_BONE = add("wither_bone", SolidFallingParticle.Factory::new);
	public static final DefaultParticleType FEATHER = add("feather", FeatherParticle.Factory::new);
	public static final DefaultParticleType SMALL_SLIME_BLOB = add("small_slime_blob", SlimeParticle.Factory::new);
	public static final DefaultParticleType MEDIUM_SLIME_BLOB = add("medium_slime_blob", SlimeParticle.Factory::new);
	public static final DefaultParticleType BIG_SLIME_BLOB = add("big_slime_blob", SlimeParticle.Factory::new);
	public static final DefaultParticleType CHARGE = add("charge", ChargeParticle.Factory::new);
	public static final DefaultParticleType WATER_CIRCLE = add("water_circle", WaterCircleParticle.Factory::new);
	public static final DefaultParticleType EMERALD = add("emerald", SolidFallingParticle.Factory::new);
	public static final DefaultParticleType SOUL = add("soul", SoulParticle.Factory::new);

	public static void init() {
		ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
		FACTORIES.forEach(registry::register);
	}

	private static DefaultParticleType add(String name, ParticleFactoryRegistry.PendingParticleFactory<DefaultParticleType> constructor) {
		var particle = Registry.register(Registry.PARTICLE_TYPE, VisualityMod.id(name), FabricParticleTypes.simple());
		FACTORIES.put(particle, constructor);
		return particle;
	}
}
