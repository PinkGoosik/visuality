package visuality.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class ParticleUtils {

	public static void add(World world, ParticleEffect particle, double x, double y, double z) {
		world.addParticle(particle, x, y, z, 0, 0, 0);
	}

	public static void add(World world, ParticleEffect particle, double x, double y, double z, int color) {
		world.addParticle(particle, x, y, z, color, 0, 0);
	}

	public static void add(World world, ParticleEffect particle, double x, double y, double z, int color, double size) {
		world.addParticle(particle, x, y, z, color, size, 0);
	}
}
