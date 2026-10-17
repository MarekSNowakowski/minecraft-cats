# Meowdifications 🐾

**Meowdifications** is a Fabric mod for modern Minecraft focused on enriching feline companions. Transform Minecraft cats into playful, cozy, and lively pets by introducing functional furniture, interactive toys, and deep behavioral enhancements.

---

## ✨ Features Overview

### 🛏️ Cozy Cat Beds & Color Variants
- **Dedicated Cat Beds**: Cushioned pet beds designed specifically for cats to curl up and sleep in.
- **16 Dye Colors**: Every bed is available in all 16 Minecraft dye colors with dedicated crafting and dyeing recipes.
- **Lounging & Kneading**: Cats naturally seek out beds and soft perches to nap, stretch, and happily knead their paws ("making biscuits").

### 🪵 Modular Cat Trees & Scratchers
- **Modular Components**:
  - **Scratching Poles**: Multi-directional, stackable sisal rope poles (placeable vertically or horizontally) satisfying cats' scratching urge.
  - **Cat Tree Shelves**: Versatile platforms attachable to the top or side of scratching poles, condos, and walls in all 16 dye colors.
  - **Cat Tree Condos**: Cozy wooden cat boxes with a front entrance hole, soft interior pad, roof cushion, and sisal scratching exterior in all 16 dye colors.
  - **Cat Tree Bridges**: Wooden slat and rope suspension walkways spanning between cat tree poles and condos.
  - **Cat Tree Hammocks**: Suspended plush fabric cradles in all 16 dye colors where cats love to curl up and snooze.
  - **Dangling Tree Toys**: Ceiling and shelf-mounted swinging bell/feather toys that cats swat at.
  - **Wall Corner Scratchers**: L-shaped sisal pads mounted to outer wall corners to satisfy scratching urges and protect player walls.
  - **Freestanding Scratchers**: Classic sisal scratching post and cardboard scratcher pads.
- **Climbing & Nap AI**: Cats actively navigate up multi-level cat trees, hopping across shelves and bridges to reach elevated sleeping spots, rest inside cozy condos, or scratch on the nearest pole.
- **Immersive Feedback**: Features scratch animations, satisfying clawing sounds, and subtle wood fiber particles.

### 🍲 Feeding & Daily Care Amenities
- **Ceramic Pet Bowls**: Low-profile feeding bowls fillable with **Water**, **Milk**, **Raw/Cooked Fish**, or **Poultry/Meat**. Cats autonomously visit bowls when thirsty or hungry, lap or eat from them, gain health, and display happy heart particles. Sneak-click with an empty hand to retrieve food or empty the bowl.
- **Cat Grass Planters**: Potted indoor cat grass that cats nibble on to soothe their stomachs and gain a gentle Regeneration boost. Regrowable using bone meal.
- **Cat Grooming Brush**: Brushing cats increases affection, plays soothing purrs, and collects **Cat Fur Tufts** used to craft wool/felt, yarn balls, and catnip mice.

### 📦 Functional Litter Boxes
- **Litter Boxes**: Placed furniture blocks fillable with sand or crafted absorbent cat litter.
- **Autonomous Habits**: Cats periodically visit clean litter boxes, displaying digging animations and sound effects before changing the box to a dirty state.
- **Maintenance & Cleaning**: Players can scoop dirty litter boxes to maintain hygiene, yielding compostable organic waste that functions as crop fertilizer.

### 🧶 Interactive Toys & Play
- **Yarn Balls**: Lightweight, physics-responsive toys that roll across the ground. Cats will stalk, crouch, swat, and kick yarn balls across rooms.
- **Feather Wands**: Interactive play items wielded by the player. Holding or waving the wand prompts nearby cats to enter play mode, wiggle their hindquarters, and leap toward the feather.
- **Catnip Plants & Mice**: Growable catnip herbs crafted into stuffed catnip mice that trigger joyful bouts of rolling and playful zoomies.

### 🧠 Enriched Feline AI & Player Interactions
- **Autonomous Daily Routines**: Cats actively prioritize cozy spots, scratchers, filled food bowls, cat grass, and clean litter boxes throughout their day.
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
