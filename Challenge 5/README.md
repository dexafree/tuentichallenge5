# Challenge 5 - The One Treasure

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=5)

The most famous pirate on earth, G.D. Roger, told the world that he left his entire fortune and all the treasures he had collected on the last island he pillaged. Since he proclaimed that, pillagers and pirates from every country have started to follow the path in order to find the One Treasure!

You are one of them!
You have a map containing some known routes that G.D. Roger followed to collect his fortune. These routes connect islands.

Thanks to the Log Pose, you know that each route you take costs you some of the gold on your ship, and each island you arrive at may cost you some gold (if the island is well-defended by Marines, or other Pirates), or may boost your gold (if the island can be salvaged).

Starting from your island, your objective is to reach the last island, following the route that costs the least amount of gold. Don’t forget that you may end up richer, that is the point!

Other Pirates like you will travel this route to reach the One Treasure. You know from the start who they are, because they, like you, are also famous, but: They travel without penalties, although they don’t gain any gold when pillaging an island, that is, their initial gold doesn’t change. When you move to another island, they move to another island, but they don’t have any guide or choice, they follow a predefined path. They move after your move, and if any Pirate Ship lands on the island where you are, fighting them will cost you a huge amount of gold!

### Input

A graph of nodes, defined by:

* A line with the number of nodes “islands” = N (i.e: 50)
* N lines with the node name and the node cost, separated by a space. (i.e: Loguetown 23)
* A line with the number of routes = R (i.e: 120)
* R lines containing a pair of node names and a route cost, separated by spaces. (i.e: Dressrosa Zou 20)


The ship’s description, defined by:

* A line with the number of ships, including you = S (i.e: 4)
* S lines with the ship number, ship name, ship’s gold and starting island, separated by spaces. The first line is always your ship. (i.e: 1 Thousand\_Sunny 56 Water\_7)


### Rules

* When you take a route, subtract the route cost and node cost from your gold. The node cost may be negative.
* Take into account the positions of the other ships when making your choice.
* A Pirate never goes back! The ships can’t travel to an island where they have been previously.
* The routes between islands are unidirectional. In a route line, the first node is the origin and the second node is the destination.
* Subtract from your gold the sum of all the gold from the other ships that when they take a route end their movement on your node. Do not subtract this gold if you reached Raftel.
* Ships are moved following the ship number in order.
* After all ships have moved, if you have less than zero gold, you are instead considered to have zero gold.
* You can’t travel a route with zero gold.
* You can choose to pillage instead of taking a route, giving you 10 gold instead of subtracting the route and node cost. Each time you pillage, the other ships make their movement.
* Other ships do not add or subtract any gold from the routes and nodes they sail.
* The other odd numbered ships will always take the route to the next island with the minimum route cost. If there is more than one, choose the first following the input order. If there are zero, the ship skips its move.
* The other even numbered ships will always take the route to the next island with the maximum route cost. If there is more than one, choose the first following the input order. If there are zero, the ship skips its move.
* All paths end in Raftel.

### Output

The amount of gold on your ship when you reach the final island named “Raftel”, including that last island. The route you take must enable you to be the first to reach the island with the maximum gold possible.

### Limits

```
4 <= N <= 125
16 <= R <= 10000
3 <= S <= 64
```

### Example

#### INPUT

```
7
Alabasta 0
Baltigo 0
Cactus 5
Drum 10
Enies -5
Foolshout -5
Raftel -10
9
Alabasta Baltigo 10
Alabasta Cactus 5
Alabasta Drum 0
Baltigo Enies 0
Cactus Enies 5
Cactus Foolshout 10
Drum Foolshout 0
Enies Raftel 0
Foolshout Raftel 0
3
1 Going_Merry 31 Alabasta
2 Sexy_Foxy 20 Alabasta
3 Oro_Jackson 20 Alabasta
```

#### OUTPUT

```
11
```

### Explanation

The path taken is Alabasta,Cactus,Enies,Raftel

