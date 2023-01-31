package io.github.aaronr92.auroraproject.model;

public class Particle {
    private org.bukkit.Particle particle;
    private double x;
    private double y;
    private double z;
    private int count;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double size;

    private Particle() {}

    public Particle(
            org.bukkit.Particle particle,
            double x,
            double y,
            double z,
            int count,
            double offsetX,
            double offsetY,
            double offsetZ,
            double size
    ) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.size = size == 0 ? 1 : size;
    }

    public Particle(
            org.bukkit.Particle particle,
            double x,
            double y,
            double z,
            int count
    ) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
        this.size = 1;
    }

    public double getX() {
        return x;
    }

    public org.bukkit.Particle getParticle() {
        return particle;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getCount() {
        return count;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public double getSize() {
        return size;
    }
}