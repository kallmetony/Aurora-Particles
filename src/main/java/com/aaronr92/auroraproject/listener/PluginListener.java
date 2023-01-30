package com.aaronr92.auroraproject.listener;

import com.aaronr92.auroraproject.handler.ParticleLoader;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PluginListener implements Listener {
    private final ParticleLoader particleLoader = ParticleLoader.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        particleLoader.sendParticlesToPlayer(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        particleLoader.sendParticleToAllPlayers(
                new com.aaronr92.auroraproject.model.Particle(
                        Particle.CLOUD,
                        loc.getBlockX(),
                        loc.getY(),
                        loc.getZ(),
                        1
                )
        );
    }
}
