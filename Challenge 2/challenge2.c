/* TUENTI CHALLENGE 2015
 * CHALLENGE 2
 * Dexafree
 */

#include <stdio.h>
#include <stdlib.h>

// Checks if a number n is almost prime
int almPrime(int n){
    int p, f = 0;

    // Divide as much times as you can (but less than 3) for two
    while(n%2 == 0 && f < 2){
        n /= 2;
        f++;
    }

    // As there will be no more even primes, add 2 to each iteration
    // Also, only check until sqrt(n), as n will have no dividers greater
    // then its sqrt
    for (p = 3; f < 2 && p*p <= n; p += 2)
        while (0 == n % p)
            n /= p, f++;

    return f + (n > 1) == 2;
}

unsigned long long int getLong(FILE *f){
    unsigned long long int num;
    fscanf(f, "%llu", &num);
    return num;

}

int getNum(FILE *f){
    int num;
    fscanf(f, "%d", &num);
    return num;
}

void getAlmostPrimes(unsigned long long int first, unsigned long long int last){
    unsigned long long int i, c, k;
    for (i = first, c = 0; i < last; i++){
        if (almPrime(i)) {
            c++;
        }
    }
    printf("\n%llu", c);
}

int main(int argc, char **argv){

    FILE *f = fopen(argv[1], "r");

    if (f != NULL){

        int numLines = getNum(f);

        int line;
        for(line=0; line<numLines; line++){
            unsigned long long int first = getLong(f);
            unsigned long long int last = getLong(f);
            getAlmostPrimes(first, last);
            printf("\nLINE %d FINISHED\n", line);

        }
        fclose(f);
    } else {
        printf("\nFILE NOT FOUND\n");
    }

    printf("\n");


    return 0;
}
