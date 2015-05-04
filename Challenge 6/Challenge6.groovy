import groovy.transform.CompileStatic

/**
 * TUENTI CHALLENGE 2015
 * CHALLENGE 6
 * Dexafree
 */
@CompileStatic
class Challenge6 {

    // Name of the sheet.data file
    public final static String SHEET_DATA_NAME = "sheet.data"

    // Here we will store the precomputed matrix
    List<List<Long>> computedMatrix

    // Width of the provided matrix
    int supposedWidth

    // Height of the provided matrix
    int supposedHeight

    // Main function of the program
    void execute(String inputFile, String outputFile){

        computedMatrix = []

        // Load the searches we will have to make
        List<Search> searches = loadSearches(inputFile)

        // Load and pre-compute the matrix
        loadMatrix()
        println("MATRIX LOADED")

        // Check the sizes are correct
        assert supposedHeight == computedMatrix.size()
        assert supposedWidth == computedMatrix.first().size()

        println("HEIGHT = $supposedHeight | WIDTH = $supposedWidth")

        // Perform the searaches
        List<Long> results = performSearches(searches)

        // Save the results
        saveResults(results, outputFile)

    }

    void saveResults(List<Long> results, String outputFile){
        new File(outputFile).withWriter {writer ->
            for(i in 0..<results.size()) {
                writer.write "Case ${i+1}: ${results[i]}\n"
            }

        }
    }

    // Used for debug purposes
    void printMatrix(){
        computedMatrix.each {
            it.each {print "$it "}
            println()
        }
    }


    // Perform all the searches that need to be done
    List<Long> performSearches(List<Search> searches){

        List<Long> results = []
        List<String> resultPosition = []

        // Iterate through searches
        searches.each { Search search ->

            // Initialize the best quality at Long.MIN_VALUE
            def best = Long.MIN_VALUE
            String pos = ""

            // Find the starting coordinates for the center
            int startingCenterX = search.x0 + search.k
            int startingCenterY = search.y0 + search.k

            int centerY = startingCenterY

            // Move the center to bottom until it (and the airscrew) fits
            while (centerY + search.k <= search.y1) {

                // Set the center of the airscrew to the starting x position
                int centerX = startingCenterX

                // Move the center to the right until it (and the airscrew) fits
                while (centerX + search.k <= search.x1) {

                    // Get the current area
                    def area = calculateArea(centerX, centerY, search.k)

                    // Save the max area and the position
                    if (area > best) {
                        best = area
                        pos = "($centerX, $centerY)"
                    }

                    centerX++
                }

                centerY++
            }

            // Store the area and the position
            results << best
            resultPosition << pos
        }

        // Print the results
        results.each { println it }

        // Return the results
        results
    }

    // Calculates the area given a pair of coordinates (x,y) and the k
    long calculateArea(int centerX, int centerY, int k){

        // UP LEFT AREA
        int uLx0 = centerX - k
        int uLy0 = centerY - k
        int uLx1 = centerX - 1
        int uLy1 = centerY - 1

        long uLArea = getArea(uLx0, uLy0, uLx1, uLy1)



        // DOWN RIGHT AREA
        int dRx0 = centerX + 1
        int dRy0 = centerY + 1
        int dRx1 = centerX + k
        int dRy1 = centerY + k

        long dRArea = getArea(dRx0, dRy0, dRx1, dRy1)


        uLArea + dRArea

    }

    long getArea(int x0, int y0, int x1, int y1) {


        long OD = computedMatrix[y1][x1]
        long OA = (x0 == 0 || y0 == 0)? 0 : computedMatrix[y0 - 1][x0 - 1]
        long OC = (x0 == 0)? 0 : computedMatrix[y1][x0 - 1]
        long OB = (y0 == 0)? 0 : computedMatrix[y0-1][x1]

        long returnValue = OD - OB - OC + OA

        returnValue
    }


    // Matrix parsing
    void loadMatrix(){

        new File(SHEET_DATA_NAME).eachLine { String text, Integer lineNumber ->

            int realLineNumber = lineNumber.intValue()-1

            List<Long> thisLine = []

            String[] splits = text.split(" ")

            if(realLineNumber > 0){

                if(realLineNumber == 1){

                    thisLine << Long.parseLong(splits[0])
                    for(i in 1..<splits.length){
                        thisLine << thisLine[i-1] + Long.parseLong(splits[i])
                    }


                } else {

                    thisLine << (Long.parseLong(splits[0]) + computedMatrix.last()[0])
                    for(i in 1..<splits.length){
                        long rawValue = Long.parseLong(splits[i])
                        long toMyLeft = thisLine[i-1]
                        long overMe = computedMatrix.last()[i]
                        long overLeft = computedMatrix.last()[i-1]

                        long finalValue = rawValue + toMyLeft + overMe - overLeft

                        thisLine << finalValue
                    }

                }

                computedMatrix << thisLine

            } else {
                supposedHeight = Integer.parseInt(splits[0])
                supposedWidth = Integer.parseInt(splits[1])
            }

        }

    }

    List<Search> loadSearches(String inputFile){
        def lines = new File(inputFile).readLines()

        int numLines = lines[0].toInteger()

        return lines[1..numLines].collect {
            String[] splits = it.split(" ")
            new Search(splits[1].toInteger(),
                    splits[0].toInteger(),
                    splits[3].toInteger(),
                    splits[2].toInteger(),
                    splits[4].toInteger())
        }
    }

    static class Search {
        int x0
        int y0
        int x1
        int y1
        int k

        Search(int x0, int y0, int x1, int y1, int k) {
            this.x0 = x0
            this.y0 = y0
            this.x1 = x1
            this.y1 = y1
            this.k = k
        }
    }

    public static void main(String[] args){
        new Challenge6().execute(args[0], args[1])
    }
}
