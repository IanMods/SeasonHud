## [2.0.0] - 2026-02-27

### Changed

- Large rewrite to hopefully make porting easier
- (1.21+ Forge) Updated to use the default Forge overlay rendering, now that it actually exists
- Biome fertility now only shows when it differs from the biome's default fertility (and is enabled in the config)

### Added

- Minimap Mods:
    - Added Xaero's Better PVP support
- Season Mods:
    - Added Homeostatic Seasons support
    - Added ProtoManly's Weather support
- Added sub-season support for:
    1. Fabric Seasons
        - Fabric Season's default season length is 28, which is not evenly divisible to 3 sub-seasons
            - The ingame config button is disabled if that is detected
                - Also added a tooltip to suggest changing it to a number divisible by 3
            - Also added a warning to the "showSubSeason" option in the config file
    2. Ecliptic Seasons
- Added a config option to replace the season name with the fertility value, when it differs from the biome's default
- Added a server config option to override the Serene Seasons sub-season length used in the date calculations
    - Used if the server owner changes the length, but the clients don't have the updated Serene Season config.

