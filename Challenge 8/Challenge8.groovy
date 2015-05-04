import groovy.transform.CompileStatic

/**
 * TUENTI CHALLENGE 2015
 * CHALLENGE 8
 * Dexafree
 */
@CompileStatic
class Challenge8 {

    // The .data file name
    public final static String DATA_FILE = "book.data"

    // Map containing the Elements in (element_name, Element instance)
    HashMap<String, Element> elements = [:]

    // List of starting inventories
    List<List<Element>> inventory = []

    // List of the results
    List<Integer> bestGoldResults = []

    void execute(String inputFile, String outputFile){

        // Initial data parsing
        parseData()

        // Starting inventories parsing
        parseInventory(inputFile)

        println("SIZE: ${inventory.size()}")

        // Solve every inventory
        inventory.each {

            bestGoldResults << 0
            solve(it)
            println("Solved")
        }

        bestGoldResults.each {println it}

        // Write the results to file
        new File(outputFile).withWriter { writer ->
            bestGoldResults.each {
                writer.write "$it\n"
            }
        }
    }

    // Parse the .data file
    void parseData(){
        new File(DATA_FILE).eachLine {String line ->
            def splits = line.split(" ")

            def name = splits[0]
            int gold = splits[1].toInteger()
            def elem = new Element(name, gold)

            // Initialize the elementsNeeded array
            elem.elementsNeeded = []
            if(splits.length > 2){
                splits[2..<splits.length].each {

                    // Store every element needed for its creation
                    elem.elementsNeeded << elements[it]

                    // To every element needed for its creation, save that it can create this Element (double relationship will speed up future checks)
                    elements[it].canCreate << elem
                }
            }

            elements.put(name, elem)
        }

    }

    // Parse the starting inventories
    void parseInventory(String inputFile){
        new File(inputFile).eachLine { String line, int lineNumber ->
            if(lineNumber > 1){
                def splits = line.split(" ")
                List<Element> invent = []
                splits.each { String it ->

                    invent << elements[it]

                }
                inventory << invent
            }

        }
    }

    // Solves the problem for a given inventory
    void solve(List<Element> myInventory){

        def currentGold = goldInventory(myInventory)

        // Save the best gold
        if(currentGold > bestGoldResults.last()){
            bestGoldResults[bestGoldResults.size()-1] = currentGold
        }

        // Iterate through all the elements of the inventory
        myInventory.each {

            // Iterate through all the elements that element can create
            it.canCreate.each { Element createdElement ->

                // Check if it can be created with our current inventory
                if(canBeCreated(createdElement, myInventory)){

                    // Copy the current inventory in order not to modify it for future recursions
                    def newList = copyList(myInventory)

                    // Remove every element needed for creating the new Element
                    createdElement.elementsNeeded.each {newList.remove(it)}

                    // Add the element created
                    newList << createdElement

                    // Recursion
                    solve(newList)

                }
            }

        }

    }

    // Calculates the amount of gold given a List of Element
    int goldInventory(List<Element> elements){
        def gold = 0
        elements.each {gold += it.gold}
        gold
    }

    // Checks if an Element can be created with a List of Element
    boolean canBeCreated(Element createdElement, List<Element> inventory){

        def copy = copyList(inventory)

        def can = true
        createdElement.elementsNeeded.each {

            if(can){
                if(!copy.contains(it)){
                    can = false
                } else {
                    copy.remove(it)
                }
            }
        }

        can

    }

    List<Element> copyList(List<Element> originalList){
        List<Element> newList = []
        originalList.each {newList << it}
        newList
    }

    static class Element {
        String name
        int gold
        List<Element> elementsNeeded
        List<Element> canCreate

        public Element(String name, int gold) {
            this.name = name
            this.gold = gold
            this.elementsNeeded = []
            this.canCreate = []
        }
    }

    public static void main(String[] args){
        new Challenge8().execute(args[0], args[1])
    }
}
