# PimorSet P0 Integration Status

This document records what the PimorSet P0 integration example proves today. It separates automated evidence from manual gameplay checks so later work does not treat declaration smoke tests as a finished playable loop.

## Automated Coverage

`PimorSetRuntimeSliceTest` proves that the `gravity_lift` example consumes the public P0 APIs from the Pi stack:

- PiKey: builds a typed active-skill input intent packet.
- PiEngine: exposes a typed actor, ability type, effect type, cooldown, and resource key.
- PiDataGraph: links the ability to `pimorset:gravity_lift`.
- PiDamage: builds the impact damage request for `pimorset:set_burst`.
- PiNet: chooses a tracking-entity sync scope.
- PiRenderRuntime: emits a scene frame with a semantic cue.
- PiRig: resolves the `right_hand` socket.
- PiAvatar: declares an upper-body cast cue and player anchor.
- PiUI: exposes cooldown and capture progress HUD state.
- PiContent: declares the storm staff, storm altar block, and storm altar block entity datagen bundle.

`PimorSetGravityLiftGraphTest` proves that `src/main/resources/data/pimorset/action/gravity_lift.json` carries the editable datapack sequence:

- select a nearby living target in a cone
- filter self and non-capturable targets
- capture the nearest target at the `right_hand` socket
- wait 46 ticks
- throw forward
- request impact damage
- emit beam and camera impulse cues

## Current Boundary

The P0 example is a public API and datapack-data closure. It does not yet register a real Forge staff item, altar block, block entity, or in-world gameplay handler for `gravity_lift`.

The current code intentionally avoids application-layer references to raw network buffers, generated helper APIs, old PiAttack/PiCombat names, and renderer internals. Client access remains isolated to explicit client packages.

## Manual Gap

The `runClient` smoke check from the global P0 plan still needs a playable registration layer before it can prove the full gameplay loop. The remaining manual criteria are:

- a player triggers the ability in-game
- the server selects a target
- damage trace is produced
- capture starts and ends
- throw applies impact damage
- the client sees a visible cue
- HUD feedback appears during cooldown or failure states

Until that layer exists, `./gradlew runClient` can verify startup but cannot prove the full P0 gameplay loop.
