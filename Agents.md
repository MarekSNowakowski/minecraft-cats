# AGENTS.md — Meowdifications Development Handbook & Agent Guidelines

Welcome to the **Meowdifications** repository. This document serves as the canonical technical guide and operating manual for autonomous agents and human developers contributing to this Fabric mod.

---

## 1. Project Vision & Overview

**Meowdifications** transforms Minecraft cats from passive, generic pets into lively, charismatic, and interactive feline companions. The mod focuses on four pillars:
1. **Cozy Cat Beds & Elevated Perches**: Dedicated sleeping and resting spaces tailored for cats.
2. **Scratching Posts & Cat Trees**: Functional furniture that satisfies natural feline scratching urges.
3. **Functional Litter Boxes**: Cleanable and maintainable hygiene amenities.
4. **Interactive Toys & Enriched Behaviors**: Physical toys and rewritten AI routines that make cats play, stalk, pounce, nap, knead, and bond with the player.

### Core Design Principles
- **Vanilla-Plus Aesthetics**: All textures, voxel models, sounds, and mechanics must look and feel organic within modern Minecraft.
- **Behavioral Depth over Pure Utility**: Focus on feline personality, warmth, and curious habits rather than turning cats into overpowered utility tools.
- **Mod Compatibility & Performance**: Use clean Fabric mixins, non-destructive AI goal injection into `net.minecraft.world.entity.animal.Cat`, and standard event hooks so that third-party mob and creature mods remain fully compatible.

---

## 2. Technical Stack & Environment

- **Target Minecraft Version**: `26.3`
- **Mod Loader**: Fabric (`fabricloader >= 0.19.5`)
- **Fabric Loom**: `1.18-SNAPSHOT` (with split environment source sets: `main` and `client`)
- **Java Version**: **JDK 25** (Utilize modern Java features: pattern matching, sealed types, records, collection factories)
- **Mappings**: Mojang Official Mappings (standard in modern Fabric Loom)
- **Mod ID**: `meowdifications`
- **Base Package**: `com.keroen.meowdifications`
- **Data Generation**: Fabric Data Generation API (`FabricDataGenerator`)

---

## 3. Directory & Package Conventions

```
src/
├── main/
│   ├── java/com/keroen/meowdifications/
│   │   ├── Meowdifications.java         # ModInitializer, logger, ID helper
│   │   ├── block/                       # CatBedBlock, WallPerchBlock, ScratchingPostBlock, LitterBoxBlock
│   │   ├── block/entity/                # BlockEntities for beds, litter boxes, scratchers
│   │   ├── item/                        # FeatherWandItem, YarnBallItem, CatnipItem, CatnipMouseItem
│   │   ├── entity/                      # YarnBallEntity, ToyMouseEntity
│   │   ├── entity/ai/                   # AI Goals, sensors, and memory modules
│   │   ├── registry/                    # ModBlocks, ModItems, ModBlockEntities, ModEntities, ModSoundEvents
│   │   ├── sound/                       # Sound definitions & sound event references
│   │   └── mixin/                       # Non-destructive mixins (CatMixin)
│   └── resources/
│       ├── fabric.mod.json              # Mod metadata
│       └── meowdifications.mixins.json  # Common mixins
├── client/
│   ├── java/com/keroen/meowdifications/client/
│   │   ├── MeowdificationsClient.java   # ClientModInitializer, renderers, particle factories
│   │   ├── MeowdificationsDataGenerator.java
│   │   ├── datagen/                     # Data providers (ModelProvider, RecipeProvider, LootTableProvider)
│   │   ├── render/                      # Entity and BlockEntity renderers
│   │   └── mixin/                       # Client mixins (CatRendererMixin, CatModelMixin)
│   └── resources/
│       └── meowdifications.client.mixins.json
```

---

## 4. Key Systems & Implementation Guide

### 4.1. Cat Beds & Wall Perches (`com.keroen.meowdifications.block`)

#### A. Dedicated Cat Beds (`CatBedBlock`)
- **Visuals**: Cushioned pet beds, woven baskets, and padded sleeping mats available in various dye colors or wood variants.
- **Voxel Shape & Nesting**: Bounded voxel shape designed to fit a curled-up cat entity comfortably.
- **Behavior**:
  - Tamed and stray cats in an 8–16 block radius evaluate nearby unoccupied cat beds when tired or idle.
  - Cats curl up, sleep, and purr while in the bed.
  - Upon waking, cats stretch and may knead their paws ("making biscuits") on the soft cushion.

#### B. Wall Perches & Shelves (`WallPerchBlock`)
- **Visuals**: Wooden shelves and carpeted wall-mounted perches that attach to wall blocks.
- **Behavior**:
  - Allows cats to pathfind vertically to high lookout spots in player bases.
  - Cats enjoy sitting on elevated perches to look down at their surroundings.

---

### 4.2. Scratching Posts & Cat Trees (`com.keroen.meowdifications.block`)

