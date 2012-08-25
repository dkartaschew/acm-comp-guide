
#include <cstdlib>
#include <cstdio>
#include <cstring>

using namespace std;

#define MAX_PATTERN_LENGTH 100000
#define MAX_TEXT_LENGTH 500000

int testCases = 0;
char pattern[MAX_PATTERN_LENGTH];
char text[MAX_TEXT_LENGTH];

int index = 0;
int testCount = 1;


/**
 * KMP Failure function.
 * @param Pattern Pointer to the pattern to be processed to derive the failure function.
 * @param patternLength Length of pattern.
 * @return The failure function (array of int).
 */
int *KMPFailure(char *Pattern, int patternLength) {
  int indexI = 1;
  int indexJ = 0;
  int* failure = new int[patternLength]; // Create new function f.

  failure[0] = 0;
  while (indexI < patternLength) {
    if (Pattern[indexJ] == Pattern[indexI]) {
      failure[indexI++] = ++indexJ;
    } else if (indexJ > 0) {
      indexJ = failure[indexJ - 1];
    } else {
      failure[indexI++] = 0;
    }
  }
  return failure;
}


/**
 * Utilise the KMP Substring search algorithm to find a pattern in text.
 * @param Text Pointer to the text to be searched.
 * @param textLength Length of the text to be processed.
 * @param Pattern Pointer to the pattern to find within the text
 * @param patternLength Length of the pattern string
 * @return Index of pattern in text, otherwise -1 if not found.
 */
int KMPMatch(char* Text, int textLength, char* Pattern, int patternLength) {
  int* failure = KMPFailure(Pattern, patternLength);
  int indexI = 0;
  int indexJ = 0;
  while (indexI < textLength) {
    if (Pattern[indexJ] == Text[indexI]) {
      if (indexJ == (patternLength - 1)) {
        delete [] failure;
        return (indexI - patternLength + 1);
      }
      indexI++;
      indexJ++;
    } else if (indexJ > 0) { // No match but advanced in P.
      indexJ = failure[indexJ - 1]; // Move forward appropriate amount.
    } else {
      indexI++;
    }
  }
  delete [] failure;
  return -1;
}


/*
 * Main Function
 */
int main() {
  scanf("%d\n", &testCases);
  while (testCases--) {
    // Read in our pattern and text
    gets(pattern);
    gets(text);

    // found our pattern in text.
    index = KMPMatch(text, strlen(text), pattern, strlen(pattern));
    if (index == -1) {
      printf("%d NOT FOUND\n", testCount++);
    } else {
      printf("%d %d\n", testCount++, index);
    }
  }
  return 0;
}
