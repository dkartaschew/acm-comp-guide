
#include <cstdlib>
#include <iostream>
#include <cctype>
#include <cstring>

using namespace std;

char** wordmap;
int testCases = 0;
int wordmapWidth = 0;
int wordmapHeight = 0;
char blankline[256];
int wordSearches = 0;
char* pattern = NULL;
int patternLength = 0;
char* text = NULL;

/**
 * Attempt to find a pattern contained within the text.
 */
int BruteForceStringMatch(char* text, int textLength, char* pattern, int patternLength) {
  int patternIndex = 0;

  // Ensure the text is big enough to hold our pattern!
  if (patternLength > textLength) {
    return -1;
  }

  // Scan the text looking for the pattern
  for (int textIndex = 0; textIndex < textLength - patternLength; textIndex++) {
    patternIndex = 0;
    // While the current text position matches the pattern, keep scanning it.
    while ((patternIndex < patternLength) && (text[patternIndex] == pattern[textIndex + patternIndex])) {
      patternIndex++;
    }
    // If the patternIndex is equal to the patternLength, we have found the pattern!
    if (patternIndex == patternLength) {
      return textIndex;
    }
  }
  // Pattern not found, so return -1.
  return -1;
}


/**
 * Create a search pattern of length, from the word map.
 * @param col starting position in the word map (0 offset)
 * @param row starting position in the word map (0 offset)
 * @param length length of required string.
 * @param colOffset direction to move column index for each letter.
 * @param rowOffset direction to move row index for each letter.
 * @return Index of found string.
 */
char* buildTextString(int col, int row, int length, int colOffset, int rowOffset) {
  char* newString = new char[length + 1];
  int index = 0;
  while (length--) {
    newString[index++] = wordmap[row][col];
    row += rowOffset;
    col += colOffset;
    // Ensure we are not going outside map boundary
    if ((row < 0) || (row >= wordmapHeight) || (col < 0) || (col >= wordmapWidth)) {
      newString[index] = NULL;
      return newString;
    }
  }
  newString[index] = NULL;
  return newString;
}


/**
 * Convert string to lower case, and store in place
 * @param str ASCIIZ string to convert.
 */
void strToLower(char* str) {
  int i = 0;
  while (str[i]) {
    str[i] = (char) tolower(str[i]);
    i++;
  }
}


/**
 * Main 
 */
int main() {
  scanf("%d", &testCases);
  while (testCases--) {
    gets(blankline);
    // Read in the wordmap, and ensure in lower case.
    scanf("%d %d", &wordmapHeight, &wordmapWidth);
    wordmap = new char*[wordmapHeight];
    for (int row = 0; row < wordmapHeight; row++) {
      wordmap[row] = new char[wordmapWidth + 1];
      scanf("%s", wordmap[row]);
      strToLower(wordmap[row]);
    }

    // Read in the number of words to search for.
    scanf("%d", &wordSearches);
    while (wordSearches--) {
      // Read in a single word.
      pattern = new char[wordmapWidth + 1];
      scanf("%s", pattern);
      strToLower(pattern);
      patternLength = strlen(pattern);

      // Attempt to scan for the word;
      for (int row = 0; row < wordmapHeight; row++) {
        for (int col = 0; col < wordmapWidth; col++) {
          for (int rowOffset = -1; rowOffset < 2; rowOffset++) {
            for (int colOffset = -1; colOffset < 2; colOffset++) {
              if ((colOffset != 0) || (rowOffset != 0)) {
                // Get our next text string, and attempt to match!
                text = buildTextString(col, row, patternLength, colOffset, rowOffset);
                if (BruteForceStringMatch(text, strlen(text) + 1, pattern, patternLength) == 0) {
                  printf("%d %d\n", (row + 1), (col + 1));
                  delete(text);
                  goto break_search;
                }
                delete(text);
              }
            }
          }
        }
      }
break_search:

      // Free our word.
      delete(pattern);
    }

    // Free our wordmap;
    for (int row = 0; row < wordmapHeight; row++) {
      delete(wordmap[row]);
    }
    delete(wordmap);
    printf("\n");
  }
  return 0;
}

