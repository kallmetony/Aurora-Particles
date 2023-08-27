package io.github.aaronr92.auroraproject.listener;

import io.github.aaronr92.auroraproject.Plugin;
import io.github.aaronr92.auroraproject.handler.ParticleLoader;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ParticleCommand implements CommandExecutor {

    private final ParticleLoader particleLoader = ParticleLoader.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            System.out.println(args[0]);
            if (args[0].equals("simple")) {
                System.out.println("Drawing simple");
                particleLoader.drawCircle(player.getLocation(), 3, Integer.parseInt(args[1]));
            } else if (args[0].equals("rot")) {
                System.out.println("Drawing with rot");
                particleLoader.drawCircleWithRotation(
                        player.getLocation(),
                        2,
                        Integer.parseInt(args[1]),
                        Math.PI / 2, 0, 0
                );
            } else if (args[0].equals("vert")) {
                particleLoader.drawVerticalCircle(player, 2, Integer.parseInt(args[1]));
            } else if (args[0].equals("semi")) {
                long start = System.nanoTime();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        long start = System.nanoTime();
                        particleLoader.drawVerticalSemicircle(player, Color.fromRGB(0,191,255), 2, Integer.parseInt(args[1]), 0);
                        particleLoader.drawVerticalSemicircle(player, Color.WHITE, 2.2, Integer.parseInt(args[1]) / 2, .15);
                        System.out.println(System.nanoTime() - start);
                    }
                }.runTaskAsynchronously(Plugin.getPlugin());
                System.out.println("Done: " + (System.nanoTime() - start));
            } else if (args[0].equals("b")) {
                particleLoader.drawSemicircleBeautifully(player, 2, Integer.parseInt(args[1]));
            }
        }
        return true;
    }
}
