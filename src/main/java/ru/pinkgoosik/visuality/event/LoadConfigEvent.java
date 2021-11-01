package ru.pinkgoosik.visuality.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import ru.pinkgoosik.visuality.VisualityMod;
import ru.pinkgoosik.visuality.config.ConfigReader;
import ru.pinkgoosik.visuality.registry.HitParticleRegistry;

public class LoadConfigEvent implements ClientTickEvents.EndTick {
    private static boolean isLoaded = false;

    @Override
    public void onEndTick(MinecraftClient client) {
        if(client.world != null){
            if(!isLoaded){
                VisualityMod.config = ConfigReader.read();
                HitParticleRegistry.reload();
                isLoaded = true;
            }
        }else isLoaded = false;
    }
}
