# Challenge 3 - Favourite primes

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=3)

A composite number is a positive integer that has at least one positive divisor other than one or the number itself. In other words, a composite number is any positive integer greater than one that is not a prime number.
For example, 14 = 2*7 and 18 = 2*3*3 are composite numbers.

We will say a number is "almost prime" if it has exactly two (not necessarily distinct) prime factors.

For example, the following numbers are almost prime: 6 = 2*3, 25 = 5*5. And the following numbers are not: 17 (prime), 81 = 3*3*3*3.

Please help us get an idea about how many almost prime numbers there are in certain integer intervals.

### Input

The first line contains an integer T, the number of test cases. T lines follow, containing two integers each: A and B, separated by a space.

### Output

For each test case, print the number of almost prime numbers P that verify A ≤ P ≤ B.

### Constraints

```
1 ≤ T ≤ 100
1 ≤ A ≤ B ≤ 10^8
```

### Sample input

```
2
1 10
10 20
```

### Sample output

```
4
3
```

---

### Personal considerations
When I made this challenge, it took 45 minutes to process the entire submit file. It was an improvement, since the previous attempt took about 8 hours.

However, after solving the 6th Challenge, I realized that I could have used that optimization in order to solve this one much faster

You can check more details about the optimization in Challenge 6