#### A. Scratching Posts & Boards (`ScratchingPostBlock`, `CardboardScratcherBlock`)
- **Crafting**: Wood logs/planks wrapped in string/sisal, or crafted paper/cardboard scratching pads.
- **Multi-Level Cat Trees**: Modular or multi-block posts with platforms allowing both scratching and perching.
- **Behavior**:
  - Cats build up an autonomous "scratching urge" over time.
  - When the urge reaches a threshold, the cat seeks the nearest scratching post within range.
  - While scratching, the cat faces the post, plays a clawing animation, emits scratching sounds (`block.meowdifications.scratcher.scratch`), and releases wood/fiber particles.
  - Satisfying the urge prevents the cat from scratching at player wooden doors or furniture, and grants a short contentment buff (soft purring).

---

### 4.3. Functional Litter Boxes (`com.keroen.meowdifications.block`)

#### A. Litter Box (`LitterBoxBlock`)
- **Block States**:
  - `DIRTY` (boolean: false = clean, true = dirty)
  - `FILL_TYPE` (enum: EMPTY, SAND, LITTER)
- **Behavior**:
  - Players place the box and fill it using sand or crafted absorbent cat litter.
  - Cats autonomously recognize clean, filled litter boxes when their biological routine triggers.
  - The cat steps into the box, plays a digging animation, emits digging sounds, and the box transitions to `DIRTY = true`.
  - Players can clean a dirty box using a shovel or scoop item:
    - Restores the box to clean state (or consumes litter depending on balance).
    - Drops organic compost/fertilizer that acts as bone meal for crops.

---

### 4.4. Interactive Toys & Play

#### A. Yarn Ball (`com.keroen.meowdifications.entity.YarnBallEntity`, `YarnBallItem`)
- **Physics**: A lightweight physics entity that rolls and bounces lightly across blocks with gentle friction.
- **Interactions**:
  - Players can drop or toss the yarn ball by right-clicking.
  - Nearby cats spot the rolling ball, stalk forward in a low crouch, wiggle their hindquarters, and pounce.
  - Pouncing bats the yarn ball forward a short distance, keeping the chase alive.

#### B. Feather Wand (`com.keroen.meowdifications.item.FeatherWandItem`)
- **Interactive Play Tool**:
  - Held and used by the player (right-click / active wave).
  - Waving the wand signals nearby tamed cats to enter playful engagement.
  - Cats lock attention onto the feather tip, perform curious head tilts, crouch, and perform joyful leaps toward the wand.

#### C. Catnip Herb & Catnip Mouse (`CatnipCropBlock`, `CatnipItem`, `CatnipMouseItem`)
- **Catnip Plant**: Growable herb crop planted on farmland.
- **Catnip Mouse**: Stuffed craftable toy. Interacting with cats triggers short bouts of playful "zoomies", floor rolling, and blissful purring.

---

### 4.5. Enriched Cat AI & Player Interaction

Inject non-destructively into `net.minecraft.world.entity.animal.Cat` via mixin (`CatMixin`):
- `UseCatBedGoal`: Priority ~6. Navigates to unoccupied cat beds or wall perches to nap and relax.
- `UseScratcherGoal`: Priority ~7. Evaluates nearby scratching posts, paths to them, and plays clawing animation.
- `UseLitterBoxGoal`: Priority ~7. Periodically navigates to clean, filled litter boxes.
- `PlayWithToyGoal`: Priority ~5. Stalks, chases, and bats `YarnBallEntity` or interacts with a waving `FeatherWandItem`.
- `CatKneadGoal`: Performs kneading ("making biscuits") on wool, beds, or soft cushions when resting and happy.
- `CatAffectionGoal`: Headbutts, rubs against the owner's legs, and purrs warmly when the player is nearby.

#### Affection & Bonding System
- Cats maintain an internal affection rating toward their owner.
- Keeping clean litter boxes, providing cozy beds, offering scratchers, and engaging in regular toy play builds affection.
- High affection unlocks warm greetings, louder purrs, closer following, and morning gifts when waking up together.

---

## 5. Development & Coding Guidelines

### 5.1. Mojang Mappings & Clean Code
- Modern Loom uses **Mojang mappings**. Use standard Mojang names (`Identifier`, `BlockPos`, `Level`, `Cat`, `Player`, `InteractionResult`, etc.).
- Mod ID constant is located at `Meowdifications.MOD_ID`.
- Identifier helper: `Meowdifications.id("path")`.

### 5.2. Mixin Best Practices
- Never use `@Overwrite` unless completely unavoidable.
- Prefer `@Inject` (with `cancellable = true` when altering returns) or `@WrapOperation`.
- Keep injection points documented with clear comments explaining why the injection is required.
- Place entity mixins in `com.keroen.meowdifications.mixin` and client mixins in `com.keroen.meowdifications.client.mixin`.

### 5.3. Data Generation
- Do not manually construct duplicate JSON files for block models, item models, recipes, or blockstates.
- Implement data generators under `com.keroen.meowdifications.client.datagen` and register them in `MeowdificationsDataGenerator`.
- Run data generation with `./gradlew runDatagen`.

### 5.4. Localization & Keys
- All translation keys follow standard namespaces:
  - `block.meowdifications.<name>`
  - `item.meowdifications.<name>`
  - `entity.meowdifications.<name>`
  - `subtitles.meowdifications.<name>`
