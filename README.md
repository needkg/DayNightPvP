# DayNightPvP

<div align="center">

![DayNightPvP Logo](https://www.spigotmc.org/data/resource_icons/102/102250.jpg?1653715145)

**Dynamic PvP Plugin for your Minecraft server!**

[Spigot](https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/) | 
[Modrinth](https://modrinth.com/plugin/daynightpvp) | 
[Discord](https://discord.gg/FpzhnnCN3H)

</div>

## 📝 Description

DayNightPvP is a plugin that adds a unique and thrilling PvP experience to your Minecraft server. With advanced features and an easy-to-use interface, you can create a dynamic PvP cycle, activated during the night and deactivated during the day.

## ✨ Features

- **🌙 Automatic PvP**: Forget the need to manually enable or disable PvP. DayNightPvP takes care of this automatically.
  
- **⚔️ Difficulty Adjustment**: Add an extra challenge by automatically adjusting game difficulty when day or night begins.
  
- **🌍 Multi-World Support**: Works seamlessly across different worlds, allowing specific settings for each one.
  
- **⚙️ Custom Configuration**: Personalize sounds and messages to notify players about PvP events.
  
- **🔌 Integrations**:
  - PlaceholderAPI: Display PvP status on Scoreboard or TAB
  - GriefPrevention: Configure protected areas during night PvP
  - Vault: Money rewards system in combat
  - WorldGuard: Define regions with permanent PvP

## 📌 Placeholders

```
%dnp_pvp_status_current_world% → PvP status in player's current world
%dnp_pvp_status_world:<worldName>% → PvP status of the specified world
```

## 🛠️ Commands

```
/dnp → Show all available commands
/dnp reload → Reload the plugin
/dnp addworld <worldName> → Add a world to configuration
/dnp delworld <worldName> → Remove a world from configuration
/dnp editworld <worldName> <setting> <value> → Edit specific world settings
/dnp lang <language> → Change plugin language
```

## 🚩 WorldGuard Flags

```
allow-pvp-on-day → Allows PvP during day in specific regions
```

## 👮 Permissions

```
dnp.admin → Access to /dnp command
dnp.losemoney.<1-100> → Percentage of money lost on death
dnp.bypass → Allows combat entry during day
dnp.immune → Makes player immune to PvP
```

## 📊 Metrics

[<img src="https://bstats.org/signatures/bukkit/daynightpvp.svg">](https://bstats.org/plugin/bukkit/DayNightPvP/19067/)

## 🤝 Support

Need help or have a suggestion? Join our community on [Discord](https://discord.needkg.com)!

## 📄 License

This project is licensed under the [MIT License](LICENSE).
