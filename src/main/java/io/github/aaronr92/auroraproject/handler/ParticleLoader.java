package io.github.aaronr92.auroraproject.handler;

import io.github.aaronr92.auroraproject.ParticleChunkSpawnRunnable;
import io.github.aaronr92.auroraproject.Plugin;
import io.github.aaronr92.auroraproject.model.Particle;
import io.sentry.util.Pair;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
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

    public void drawCircle(Location center, double radius, int numParticles) {
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * i / numParticles;
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY();
            double z = center.getZ() + radius * Math.sin(angle);
            center.getWorld().spawnParticle(
                    org.bukkit.Particle.COMPOSTER,
                    new Location(center.getWorld(), x, y, z), 0
            );
        }
    }

    public void drawCircleWithRotation(Location center, double radius, int numParticles, double alpha, double beta, double gamma) {
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * i / numParticles;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            // Rotate around X axis
            double y1 = - z * Math.sin(alpha);
            double z1 = z * Math.cos(alpha);

            // Rotate around Y axis
            double x2 = x * Math.cos(beta) + z1 * Math.sin(beta);
            double z2 = -x * Math.sin(beta) + z1 * Math.cos(beta);

            // Rotate around Z axis
            double x3 = x2 * Math.cos(gamma) - y1 * Math.sin(gamma);
            double y3 = x2 * Math.sin(gamma) + y1 * Math.cos(gamma);

            center.getWorld().spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    new Location(
                            center.getWorld(),
                            center.getX() + x3,
                            center.getY() + y3,
                            center.getZ() + z2
                    ),
                    0,
                    new org.bukkit.Particle.DustOptions(Color.BLUE, 1f)
            );
        }
    }

    public void drawVerticalCircle(Player player, double radius, int numParticles) {
    Location center = player.getLocation();
//    double yaw = Math.toRadians(center.getYaw());
    // Rotating to be in front of player
    double yaw = Math.toRadians(center.getYaw()) + Math.PI / 2;

        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * i / numParticles;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            // Rotate around Y axis
            double x1 = x * Math.cos(yaw);
            double z1 = x * Math.sin(yaw);

            center.getWorld().spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    new Location(
                            center.getWorld(),
                            center.getX() + x1,
                            center.getY() + y,
                            center.getZ() + z1
                    ),
                    0,
                    new org.bukkit.Particle.DustOptions(Color.BLUE, 1f)
            );
        }
    }

    public void drawVerticalSemicircle(Player player, Color color, double radius, int numParticles, double offset) {
        Location center = player.getLocation();

        center.add(0, 1, 0);

        double yaw = Math.toRadians(center.getYaw()) + Math.PI / 2;

        for (int i = 0; i < numParticles; i++) {
            double angle = (Math.PI / 4 + Math.PI / 2) - Math.PI * i / numParticles;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            // Rotate around Y axis
            double x1 = x * Math.cos(yaw);
            double z1 = x * Math.sin(yaw);

            center.getWorld().spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    center.getX() + x1,
                    center.getY() + y,
                    center.getZ() + z1,
                    2,
                    offset,
                    0,
                    offset,
                    1.0,
                    new org.bukkit.Particle.DustOptions(color, 1f)
            );
        }
    }

    public void drawSemicircleBeautifully(Player player, double radius, int numParticles) {
        int chunkSize = 3;

        Location center = player.getLocation();

        center.add(0, 1, 0);
        double yaw = Math.toRadians(center.getYaw()) + Math.PI / 2;

        int particleChunkSize = numParticles / chunkSize;

        for (int i = 1; i <= chunkSize; i++) {
            new ParticleChunkSpawnRunnable(
                    center,
                    i,
                    particleChunkSize,
                    numParticles,
                    radius,
                    yaw
            ).runTaskLater(Plugin.getPlugin(), i);
        }
    }

    public void drawVerticalQuarterCircle(Player player, double radius, int numParticles) {
        Location center = player.getLocation();
        double yaw = Math.toRadians(center.getYaw()) + Math.PI / 2;

        numParticles++;

        for (int i = 1; i < numParticles; i++) {
            double angle = Math.PI / 4 - Math.PI / 2 * i / numParticles;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            // Rotate around Y axis
            double x1 = x * Math.cos(yaw);
            double z1 = x * Math.sin(yaw);

            center.getWorld().spawnParticle(
                    org.bukkit.Particle.REDSTONE,
                    new Location(
                            center.getWorld(),
                            center.getX() + x1,
                            center.getY() + y,
                            center.getZ() + z1
                    ),
                    0,
                    new org.bukkit.Particle.DustOptions(Color.BLUE, 1f)
            );
        }
    }

    private void sendParticles(Location location, Player player, double x, double y, double z, Color color) {
        location.getWorld().spawnParticle(
                org.bukkit.Particle.REDSTONE,
                (List<Player>) location.getNearbyPlayers(15),
                player,
                x,
                y,
                z,
                0,
                0,
                0,
                0,
                1.0,
                new org.bukkit.Particle.DustOptions(color, 1f)
        );
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
        }.runTaskTimer(Plugin.getPlugin(), 0l, period);
    }
}