- English (`en_us.json`) is the baseline locale and must always be up-to-date.
- For `BlockItem`s, ensure `.useBlockDescriptionPrefix()` is invoked on `Item.Properties` so `block.meowdifications.<name>` matches, and supply both `block.` and `item.` keys in `en_us.json` for resilience.

### 5.5. Minecraft 26.3 API Specifics & Architecture
- **Feline Package**: Vanilla `Cat` is located in `net.minecraft.world.entity.animal.feline.Cat`.
- **Mandatory `setId` on Registrations**: Both `BlockBehaviour.Properties` and `Item.Properties` require explicit `.setId(ResourceKey)` before instantiation:
  ```java
  ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Meowdifications.id(name));
  T block = factory.apply(properties.setId(blockKey));
  ```
  Failing to set `id` results in runtime `NullPointerException: Block id not set` or `itemIdOrThrow` exceptions.
- **Item Definitions (`assets/<namespace>/items/<item>.json`)**:
  - Minecraft 26.3 uses item definition files in `items/` rather than relying solely on `models/item/`.
  - Every item (including `BlockItem`s) must have an item definition:
    ```json
    { "model": { "type": "minecraft:model", "model": "meowdifications:item/<item>" } }
    ```
    Without this, items appear as black-and-pink missing texture squares in inventories.
- **Block Models & GUI Lighting**:
  - Custom voxel block models must declare `"parent": "minecraft:block/block"` to inherit isometric GUI transforms, lighting, and first/third-person handheld positions.
- **Entity Interaction Signature**:
  - `Entity.interact(Player player, InteractionHand hand, Vec3 location)` takes a 3D hit vector `location`.
  - Save/Load logic uses modern `addAdditionalSaveData(ValueOutput)` and `readAdditionalSaveData(ValueInput)`.
  - Spawning drops from entities requires passing `ServerLevel`: `this.spawnAtLocation(serverLevel, stack)`.
- **Recipe Format (26.3)**:
  - Shaped `key`: `"X": "minecraft:item_id"`, `"Y": "#minecraft:tag_id"` (raw strings, `#` prefix for tags; legacy `{"item": "..."}` objects will fail datapack parsing).
  - Shapeless `ingredients`: `[ "minecraft:item_id", "#minecraft:tag_id" ]`.
  - Recipe results use `"id"` and `"count"`: `{ "id": "meowdifications:cat_bed", "count": 1 }`.
- **Advancement Trigger Conditions**:
  - Predicates for entity matching use registry keys in `BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE`. The key for matching entity types is `"minecraft:entity_type"`:
    ```json
    "predicate": { "minecraft:entity_type": "minecraft:cat" }
    ```

### 5.6. Build Environment & Windows Quirks
- **JDK 25**: Requires JDK 25 (`C:\Program Files\Java\jdk-25.0.4.1`). Always prefix Gradle executions with `$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.4.1"`.
- **Windows Certificate Store (SSL)**:
  - Piston/Mojang asset downloads (`downloadAssets`) on Windows can fail with PKIX path building errors unless Java uses the Windows Certificate Authority store.
  - Handled conditionally in `settings.gradle` by checking `System.getProperty("os.name")` so that Linux CI runners (GitHub Actions) remain unaffected and use their standard keystore without errors.
- **Loom & Datagen Duplicates**:
  - `src/main/generated` is automatically added as a resource root by Fabric Loom.
  - To prevent duplicates conflicts during `processResources` and `sourcesJar`, configure `tasks.withType(AbstractCopyTask).configureEach { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }` in `build.gradle`.
- **Git Ignore**:
  - Keep `src/main/generated/.cache/` ignored while committing generated assets and data.

---

## 6. Phased Implementation Roadmap

1. **Phase 1: Furniture Foundations & Registries**
   - Registries for blocks, items, block entities, and sounds.
   - `CatBedBlock` and `WallPerchBlock` (models, voxel shapes, block states).
   - `ScratchingPostBlock` and `CardboardScratcherBlock`.
   - `LitterBoxBlock` (clean, dirty, sand/litter states).

2. **Phase 2: Core Cat AI Routines**
   - Non-destructive `CatMixin` to inject custom goals into vanilla `Cat`.
   - Implement `UseCatBedGoal`, `UseScratcherGoal`, and `UseLitterBoxGoal`.
   - Add scratching and digging sounds and particle effects.

3. **Phase 3: Interactive Toys & Physics**
   - Implement `YarnBallEntity` with realistic rolling and batting physics.
   - Implement `FeatherWandItem` and cat play-response goal (`PlayWithToyGoal`).
   - Implement `CatnipCropBlock` and `CatnipMouseItem` with zoomies behavior.

4. **Phase 4: Player Bonding & Polish**
   - `CatAffectionGoal`: Leg-nuzzling, headbutting, and greeting behaviors.
   - Kneading ("making biscuits") animation and soft purring sounds.
   - Custom advancements (e.g., *"Professional Purr-vider"*, *"Making Biscuits"*).
   - Data generation for models, blockstates, recipes, tags, and localization.
