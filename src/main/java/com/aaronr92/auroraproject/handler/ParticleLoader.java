package com.aaronr92.auroraproject.handler;

import com.aaronr92.auroraproject.Plugin;
import com.aaronr92.auroraproject.model.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticleLoader {

    private static final ParticleLoader instance = new ParticleLoader();
    private static final List<Particle> particles = Collections.synchronizedList(new ArrayList<>());

    public ParticleLoader() {
        init();
    }

    public void init() {
        ConfigurationSection configuration = Plugin.getPlugin().getConfig();
        configuration.getKeys(false).forEach(p -> {
            particles.add(new Particle(
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
            ));
        });
    }

    public Particle getParticle(int id) {
        return particles.get(id);
    }

    public void sendParticlesToPlayer(Player player) {
        particles.forEach(particle -> {
            player.spawnParticle(
                    particle.getParticle(),
                    particle.getX(),
                    particle.getY(),
                    particle.getZ(),
                    particle.getCount()
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
                    particle.getCount());
        });
    }
}
