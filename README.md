# Fun Game

## Local multiplayer PvP 2d platforming shooter

**What does this application do?**
The purpose of this project is to produce a two player 2d shooter. Each player will have the ability to reload (increase ammunition), shoot a pellet, and create a temporary shield which reflects incoming pellets. Along with this, implementation of various power-ups and abilities are being considered:
- Snake gun that allows the user to control the pellet that they shoot like the classic arcade game 'snake'
- Faster reload
- Range increase of pellets fired

**Who will use it?**
The program is designed for local multiplayer, where two people are able to play without internet, and enjoy a high-intensity quick match of skill.
**Why is this project of interest?**
With the evergrowing existance of online multiplayer battle royales and single-player open world games, the market for local co-op/PvP games has dwindled. My favourite games have always been local multiplayer games:
- Super Smash Bros.
- Mario Party
- Nidhogg
- Duck game
- Fifa

Because of my passion and liking of such games, I feel it is a good challenge and test of my programming ability to see if I can make a game of such a standard for me and my friends to honestly enjoy.

## User Stories
- As a user, I want to be able to have a player that moves and jumps
- As a user, I want to be able to have a player that can reload and shoot (add pellets to a list of pellets)
- As a user, I want to be able to have a second player with different controls
- As a user, I want to be able to win a game by hitting another player with a pellet

- As a user, I want to be able to choose a level to play in
- As a user, I want to be able to interact with the level chosen
- As a user, I want to be able to create a level
- As a user, I want to be able to save levels that I have made

## Phase 4: Task 2
Rigid Exception catching for NoSuchLevelNameException thrown in LevelBank Class in the 'findLevel' method.

## Phase 4: Task 3
- Change the 'Pellets' class to be of type iterable rather than holding a list of Pellets
- Delete the 'LevelBank' class and save each level as an individual file, this would mean changing detecting how levels are found but would allow for the deletion of an entire class
- Change the location of keyEvent data to one centralized class to increase cohesion
- Refactor Editor panel controls and tools to only include necesarry mouse events and action (originally left in for potential addition of actions)
