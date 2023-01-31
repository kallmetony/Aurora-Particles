package io.github.aaronr92.auroraproject.listener;

import io.github.aaronr92.auroraproject.handler.ParticleLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PluginListener implements Listener {
    private final ParticleLoader particleLoader = ParticleLoader.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        particleLoader.sendParticlesToPlayer(event.getPlayer());
    }
}
