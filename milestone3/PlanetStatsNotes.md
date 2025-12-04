# User Story: Planet-Specific Statistics Tracking

## Story
**As a** player  
**I want** to see statistics tracked individually for each planet  
**So that** I can track my progress and performance on different planets separately

## Description
This feature adds per-planet statistics tracking to Mindustry. Players can now see how many deaths, builds, units created, and other metrics they have accumulated on each individual planet. This allows players to:
- Track their progress on different planets independently
- Compare performance across different planets
- See cumulative statistics for each planet over time

## Implementation Details

### New Components

1. **PlanetStats Class** (`mindustry.game.PlanetStats`)
   - Tracks the following statistics per planet:
     - `playerDeaths`: Number of player unit deaths on this planet
     - `enemyUnitsDestroyed`: Number of enemy units destroyed
     - `wavesLasted`: Highest wave reached
     - `buildingsBuilt`: Total buildings constructed
     - `buildingsDeconstructed`: Total buildings deconstructed
     - `buildingsDestroyed`: Total buildings destroyed
     - `unitsCreated`: Total units created
     - `placedBlockCount`: Map tracking how many of each block type was placed

2. **Planet Class Modifications**
   - Added `PlanetStats stats` field to store planet-specific statistics
   - Added `saveStats()` method to persist statistics to disk
   - Added `loadStats()` method to load statistics from disk
   - Added `getStats()` method to get or create statistics instance
   - Statistics are automatically loaded when planet is initialized

3. **Event Tracking Integration**
   - Modified `Logic.java` to update planet stats when:
     - Buildings are built (`BlockBuildEndEvent`)
     - Buildings are destroyed (`BlockDestroyEvent`)
     - Units are created (`UnitCreateEvent`)
     - Units are destroyed (`UnitDestroyEvent`) - tracks both enemy kills and player deaths
     - Waves are completed (`GameOverEvent` and wave tracking)
   - Modified `Control.java` to update planet stats on wave completion
   - Statistics are automatically saved after each update

### Data Persistence
- Statistics are saved using `Core.settings.putJson()` with key format: `{planetName}-stats`
- Statistics are loaded automatically when planets are initialized
- Data persists across game sessions

## Acceptance Criteria

- [x] Planet statistics are tracked separately for each planet
- [x] Player deaths are tracked when player units die on a planet
- [x] Building statistics (built, destroyed, deconstructed) are tracked per planet
- [x] Unit statistics (created, enemy units destroyed) are tracked per planet
- [x] Wave statistics (highest wave reached) are tracked per planet
- [x] Statistics persist across game sessions
- [x] Statistics are automatically updated during gameplay
- [x] Each planet maintains its own independent statistics

## Technical Notes

### Statistics Tracked
- **Deaths**: Tracked when a player unit (checked via `isPlayer()`) is destroyed
- **Builds**: Tracked when buildings are completed (not just started)
- **Destructions**: Tracked when friendly buildings are destroyed
- **Deconstructions**: Tracked when buildings are manually deconstructed
- **Units Created**: Tracked when units are created for the default team
- **Enemy Units Destroyed**: Tracked when enemy units are destroyed
- **Waves Lasted**: Tracks the highest wave reached (not cumulative)

### Performance Considerations
- Statistics are saved after each update, which may cause frequent disk writes
- Consider batching saves or saving less frequently if performance becomes an issue
- Statistics are stored as JSON, which is human-readable but may be slower than binary formats

## Future Enhancements (Out of Scope)
- UI to display planet statistics in the planet selection dialog
- Statistics comparison view between planets
- Export/import statistics functionality
- Statistics reset functionality
- Additional metrics (time played, resources gathered, etc.)

## Files Modified
- `core/src/mindustry/game/PlanetStats.java` (new file)
- `core/src/mindustry/type/Planet.java` (modified)
- `core/src/mindustry/core/Logic.java` (modified)
- `core/src/mindustry/core/Control.java` (modified)

## Testing Recommendations
1. Play on multiple planets and verify statistics are tracked separately
2. Verify statistics persist after closing and reopening the game
3. Test that player deaths are correctly tracked
4. Test that building statistics increment correctly
5. Test that wave statistics track the highest wave reached
6. Verify statistics are not lost when switching between planets

