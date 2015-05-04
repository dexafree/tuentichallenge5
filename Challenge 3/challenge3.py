# TUENTI CHALLENGE 5
# CHALLENGE 3
# Dexafree

import sys

INPUT_FILE = sys.argv[1]
OUTPUT_FILE = sys.argv[2]

# Here we will store the first 25 primes
primes = []

# Here we will store the prime decomposition of the 30k numbers in numbers.txt
factor_list = []


def merge_two_dicts(x, y):
    """Given two dictionaries made of str:int, it merges both
    in a single dict, containing all the keys, and adding the
    values if the keys are repeated
    EX: a = {"a":1, "b":2}. b = {"b":3, "c":4}
    merge_two_dicts(a,b) -> {"a":1, "b":5, "c":4}
    """
    z = {}
    for elem in x:
        z[elem] = x[elem]
        if elem in y:
            z[elem] += y[elem]

    for elem in y:
        if elem not in z:
            z[elem] = y[elem]

    return z


def fill_primes():
    """Calculates the 25 first primes and stores them into the
    primes global array
    """
    for num in range(2,100):
        if all(num % i !=0 for i in range(2,num)):
           primes.append(num)

def reduce_number(number):
    """Given a number, it returns a dict containing all the primes
    that compose that number, and how many times have they appeared
    """
    dct = {}
    while number != 1:
        for prime in primes:
            if number % prime == 0:
                number /= prime
                if prime not in dct:
                    dct[prime] = 1
                else:
                    dct[prime] += 1
    return dct

def factorize():
    """Starts the factorization process
    """
    fill_primes()

    print "Finished generating primes"
    with open("numbers.txt") as f:
        print "Factorizing numbers..."
        content = f.read().splitlines()
        i = 0
        while i < 30000:
            if i % 1000 == 0:
                print str((i/1000)+1)+"/30"
            num = int(content[i])
            factors = reduce_number(num)
            factor_list.append(factors)
            i += 1

def write(string):
    hs = open(OUTPUT_FILE, "a")
    hs.write(string+"\n")
    hs.close()

def most_common(dct):
    """Given a dict that contains all the factors of a number, it
    calculates the most common factor/s and prints them, including
    how many times they have appeared
    """

    # Here we get the maximum number of repetitions
    most_comm = reduce(lambda x, y: max(x, y), dct.values())

    # Filter the dict and return only the numbers that repeat most_comm times
    elems = filter(lambda x: dct[x] == most_comm, dct)

    # Print the result in format REPETITIONS: number [number]...
    string = str(most_comm)+" "+" ".join([str(num) for num in elems])
    write(string)

def run():
    """Having all the numbers factorized and stored, with every
    dictionary placed at the same index as the line, process the
    input
    """
    with open(INPUT_FILE) as f:
        content = f.read().splitlines()
        numlines = int(content[0])

        i = 1
        while i <= numlines:
            print str(i)+"/"+str(numlines)
            current_line = content[i]

            # Split the line for being able to get the starting value and the ending one
            splits = current_line.split()

            starting = int(splits[0])
            ending = int(splits[1])

            last = factor_list[starting]
            for line in range(starting+1, ending):
                last = merge_two_dicts(last, factor_list[line])
            most_common(last)
            i += 1

def main():
    factorize()
    print "Finished factorizing"
    run()

if __name__ == "__main__":
    main()
