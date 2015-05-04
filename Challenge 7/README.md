# Challenge 7 - The Perfect Larry Matching Algorithm

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=7)

Larry had tried everything he possibly could. First, he tried going to bars accompanied by a shameless wingman who (for a not-too-large fee) would approach girls and ask them "have you met Larry?"... but the same answer always came back, “Thank God, no!. He tried lying, pretending to be a rich and successful man, or a thrill-seeking archeologist. He tried speed dating, with the emphasis in his case on “speed”. And, finally, he tried meeting girls using dating websites, which promised him to find his perfect match... but that didn't work either.

![Larry](https://contest.tuenti.net/resources/img/larry.jpg)

After years and years of loneliness and not scoring, Larry realized that the blame lay, of course, with the matching algorithm used by these dating sites. It wasn't good enough, and that’s why it always matched him with women who weren't interested in him at all. As he had so much time on his hands, he decided to make a study. He has created a poll on a large dataset of men and women and compared the answers with the results, obtaining a set of meaningful questions for every profile. He has also found that the important issue often isn't the answer given by the girl, but the ones given by her friends, or even her friend’s friends. Finally, he has studied his own profile and as a result he has obtained... the Perfect Larry Matching Algorithm.

The questions in the poll are as follows:

* A. Do you like naughty, dirty games?
* B. Do you like super hero action figures?
* C. Do you like men in leisure suits?
* D. Do you like cats?
* E. Do you like to go shopping?

The final score for every girl G will be obtained by adding these points:

* 7 points if G likes naughty, dirty games
* 3 points for every friend of G who likes super hero action figures.
* 6 points for every friend of a friend of G, not including the friends of G and G herself, who likes men in leisure suits
* 4 points if G has any friend H who likes cats, and no friend of H (except perhaps G) likes cats (4 points at most, not 4 for every friend).
* 5 points for every girl H who likes to go shopping and has no possible connection with G through a chain of friends (friends, friends of friends, friends of friends of friends, etc.)

Larry is now feeling hopeful and anxious... his big day is getting closer. He just needs a tough and hardened engineer who is able to process the data of his polls according to the magic algorithm. Could it be you?

### Input

The input file starts with 2 numbers in a single line:

* N, the number of girls in the poll
* M, the number of friendship lines

N lines will follow, each one containing the name of the girl, and her answers to the questions A to E, using a “Y” if the answer is yes or an “N” if the answer is no.

After those, M lines will follow, each one containing a sequence of names of girls who are friends with one another (every girl in the sequence is a friend of the others). Friendship relationships between two or more girls can be repeated in other lines.

### Output

Output one single line indicating the maximum score obtained by a girl in the poll.

### Limits

M and N will fit in a 32-bit signed integer.

### Sample input

```
5 2
MINDY N Y Y Y N
COLLEEN Y Y N N Y
PIGGY N N N N Y
BAMBI N N Y Y N
BARBARA Y Y Y Y Y
MINDY COLLEEN BAMBI
PIGGY BAMBI
```

### Sample output

```
17
```

Barbara is the chosen one! She gets 7 points because she likes naughty, dirty games (A) and 5*2 = 10 points because Colleen and Peggy like to go shopping and they have no direct or indirect connection through friends to Barbara (E). Mindy got 12 points, Colleen 15, Piggy 11 and Bambi 15.
