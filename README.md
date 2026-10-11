# Meowdifications 🐾

**Meowdifications** is a Fabric mod for modern Minecraft focused on enriching feline companions. Transform Minecraft cats into playful, cozy, and lively pets by introducing functional furniture, interactive toys, and deep behavioral enhancements.

---

## ✨ Features Overview

### 🛏️ Cozy Cat Beds & Elevated Perches
- **Dedicated Cat Beds**: Cushioned pet beds, baskets, and sleeping pads designed specifically for cats to curl up and sleep in.
- **Wall Perches & Climbing Shelves**: Elevated platforms placed on walls, allowing cats to pathfind to high vantage points and observe their surroundings.
- **Lounging & Kneading**: Cats naturally seek out beds and soft perches to nap, stretch, and happily knead their paws ("making biscuits").

### 🪵 Scratching Posts & Cat Trees
- **Scratching Posts & Boards**: Crafted from wood, wool, and string. Includes sisal scratching posts, cardboard scratching pads, and multi-tiered cat trees.
- **Natural Scratching Urge**: Cats autonomously seek out scratching posts to sharpen their claws, preventing destructive scratching on furniture and wooden blocks.
- **Immersive Feedback**: Features scratch animations, satisfying clawing sounds, and subtle wood fiber particles.

### 📦 Functional Litter Boxes
- **Litter Boxes**: Placed furniture blocks fillable with sand or crafted absorbent cat litter.
- **Autonomous Habits**: Cats periodically visit clean litter boxes, displaying digging animations and sound effects before changing the box to a dirty state.
- **Maintenance & Cleaning**: Players can scoop dirty litter boxes to maintain hygiene, yielding compostable organic waste that functions as crop fertilizer.

### 🧶 Interactive Toys & Play
- **Yarn Balls**: Lightweight, physics-responsive toys that roll across the ground. Cats will stalk, crouch, swat, and kick yarn balls across rooms.
- **Feather Wands**: Interactive play items wielded by the player. Holding or waving the wand prompts nearby cats to enter play mode, wiggle their hindquarters, and leap toward the feather.
- **Catnip Plants & Mice**: Growable catnip herbs crafted into stuffed catnip mice that trigger joyful bouts of rolling and playful zoomies.

### 🧠 Enriched Feline AI & Player Interactions
- **Autonomous Daily Routines**: Cats actively prioritize cozy spots, scratchers, and clean litter boxes throughout their day.
- **Dynamic Play Routines**: Reactive hunting and pouncing sequences triggered by moving toys and player interactions.
- **Affection & Bonding**: Regular play, comfort, and care build affection. Happy, well-cared-for cats will rub against player legs, headbutt affectionately, and purr warmly.

---

## 🛠️ Requirements & Development Setup

### System Requirements
- **Minecraft**: `26.3`
- **Fabric Loader**: `>= 0.19.5`
- **Fabric API**: Latest compatible for Minecraft 26.3
- **Java**: JDK 25+

### Building from Source

```bash
# Clone the repository
git clone https://github.com/keroen/meowdifications.git
cd meowdifications

# Build the project (creates mod jar in build/libs/)
./gradlew build

# Launch Minecraft client in development environment
./gradlew runClient

# Run Fabric Data Generation
./gradlew runDatagen
```

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/keroen/meowdifications/
│   │   ├── Meowdifications.java         # Main mod entrypoint
│   │   ├── block/                       # Beds, perches, scratchers, litter boxes
│   │   ├── item/                        # Toys (yarn ball, feather wand, catnip)
│   │   ├── entity/                      # Toy entities (YarnBallEntity)
│   │   ├── entity/ai/                   # Custom cat AI goals and behaviors
│   │   ├── registry/                    # Blocks, items, entities, sounds
│   │   └── mixin/                       # Vanilla Cat injection points
│   └── resources/
│       ├── fabric.mod.json              # Mod metadata
│       └── meowdifications.mixins.json  # Mixin configuration
├── client/
│   ├── java/com/keroen/meowdifications/client/
│   │   ├── MeowdificationsClient.java   # Client entrypoint & renderers
│   │   └── MeowdificationsDataGenerator.java
│   └── resources/
│       └── meowdifications.client.mixins.json
```

---

## 📄 License

This mod is published under the [MIT License](LICENSE).
