package com.aaronr92.auroraproject.handler;

import com.aaronr92.auroraproject.Plugin;
import com.aaronr92.auroraproject.model.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticleLoader {

    private final Logger log = LoggerFactory.getLogger(ParticleLoader.class);
    private static final ParticleLoader instance = new ParticleLoader();
    private static final List<Particle> particles = Collections.synchronizedList(new ArrayList<>());

    public ParticleLoader() {

    }

    public void init() {
        ConfigurationSection configuration = Plugin.getPlugin().getConfig();
        configuration.getKeys(false).forEach(p -> {
            try {
                Particle particle = new Particle(
                        org.bukkit.Particle.valueOf(
                                configuration.getString(p + ".particle")
                        ),
                        configuration.getDouble(p + ".x"),
                        configuration.getDouble(p + ".y"),
                        configuration.getDouble(p + ".z"),
                        configuration.getInt(p + ".count"),
                        configuration.getDouble(p + ".offsetX"),
                        configuration.getDouble(p + ".offsetY"),
                        configuration.getDouble(p + ".offsetZ"),
                        configuration.getDouble(p + ".size")
                );
                particles.add(particle);
                long period = configuration.getLong(p + ".period");
                setupTask(
                        particle,
                        period == 0 ? 20L : period
                );
            } catch (IllegalArgumentException e) {
                log.warn("Particle is not loaded correctly. Check your config.yml!");
            }
        });
    }

    public void sendParticlesToPlayer(Player player) {
        particles.forEach(particle -> {
            player.spawnParticle(
                    particle.getParticle(),
                    particle.getX(),
                    particle.getY(),
                    particle.getZ(),
                    particle.getCount(),
                    particle.getOffsetX(),
                    particle.getOffsetY(),
                    particle.getOffsetZ(),
                    particle.getSize()
            );
        });
    }

    public static ParticleLoader getInstance() {
        return instance;
    }

    public void sendParticleToAllPlayers(Particle particle) {
        Plugin.getPlugin().getServer().getOnlinePlayers().forEach(player -> {
            player.spawnParticle(
                    particle.getParticle(),
                    particle.getX(),
                    particle.getY(),
                    particle.getZ(),
                    particle.getCount(),
                    particle.getOffsetX(),
                    particle.getOffsetY(),
                    particle.getOffsetZ(),
                    particle.getSize()
            );
        });
    }

    private void setupTask(Particle particle, long period) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendParticleToAllPlayers(particle);
            }
        }.runTaskTimer(Plugin.getPlugin(), 20l, period);
    }
}
