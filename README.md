# **Hytils**
An (mostly) all-in-one mod for hypixel and minecraft.

Features (WIP):
- Discord Rich Presence support
- Teammate Detection and HUD (Currently only suports games that use the "leather hat" system to differenciate teams.)
  - PVE games have all players registered as teammates
- On Screen HUD (Changed some icons on the HUD to be bar-like for easy reference, togglable)  
- Combat Card (Information about your attacker like health, armor, absorption)

* Expect gamemode-specific features soon!

***

### Installation
Note: This mod is still a heavy WIP. Do keep in mind that bugs are considerably common, so don't expect a bug-free experience.

1. Obtain the mod's JAR file
  This can be done by either just downloading the lastest release in the `releases` tab, or compiling the mod yourself by going under `Code -> Download ZIP`, Unzipping the file, and in the root folder (the folder that houses the `gradlew` file), open a terminal, and then typing `./gradlew build` in said terminal. Make sure you have Java 8 installed. Once the process is complete, the JAR file will be located in `Project root -> build -> libs -> Hytils-[VERSION].jar`
  
2. Installing the mod
  This mod requires [this JAR file](https://github.com/Vatuu/discord-rpc/releases/tag/1.6.2) to be installed along with the mod as it is a required library, failure to do so will cause the mod to crash on startup.
  
If this goes smoothly you should see "Hytils [VERSION]" in the game title instead of "Minecraft 1.8.9". Discord Rich Presence is also present with a custom status message on your Discord profile.
