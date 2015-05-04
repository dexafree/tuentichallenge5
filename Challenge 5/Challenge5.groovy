import groovy.transform.CompileStatic

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * TUENTI CHALLENGE 2015
 * CHALLENGE 5
 * Dexafree
 */
@CompileStatic
class Challenge5 {

    public final static String END_ISLAND = "Raftel"

    // Map where we will store the islands, with key:value format (NAME:Island)
    Map<String, Island> islands = [:]

    // Ships that will be participating
    List<Ship> ships = []

    // List of List of Ships where we will store the position of every ship acording to the number of hops they've done
    List<List<Ship>> moves = []

    // Global variable that will store the best gold at end
    int bestGold = Integer.MIN_VALUE

    void execute(String input, String output){

        // Processes the text file
        loadFile(input)

        // Get the reference to our ship (First element)
        def me = ships.first()

        // Calculate the different positions of the other pirates
        calculateAI()

        // Starts the resolution
        solve(me, 0)


        println bestGold
        new File(output).withWriter { out ->
            out.println bestGold
        }

    }


    void solve(Ship me, int hops) {

        // Check if we are at Raftel
        if(me.island.name == END_ISLAND){
            println("Made the trip to Raftel with $me.gold")
            me.visited.each {println it.name}
            println("-----------------------")

            // If the amount of gold we've collected is better than the previous one, store it
            if(me.gold > 0 && me.gold > bestGold){
                bestGold = me.gold
            }

            // Exit from the recursion
            return
        }


        // If we have made as hops as the first pirate who arrives to Raftel, and we are not in Raftel yet
        // (the previous if would have evaluated true), exit from the recursion
        if(hops == moves.size()-1){
            return
        }

        // Check every possible route from the current island
        me.island.routes.each {

            // Create another ship in order not to modify the current one
            def travelShip = Ship.fromShip(me)
            def destination = it.destination

            // If we have not ran out of gold and we have not visited the island, GO!
            if(travelShip.gold > 0 && !travelShip.visited.contains(destination)){

                // Mark the island as visited
                travelShip.visited << destination

                // Substract the node cost and the route cost
                travelShip.gold -= destination.cost
                travelShip.gold -= it.cost

                // Change the current position to the destination one
                travelShip.island = destination

                // Check if other pirates have stolen us
                calculateFight(travelShip, hops)

                // Set the correct amount of gold (Check for negatives
                if(travelShip.gold < 0)
                    travelShip.gold = 0

                // Make another step
                solve(travelShip, hops + 1)

            }
        }

        // Check what would happen if we decided to pillage
        pillage(me, hops)

    }

    void calculateFight(Ship me, int hops){

        // Check if we are in a valid hop
        if (hops < moves.size()-1) {

            // For every pirate in that hop, check if they are at the same island that we are (and it's not Raftel)
            moves[hops+1].each {
                if(it.island == me.island && me.island != islands[END_ISLAND]){
                    //println("$it.name stole $it.gold at $it.island.name")
                    me.gold -= it.gold
                }
            }
        }
    }

    void pillage(Ship me, int hops){

        // Create another ship in order not to change the current instance
        def nShip = Ship.fromShip(me)

        // Save the current island (So it will appear another time when we print the route)
        nShip.visited << nShip.island

        // Increase the amount of gold
        nShip.gold += 10

        // Check if other pirates have stolen us
        calculateFight(nShip, hops)

        // Correct the amount of gold
        if(nShip.gold < 0)
            nShip.gold = 0

        // Make another step
        solve(nShip, hops+1)
    }


    void calculateAI() {

        // Stop condition
        boolean hasFinished = false

        // Get the list of ships at their initial state
        List<Ship> shipsAtStart = ships[1..<ships.size()]

        // Add the initial state to the moves list
        moves << shipsAtStart

        def hop = 0

        // Iterate while no one has arrived to Raftel
        while(!hasFinished){
            List<Ship> currentHop = []

            // Iterate for every boat
            (0..<shipsAtStart.size()).each {

                // Copy the ship so we can have it on different positions at different hops
                // It copies it from the last state
                def ship = Ship.fromShip(moves[hop].get(it))

                // Calculate which route will it take
                def routeToTake = findAIRoute(ship, ship.number % 2 == 0)

                // Mark the destination as visited, and move the ship to its destination
                ship.visited.add(routeToTake.destination)
                ship.island = routeToTake.destination

                // Add the current status to the current hop list
                currentHop << ship

                // Also, if it has arrived to Raftel, make the while stop
                if (routeToTake.destination == islands[END_ISLAND]) {
                    hasFinished = true
                }
            }

            moves << currentHop
            hop++
        }
    }

    Route findAIRoute(Ship ship, boolean mostExpensive){

        Route returnRoute = null

        ship.island.routes.each {
            if(returnRoute == null && !ship.visited.contains(it.destination)){
                returnRoute = it
            } else {

                if(!ship.visited.contains(it.destination)){
                    if(mostExpensive){
                        if(it.cost > returnRoute.cost)
                            returnRoute = it

                    } else {
                        if(it.cost < returnRoute.cost)
                            returnRoute = it
                    }
                }
            }
        }

        returnRoute
    }

    void loadFile(String input){
        List<String> lines = Files.readAllLines(Paths.get(input), StandardCharsets.UTF_8);

        def numIslands = lines.first().toInteger()

        lines[1..numIslands].each {
            def splits = it.split(" ")
            def name = splits[0]
            def cost = splits[1].toInteger()
            islands.put(name, new Island(name, cost))
        }

        def numRoutes = lines[numIslands+1].toInteger()

        lines[(numIslands+2)..(numIslands+numRoutes+1)].each {

            def splits = it.split(" ")

            def starting = islands.get(splits[0])
            def ending = islands.get(splits[1])
            def cost = splits[2].toInteger()

            starting.routes.add(new Route(ending, cost))
        }

        def numShips = lines[numIslands+numRoutes+2].toInteger()

        lines[(numIslands+numRoutes+3)..(numIslands+numRoutes+numShips+2)].each {
            def splits = it.split(" ")
            def num = splits[0].toInteger()
            def name = splits[1]
            def gold = splits[2].toInteger()
            def island = islands.get(splits[3])

            ships.add(new Ship(num, name, gold, island))
        }

    }

    static class Island {
        def name
        int cost
        List<Route> routes

        public Island(String name, int cost){
            this.name = name
            this.cost = cost
            this.routes = []
        }

    }

    static class Route {
        Island destination
        int cost

        public Route(Island destination, int cost){
            this.destination = destination
            this.cost = cost
        }
    }

    static class Ship {
        int number
        def name
        int gold
        Island island
        List<Island> visited

        public Ship(int number, String name, int gold, Island island){
            this.number = number
            this.name = name
            this.gold = gold
            this.island = island
            this.visited = []
            this.visited << island
        }

        public static Ship fromShip(Ship original){
            Ship s = new Ship(original.number, original.name.toString(), original.gold, original.island)

            s.visited.clear()

            original.visited.each {
                s.visited << it
            }

            s
        }

    }

    public static void main(String[] args){
        new Challenge5().execute(args[0], args[1])
    }
}
