package io.github.aaronr92.auroraproject;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleChunkSpawnRunnable extends BukkitRunnable {

    private final Location center;
    private final int i;
    private final int particleChunkSize;
    private final int numParticles;
    private final double radius;
    private final double yaw;

    public ParticleChunkSpawnRunnable(Location center, int i, int particleChunkSize, int numParticles, double radius, double yaw) {
        this.center = center;
        this.i = i;
        this.particleChunkSize = particleChunkSize;
        this.numParticles = numParticles;
        this.radius = radius;
        this.yaw = yaw;
    }

    @Override
    public void run() {
        for (int j = (i - 1) * particleChunkSize; j < i * particleChunkSize; j++) {
            double angle = Math.PI / 2 - Math.PI * j / numParticles;
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
}
