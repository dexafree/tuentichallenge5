# Challenge 4 - A Bitter Dinner

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=4)

We are hosting a very special dinner tonight. A lot of guests are coming, and some of them are celebrities. But this is a special dinner. It's the annual CPU bitter dinner, where all the CPUs meet, talk, have fun, and of course... eat.

But this year we've got a problem. We weren't able to find a chef, the CPUs are almost here and we need to start preparing the food. We need your help.

Fortunately all the CPUs eat the same food; bits. And we already have a long string of delicious bits available for them. But... as you know, when you have to prepare food for a lot of guests, you are going to have individual requests. Not all the CPUs eat the same quantity of bits, some are left-handed and they want their bits like that, etc.

The CPUs come to this meeting to have fun and they don't want to work. What's more, some of them are particularly bad at dividing things and we don't want to risk a CPU losing a leg (I'm looking at you, old Pentium). So your job tonight is to split a long bitstring into pieces that are manageable for each CPU. Don't worry we have an exhaustive list of the pieces you will need.

### Input

The first line will be a long byte sequence encoded using base64.
The next line is the number of pieces you'll have to cut.
After that, each line will be the description of a piece. Each piece is determined by a number which represents the size in bits of this piece, followed by the letter L or B which means Little-Endian or Big-Endian, and finally, if there is an R, it means that you have to bit-reverse the piece.

### Output

For each piece just print the integer that you cut. All integers are unsigned.

### Limits

* N = Number of bits of each piece
* G = Number of guests
* 1 ≤ N ≤ 300
* 1 ≤ G ≤ 1000

### Sample Input

```
6cGW2vYxiyB1yl6ZhMuDIgcTsX77fHVMKQ==
10
29L
29B
20B
9B
13L
28BR
27B
13B
10B
20L
```

### Sample Output

```
462864873
198755884
531826
303
6220
72097074
3710347
7931
497
667861
```
