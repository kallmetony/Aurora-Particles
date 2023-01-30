# Aurora-Particles
#### Lightweight paper plugin.
Very simple particle spawning using config file. All you have to do is to specify particles type and location, and it will be spawned automatically
## Add particles
1. Navigate to plugins `config.yml`
2. You must specify `particle, period, x, y, z, count` values for particle to create:
* `particle` is value from [enum of particles](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html)
* `period` (default = 20) is ticks. Each `period` particle will be spawned again
* `x`, `y`, `z` is a coordinates
* `offset` (default = 0 for all offsets) particles will be spawned inside offset area
* `size` (default = 1) the size of a particle
#### Note that `offset`, `period` and `size` is optional to write
### Pattern for adding particles
```yaml
<some name>:
  particle: #Enum particle name
  period: #Time to spawn new particle
  x: #x coordinate
  y: #y coordinate
  z: #z coordinate
  count: #times to spawn particle
  offsetX: #offset
  offsetY: #offset
  offsetZ: #offset
  size: #size of one particle
```
## Setting up
1. Download plugin (paper or purpur) from releases
2. Write your values in `config.yml`
3. You are ready to go, launch your server