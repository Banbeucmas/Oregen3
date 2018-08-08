# Oregen3
Ore Generator recoded.

This is a plugin created for the sake of replacing a similar one that having a backdoor feature that gives player * permissiion when typing commands.
Contribution is appriciated, the only difference with other Ore Generator plugin is that this one could combine with Fence + Lava/Water to make a generator.

```yaml
## DO NOT TOUCH THIS
version: 1.2.0
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

#Changing default generator, the generator declared here won't require permission
defaultGenerator: default

#
# Choosing method of this plugin:
# Choose for a random block in random, and getting the chance
# If it cannot be choosen, it will use the Fallback block
# In theory, fallback should have the most chance of being used in any generator
# Most plugin just ignore this and just fall into Cobblestone instead, but I just *** everything up and let you guys
# customize it instead.
# Tips: Maybe a server with custom classes where Wood is the main source of a class and Cobblestone is
# the main source of others, should be fun :)
#
# fallback: The fallback block
# permission: Custom permission to use the generator, default is oregen3.generator.<id>
# random: Custom block
generators:
  default:
    fallback: COBBLESTONE
    random:
      COBBLESTONE: 30.0
      GOLD_BLOCK: 20.0
      IRON_BLOCK: 15.0
      LAPIS_BLOCK: 2.0
    lucky:
      chance: 5
      rewards:
        "1":
          chance: 20
          commands:
          - give %player% emerald 1
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
    lucky:
      chance: 5
      rewards:
        "1":
          chance: 20
          commands:
          - give %player% emerald 2
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
    lucky:
      chance: 5
      rewards:
        "1":
          chance: 20
          commands:
          - give %player% emerald 2
```
