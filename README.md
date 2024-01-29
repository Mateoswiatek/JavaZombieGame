## Zombie Shooter Project

# Description

The Zombie Shooter project is a simple game where the player's task is to shoot moving zombies.
The game keeps track of the number of hit enemies and calculates accuracy. The closest enemy is the one eliminated.

The main idea behind the project was to apply various design patterns and **SOLID** principles in practice.
Notably, Factory, Observer, and Singleton patterns were employed, and interfaces were used to reduce dependencies.
This approach makes the project easy to understand, and expansion does not require extensive modifications.

The SOLID principles and design patterns utilized include:
- **Factory Pattern**: Applied for creating instances of game entities, promoting object creation flexibility.
- **Observer Pattern**: Implemented to allow certain game elements to react to events, enhancing modularity.
- **Singleton Pattern**: Utilized in specific instances where a single global instance is preferred.
- **Interfaces**: Introduced to define contracts, reducing coupling between components.
- Other principles

This approach results in a project that is not only comprehensible but also easily extensible without significant code changes.

# Program Structure

The program is constructed with the ZombieFactory (Singleton, Factory, Interface), which can be easily replaced with factories for other characters.
An object responsible for sounds is created (likely to be eventually incorporated into the Gun class).
A crosshair is also created (as a mouse, changing its icon), utilizing the Observer pattern.
A single global list, spiritesList, is passed, to which the factory produces zombies. In the future, this may be generalized to a dictionary (Type of spirit: List).
At regular intervals, a new zombie is generated, the smaller size simulating a greater distance from the player.
Relevant classes listen for the shooting event and modify the content accordingly.
Thanks to the application of design patterns and SOLID principles, the application's development is straightforward and enjoyable.

# Further Possible Development
- **Gun Interface Creation**: Develop an interface for Gun, encompassing different types of weapons with information about sounds, crosshairs, ammunition, reload time, damage dealt, and displayed icons.
- **Weapon Inventory Bar**: Include a bar displaying available weapons.
- **Weapon Reload Feature**: Add a reloading mechanism for weapons.
- **Inclusion of Special Weapon Abilities**: Integrate unique abilities for weapons; for instance, a sniper rifle capable of eliminating all entities in its path or another weapon with area-of-effect capabilities.
- **Addition of Zombie Health Bar**: Incorporate a health bar (HP) for zombies.
- **Visual and Audio Effects**: Enhance the gaming experience by adding visual and audio effects corresponding to the special abilities of each weapon.
- **User Interface (UI) Indicators**: Provide clear UI indicators or notifications to inform players about the activation and cooldown of special weapon abilities.
- **Introduction of Other Enemies**: Expand the variety of adversaries.
- **Plot Implementation**: Add a storyline to the game.
- **Death Effect for Zombies**: Integrate a death effect for zombies.
