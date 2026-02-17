## [2.0.0] - 2026-02-17

### Changed

- Large rewrite to hopefully make porting easier
- (1.21+ Forge) Updated to use the default Forge overlay rendering, now that it actually exists

### Added

- Added Homeostatic Seasons support
- Added sub-season support for Fabric Seasons
    - Fabric Season's default season length is 28, which is not evenly divisible to 3 sub-seasons
        - The ingame config button is disabled if that is detected
            - Also added a tooltip to suggest changing it to a number divisible by 3
        - Also added a warning to the "showSubSeason" option in the config file
- Added sub-season support for Ecliptic Seasons

