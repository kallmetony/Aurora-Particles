package com.aaronr92.auroraproject.listener;

import com.aaronr92.auroraproject.handler.ParticleLoader;
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
            Location loc = player.getLocation();
            particleLoader.sendParticleToAllPlayers(
                    new com.aaronr92.auroraproject.model.Particle(
                            Particle.DRIP_LAVA,
                            loc.getBlockX(),
                            loc.getY(),
                            loc.getZ(),
                            Integer.parseInt(args[0])
                    )
            );
        }
        return true;
    }
}
