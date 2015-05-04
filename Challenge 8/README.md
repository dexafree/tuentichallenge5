# Challenge 8 - Alchemy Pot

[LINK TO ORIGINAL](https://contest.tuenti.net/Challenges?id=8)

You’re playing the latest version of your favorite RPG: Tales of a Mighty Geek XIII. This version of the game has a fairly common mini-game within it: a goblin has given you an Alchemy Pot, where you put some items in and convert them into something else.

The rules are really simple; you just need to mix some goods in the pot and then a brand-new item is created. For example:

* 3 x healing potion = 1 phoenix potion
* 1 dragon scale + 1 steel shield = 1 dragon shield

But you are getting tired of these kind of alchemy games (and, frankly, you already have some kickass equipment) so you’ve decided to just go for the gold.

Using a digitalization spell you’ve been able to convert your Alchemy Book into an easily handled [file](https://contest.tuenti.net/resources/book.data.xz). The file has one element per line with its name, its gold value, and the name of the elements needed to obtain it:

```
name value source_element1 source_element2 … source_elementS
```

As you can see by taking a look at the file, the names don’t have spaces or special characters, the value is an integer (gold coins!) and there are base elements without any source element or combined elements with between 2 and 10 source element. What’s more, an item can’t produce the same item again, no matter how many transformations it undergoes.

Your task is to elaborate an algorithm that, given the set of items that you have in your inventory, returns the maximum amount of gold that you can obtain.

### Input

The first line is **N**. **N** scenarios will follow.

Each scenario will be represented in one line with the **E** element names that represent the available inventory. The inventory capacity is 20 and the same element can appear multiple times in the inventory, occupying one slot each.

### Output

**N** lines with the maximum amount of gold that can be obtained in each scenario.

### Scenario examples

### Sample Input

```
1
healing_potion healing_potion healing_potion dragon_scale steel_shield
```

### Sample Output

```
10000
```
