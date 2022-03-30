package ru.pinkgoosik.visuality.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class JukeboxNoteParticle extends RisingParticle {

    private JukeboxNoteParticle(ClientLevel level, double x, double y, double z, double color) {
        super(level, x, y, z, 0, 0, 0);
        this.setColor((int) color);
        this.scale(1.0F + (float)level.random.nextInt(3) / 10);
        this.lifetime = 5 + this.random.nextInt(9);

        this.yd = 0.1D;
    }

    public void setColor(int rgbHex) {
        float red = (float)((rgbHex & 16711680) >> 16) / 255.0F;
        float green = (float)((rgbHex & '\uff00') >> 8) / 255.0F;
        float blue = (float)((rgbHex & 255)) / 255.0F;
        this.setColor(red, green, blue);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age > this.lifetime / 2) {
            this.setAlpha(1.0F - ((float)this.age - (float)(this.lifetime / 2)) / (float)this.lifetime);
            this.yd = this.yd - 0.05D;
        }
        if(this.age == 1){
            this.yd = this.yd + random.nextDouble() / 100;
        }

        if(this.age > this.lifetime) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
            JukeboxNoteParticle particle = new JukeboxNoteParticle(world, x, y, z, velX);
            particle.setSpriteFromAge(sprites);
            return particle;
        }
    }
}
