
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
int location;

int currentRow = 0;
int currentCol = 0;


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
  for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {
    patternIndex = 0;
    // While the current text position matches the pattern, keep scanning it.
    while ((patternIndex < patternLength) && (pattern[patternIndex] == text[textIndex + patternIndex])) {
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


void UpdateFind(int row, int col, int rowOffset, int colOffset, int location) {
  if (currentRow > row + (rowOffset * location)) {
    currentRow = row + (rowOffset * location);
    currentCol = col + (colOffset * location);
  } else if (currentRow == row + (rowOffset * location)) {
    if (currentCol > col + (colOffset * location)) {
      currentRow = row + (rowOffset * location);
      currentCol = col + (colOffset * location);
    }
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

      currentRow = wordmapHeight;
      currentCol = wordmapWidth;

      //Attempt to scan for word, along the top of the grid.
      for (int col = 0; col < wordmapWidth; col++) {
        int row = 0;
        int rowOffset = 1;
        for (int colOffset = -1; colOffset < 2; colOffset++) {
          // Get our next text string, and attempt to match!
          text = buildTextString(col, row, 50, colOffset, rowOffset);
          location = BruteForceStringMatch(text, strlen(text), pattern, patternLength);
          if (location >= 0) {
            UpdateFind(row, col, rowOffset, colOffset, location);
          }
          delete(text);
        }
      }

      //Attempt to scan for word, along the bottom of the grid.
      for (int col = 0; col < wordmapWidth; col++) {
        int row = wordmapHeight - 1;
        int rowOffset = -1;
        for (int colOffset = -1; colOffset < 2; colOffset++) {
          // Get our next text string, and attempt to match!
          text = buildTextString(col, row, 50, colOffset, rowOffset);
          location = BruteForceStringMatch(text, strlen(text), pattern, patternLength);
          if (location >= 0) {
            UpdateFind(row, col, rowOffset, colOffset, location);
          }
          delete(text);
        }
      }

      //Attempt to scan for word, along the left of the grid.
      for (int row = 0; row < wordmapHeight; row++) {
        int col = 0;
        int colOffset = 1;
        for (int rowOffset = -1; rowOffset < 2; rowOffset++) {
          // Get our next text string, and attempt to match!
          text = buildTextString(col, row, 50, colOffset, rowOffset);
          location = BruteForceStringMatch(text, strlen(text), pattern, patternLength);
          if (location >= 0) {
            UpdateFind(row, col, rowOffset, colOffset, location);
          }
          delete(text);
        }
      }

      //Attempt to scan for word, along the right of the grid.
      for (int row = 0; row < wordmapHeight; row++) {
        int col = wordmapWidth - 1;
        int colOffset = -1;
        for (int rowOffset = -1; rowOffset < 2; rowOffset++) {
          // Get our next text string, and attempt to match!
          text = buildTextString(col, row, 50, colOffset, rowOffset);
          location = BruteForceStringMatch(text, strlen(text), pattern, patternLength);
          if (location >= 0) {
            UpdateFind(row, col, rowOffset, colOffset, location);
          }
          delete(text);
        }
      }

      printf("%d %d\n", currentRow + 1, currentCol + 1);
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

