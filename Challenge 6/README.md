# Challenge 6 - Airscrews

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=6)

How do you build the best airplane in the world? There are many important parts in an airplane that must be designed carefully. Of all these parts (engine, computers, glass, etc.) we will be focusing on the airscrews. Airscrews are made of two square pieces (of size k) with a hole for the axis.

![Propeller](https://contest.tuenti.net/resources/img/propeller.jpg)

The best airscrews are those whose pieces are of the highest possible quality. The quality of their pieces is determined by the quality of each cm2 of the material used for their construction.

To help the engineers, we want to develop a program that determines where to position the axis of the airscrew. This position must give the best quality when the sheet is cut. Note that, once the position of the axis has been set, the machine is only capable of cutting the pieces as depicted in the following figure (they cannot be rotated).

![](https://contest.tuenti.net/resources/img/schema.png)

<div>
<div style="width:50%;float:left">
<img src="resources/img/schema.png" alt="schema" style="display:block">
</div>
<div style="width:50%;float:right" align="center">
<table style="float:top">
<tbody><tr>
<td>1</td><td>4</td><td bgcolor="red">2</td><td bgcolor="red">6</td><td>8</td><td>1</td><td>2</td>
</tr>
<tr>
<td>7</td><td>2</td><td bgcolor="red">9</td><td bgcolor="red">1</td><td>8</td><td>4</td><td>4</td>
</tr>
<tr>
<td bgcolor="aqua">4</td><td bgcolor="aqua">2</td><td>3</td><td>3</td><td bgcolor="lightgray">8</td><td>3</td><td>1</td>
</tr>
<tr>
<td bgcolor="aqua">8</td><td bgcolor="aqua">8</td><td>1</td><td>1</td><td>5</td><td bgcolor="red">5</td><td bgcolor="red">1</td>
</tr>
<tr>
<td>7</td><td>3</td><td bgcolor="lightgray">3</td><td>1</td><td>7</td><td bgcolor="red">1</td><td bgcolor="red">3</td>
</tr>
<tr>
<td>1</td><td>4</td><td>2</td><td bgcolor="aqua">6</td><td bgcolor="aqua">8</td><td>1</td><td>2</td>
</tr>
<tr>
<td>7</td><td>2</td><td>9</td><td bgcolor="aqua">1</td><td bgcolor="aqua">8</td><td>4</td><td>4</td>
</tr>
<tr>
<td>1</td><td>4</td><td>2</td><td>6</td><td>8</td><td>1</td><td>2</td>
</tr>
<tr>
<td>7</td><td>2</td><td>9</td><td>1</td><td>8</td><td>4</td><td>4</td>
</tr>
</tbody></table>
</div>
</div>


For instance, the quality of the airscrew obtained if we choose the position 4,2 for the axis and a length of square k=2:

```
(2 + 6 + 9 + 1) + (5 + 1 + 1 + 3) = 18 + 10 = 28
```

And choosing position 2,4 for the axis and a length of square k=2:

```
(4 + 2 + 8 + 8) + (6 + 8 + 1 + 8) = 22 + 23 = 45
```


**NOTE:** Generate the info related to the sheet used in this process by means of [this generator](https://contest.tuenti.net/resources/airscrews-gen.py) (do not modify it). Run this generator with python2, it will create a file named sheet.data. The MD5 of the file should be: `eb8294008e7dbf4e6dffb7edadafbefe`

The first line of this file contains two integers M and N indicating the size of the matrix. The following M lines will contain N integers (aij) each one representing the quality of that specific cm2.

### Input

In the first line, an integer T indicates the number of cases. Each case is described in a line with five integers y0, x0, y1, x1 and K. (x0, y0) and (x1,y1) are coordinates in the sheet indicating the initial and final coordinates (both inclusive) where the search procedure is performed. And the K value is the length needed for the pieces of the airscrews.

### Output

For each case t, the output is the string "Case t:" followed by the highest quality of the pieces of the airscrew that can be found within the sheet. Check the output example.

### Limits

* 10 ≤ N, M ≤ 5000
* 2 ≤ k ≤ min(N/2, M/2)-1
* 1 ≤ aij ≤ 10000

### Sample Input 1

Considering the sheet in the example described above:

```
3
0 0 8 6 2
0 0 8 6 3
1 1 6 6 2
```

### Sample Output 1

In the first case, the result is obtained by choosing the position (5,2) as the axis. In the second and third case, the solution is obtained by choosing the position (4,3) as the axis.

```
Case 1: 49
Case 2: 82
Case 3: 35
```

### Sample Input 2

Considering the file provided as the sheet:

```
10
10 10 25 19 3
29 28 875 413 69
25 38 182 801 63
34 16 748 391 102
6 2 558 526 161
8 40 406 875 82
47 21 314 826 54
28 20 1005 638 202
3 28 543 219 79
47 49 994 189 57
```

The first test case of this sample would take into account this part of the sheet for the search:

```
3313 4383 1367 3520 1665 1853 9816 8483 632 6372
2888 5315 3470 9078 2354 9337 1766 4774 1956 2236
9522 9993 4479 3777 3433 5618 4447 1148 6416 605
7713 4463 8852 6482 9867 1103 6969 3864 6355 2741
1633 173 6703 924 5986 9649 4718 6373 7872 2650
5220 8463 6828 6735 5635 9742 4721 7020 9726 290
1201 5461 6472 4883 4878 2474 5033 843 469 3760
4583 294 3903 8582 2566 333 2182 12 9666 1584
9049 4560 6740 5954 5828 8386 5082 8673 6444 5053
627 7174 3774 7724 6458 177 7052 4150 1491 5432
1652 4611 2002 7903 7356 1160 4671 4493 8936 2342
4714 1593 8632 2608 2366 5340 3494 8722 7166 9881
6604 669 6803 186 8209 7948 4482 9766 6255 8242
7293 7000 8360 3811 4320 6520 4518 8323 5488 2355
1810 2696 8074 8990 8120 1425 2881 8699 549 57
6361 7714 3632 2086 1112 4260 168 944 6356 2174
```

### Sample Output 2

```
Case 1: 112206
Case 2: 48728843
Case 3: 40490669
Case 4: 105457426
Case 5: 260545848
Case 6: 68352858
Case 7: 29933237
Case 8: 410319922
Case 9: 63526038
Case 10: 33321836
```

---

### Personal considerations
In order to solve this challenge, I used the optimization found [here](http://www.ardendertat.com/2011/09/20/programming-interview-questions-2-matrix-region-sum/) in order to make the cost of every search to be O(1).

It's based on making every coordinate have the accumulated area from O point (0,0) to itself (including itself).

I suggest you to check the article in order to fully understand one of the most elegant solutions I've found to this problem (which I realized that could have been very useful for Challenge 3)
