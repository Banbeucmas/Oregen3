# Oregen3
![](https://i.imgur.com/mbpigXo.png)
Ore Generator recoded.

This is a plugin created for the sake of replacing a similar one that having a backdoor feature that gives player * permissiion when typing commands.
Contribution is appriciated, the only difference with other Ore Generator plugin is that this one could combine with Fence + Lava/Water to make a generator.

# Configuratio File
```yaml
## DO NOT TOUCH THIS
version: 1.3.0
prefix: "&f[&6OreGen&e3&f]"

# Enabling which format should Oregen generate ore
# waterBlock: Block + Water
# lavaBlock: Lava + Block
# waterLava: Water + Lava
mode:
  waterBlock: true
  lavaBlock: false
  waterLava: true

#Compatible block depending on the mode
blocks:
- FENCE
- ACACIA_FENCE
- BIRCH_FENCE
- DARK_OAK_FENCE
- IRON_FENCE

#Disabled worlds
disabledWorlds:
- world
- ASkyBlock

#Enable Dependency handling
#If false, the plugin doesn't need ASkyblock - AcidIsland or any other dependency to works, but there will be
#no permision given to those generator and it will can only use the defaultGenerator instead
enableDependency: true

#Enable this will use the plugin custom choosing method. This is how it worked
#
# If true:
# - Choose for a random block in random, and getting the chance
# - If it cannot be choosen, it will use the Fallback block
# - In theory, fallback should have the most chance of being used in any generator
# - Most plugin just ignore this and just fall into Cobblestone instead, but I just *** everything up and let you guys
# customize it instead.
# - Tips: Maybe a server with custom classes where Wood is the main source of a class and Cobblestone is
# the main source of others, should be fun :)
# - This made the randomness chance of all of the block combined to be over 100% if you want, but with stricter chance
# 
# If false:
# - Choose the randomness like every other Ore Generator plugin 
# - Require all of the combination chance of the block to be equal 100% or smaller than 100%
# For example:
# If the generator contains stone and diamond blocks you set Stone to have 46% of spawning then diamond blocks should have 54% # chance or smaller
# - In case the total chance of all of the block isn't 100% then the fallback block will be chosen (sometimes)
#
# IF YOU DON'T UNDERSTAND WHAT THE HELL I SAID THEN JUST KEEP THIS FALSE ;)
randomFallback: false

#Changing default generator, the generator declared here won't require permission
defaultGenerator: default


# fallback: The fallback block
# permission: Custom permission to use the generator, default is oregen3.generator.<id>
# random: Custom block
# level: Minimum level required for island to upgrade its generator (Default is 0)
# priority: If the island owner have 2 generator permission, one with higher priority will be used as the generator (Default is 0)

generators:
  default:
    fallback: COBBLESTONE
    random:
      COBBLESTONE: 30.0
      GOLD_BLOCK: 20.0
      IRON_BLOCK: 15.0
      LAPIS_BLOCK: 2.0
  vip:
    fallback: COBBLESTONE
    permission: "test.vip"
    random:
      COBBLESTONE: 30.0
      GOLD_BLOCK: 20.0
      IRON_BLOCK: 15.0
      LAPIS_BLOCK: 2.0
      COAL_BLOCK: 5.0
      REDSTONE_BLOCK: 10.0
  mvp:
    fallback: COBBLESTONE
    random:
      COBBLESTONE: 30.0
      GOLD_BLOCK: 20.0
      IRON_BLOCK: 15.0
      LAPIS_BLOCK: 2.0
      COAL_BLOCK: 5.0
      REDSTONE_BLOCK: 10.0
      DIAMOND_BLOCK: 10.0
      EMERALD_BLOCK: 8.0
```
