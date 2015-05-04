# Tuenti Challenge 5
# Challenge 1
# Dexafree

import sys

# Define the lambda function
calculate = lambda x: int((x+1)/2)

def challenge(fin):
    # Open the file
    with open(fin) as f:

        # Read the file and split the lines (avoid \n)
        content = f.read().splitlines()

        # Get the number of inputs to process
        numlines = int(content[0])

        # Initialize to 1, because the first is the number of inputs
        i = 1

        # Iterate through all the inputs
        while(i <= numlines):
            # Get the current input as int
            current = int(content[i])

            # Run the function
            result = calculate(current)

            # Print the result
            print result
            i +=1

if __name__ == "__main__":
    fin = sys.argv[1]
    challenge(fin)
