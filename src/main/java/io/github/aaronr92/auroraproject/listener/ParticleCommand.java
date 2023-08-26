package io.github.aaronr92.auroraproject.listener;

import io.github.aaronr92.auroraproject.handler.ParticleLoader;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                particleLoader.drawVerticalSemicircle(player, 2, Integer.parseInt(args[1]));
            }
        }
        return true;
    }
}
