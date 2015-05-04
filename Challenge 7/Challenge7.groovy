/**
 * TUENTI CHALLENGE 2015
 * CHALLENGE 7
 * Dexafree
 */
class Challenge7 {

    // We will store the girls by (name, Girl instance)
    HashMap<String, Girl> girls = [:]


    void execute(String inputFile, String outputFile){

        // Load the graph
        createGraph(inputFile)

        // Calculate the punctuations (it will be stored into every Girl instance)
        calculatePunctuations()

        // Get the best punctuation
        def bestPunctuation = calculateBestPunctuation()

        // Print the best punctuation
        println(bestPunctuation)

        // Write it to file
        new File(outputFile).withWriter {writer ->
            writer.write("$bestPunctuation")
        }
    }

    // Input parse
    void createGraph(String inputFile){
        def lines = new File(inputFile).readLines()

        def splits = lines[0].split(" ")
        def numGirls = splits[0].toInteger().intValue()
        def friendshipLines = splits[1].toInteger().intValue()

        lines[1..numGirls].each { String line ->
            def lineSplits = line.split(" ")
            def name = lineSplits[0]

            List<Boolean> answers = []

            lineSplits[1..<lineSplits.length].each { String answer ->
                answers << (answer == "Y")
            }

            girls.put(name, new Girl(name, answers))
        }

        lines[numGirls+1..numGirls+friendshipLines].each { String line ->
            def lineSplits = line.split(" ")

            List<Girl> girlsInThisLine = []

            lineSplits.each { String split ->
                girlsInThisLine << girls[split]
            }

            linkGirls(girlsInThisLine)
        }
    }

    // Calculates the punctuation for every girl
    void calculatePunctuations(){

        // Iterate through every girl
        girls.each { String girlName, Girl current ->

            // Get the punctuation for every question
            def qA = questionA(current)

            def qB = questionB(current)

            def qC = questionC(current)

            def qD = questionD(current)

            def qE = questionE(current)

            println(girlName + " A: $qA | B: $qB | C: $qC | D: $qD | E: $qE")

            // Store it into the Girl instance
            current.punctuation = qA + qB + qC + qD + qE
        }


    // Iterates through all the girls and gets the maximum position
    int calculateBestPunctuation(){
        def max = 0
        def maxGirl = null
        girls.each {String girlName, Girl girl ->
            if(girl.punctuation > max) {
                max = girl.punctuation
                maxGirl = girl
            }

        }

        println(maxGirl.name)

        max
    }

    int questionA(Girl current){

        return (current.answers[0]) ? 7 : 0

    }

    int questionB(Girl current){
        def punctuation = 0
        current.friends.each { Girl friend ->
            // Question B
            if(friend.answers[1]) {
                punctuation += 3
            }
        }
        punctuation
    }

    int questionC(Girl current){
        def punctuation = 0
        List<Girl> countedFriend = []
        current.friends.each { Girl friend ->

            friend.friends.each { Girl friendOfAFriend ->
                if (friendOfAFriend != current) {
                    if (!current.friends.contains(friendOfAFriend)) {
                        if(!countedFriend.contains(friendOfAFriend)) {
                            if (friendOfAFriend.answers[2]) {
                                countedFriend << friendOfAFriend
                                punctuation += 6
                            }
                        }
                    }
                }
            }
        }
        punctuation
    }

    int questionD(Girl current){
        def punctuation = 0

        current.friends.each { Girl friend ->

            def likesCats = friend.answers[3]

            if(likesCats) {

                def anyLikesCats = false

                friend.friends.each { Girl friendOfFriend ->
                    if (friendOfFriend != current) {
                        if (friendOfFriend.answers[3]) {
                            anyLikesCats = true
                        }
                    }
                }

                if(!anyLikesCats){
                    punctuation = 4
                }
            }
        }

        punctuation
    }

    int questionE(Girl current){
        def punctuation = 0
        girls.each { String name, Girl girl ->
            if(girl != current){
                if(girl.answers[4]){
                    List<Girl> doesNotKnow = []
                    if(!isConnectedWith(current, girl, doesNotKnow)){
                        punctuation += 5
                    }
                }
            }
        }
        punctuation
    }

    private boolean isConnectedWith(Girl current, Girl destination, List<Girl> visited) {

        if (destination.friends.contains(current)) {
            return true
        } else {
            boolean knows = false
            destination.friends.each { Girl friend ->
                if (!visited.contains(friend) && !knows) {
                    visited << friend
                    knows = isConnectedWith(friend, current, visited)
                }
            }
            return knows
        }
    }

    void linkGirls(List<Girl> girlsList){
        for(i in 0..<girlsList.size()) {
            for (j in 0..<girlsList.size()) {
                if (i != j) {
                    girlsList[i].friends << girlsList[j]
                }
            }
        }

    }

    static class Girl {
        String name
        List<Boolean> answers
        List<Girl> friends
        int punctuation

        public Girl(String name, List<Boolean> answers){
            this.punctuation = 0
            this.name = name
            this.answers = answers
            this.friends = []
        }

        public void addFriend(Girl g){
            if(!friends.contains(g))
                friends << g
        }
    }

    public static void main(String[] args){
        new Challenge7().execute(args[0], args[1])
    }
}
