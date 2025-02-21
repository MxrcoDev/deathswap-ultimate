![header](https://capsule-render.vercel.app/api?type=waving&height=230&color=gradient&customColorList=7&text=Deathswap%20Ultimate&textBg=false&fontAlignY=40&reversal=false&animation=fadeIn)

![Minecraft](https://img.shields.io/badge/Minecraft-1.20-blue) ![Version](https://img.shields.io/badge/Version-1.0-green)

**DeathSwap Ultimate** is a Minecraft plugin that introduces the famous "DeathSwap" game mode. Players are randomly teleported into the world and must try to survive longer than their opponents. Get ready to set traps and outsmart other players to be the last one standing!

## ❗ dependencies
[![LuckPerms](https://img.shields.io/badge/Dependency-LuckPerms-blue?style=for-the-badge&logo=minecraft)](https://luckperms.net/)


## 🕹️ How It Works?
- Players start in random positions within the world.
- Every **minute**, they are randomly teleported to another player's position.
- During the time between teleports, players must gather resources and build traps.
- The last player alive wins the game.

## ⚙️ Configuration
The plugin is fully configurable! The configuration file allows you to:
- Set the **waiting time** before the game starts.
- Modify the **cooldown between swaps**.
- Configure the **waiting lobby**.
- Define **LuckPerms groups** for players and spectators.
- Choose the **default game mode**.

### 📄 Example `config.yml`
```yaml
start-cooldown: 15 # Seconds before the game starts
swap-cooldown: 15  # Time between swaps

lobby:
  world: world
  x: 0
  y: 64
  z: 0

default-game-mode: ADVENTURE

default-luckperms-group: default
spectator-luckperms-group: spectators
```

## 🔧 Commands
- `/deathswap setlobby` - Sets the waiting lobby *(Permission: `deathswap.admin`)*
- `/deathswap start` - Starts the game *(Permission: `deathswap.admin`)*
- `/teleport` - Teleport command *(Aliases: `/tp`, `/spect`, `/spectator`, etc.)* *(Permission: `deathswap.teleport`)*

## 🛠 Permissions
- `deathswap.admin` - Allows use of admin commands (`/deathswap`)
- `deathswap.teleport` - Allows use of the teleport command (`/teleport`)

## 📩 Installation
1. Download the **DeathSwap Ultimate** `.jar` file.
2. Upload it to your server's `plugins` folder.
3. Restart the server.
4. Configure the plugin by editing `config.yml` if needed.
5. Use `/deathswap setlobby` to set the lobby and `/deathswap start` to start playing!

## 🌏 Language
The plugin supports translations, and the default language is Italian. You can modify messages to adapt them to your preferred language in lang.yml.
