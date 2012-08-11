using namespace std;

#include <time.h> 
#include <stdio.h>
#include <iostream>

char array1[1024][1204];
char array2[2048][2048];
char array3[4096][4096];
char array4[8192][8192];
char array5[16384][16384];

int main() {

  // Row by row.
  clock_t start = clock();
  for (int y = 0; y < 1024; y++) {
    for (int x = 0; x < 1023; x++) {
      array1[y][x] = array1[y][x + 1];
    }
  }
  clock_t end = clock();
  long elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("1MB row time = %dmsec\n", elapsed);

  // Column by Column
  start = clock();
  for (int x = 0; x < 1024; x++) {
    for (int y = 0; y < 1023; y++) {
      array1[y][x] = array1[y + 1][x];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("1MB col time = %dmsec\n", elapsed);

  // 4MB Row by row.
  start = clock();
  for (int y = 0; y < 2048; y++) {
    for (int x = 0; x < 2047; x++) {
      array2[y][x] = array2[y][x + 1];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("4MB row time = %dmsec\n", elapsed);

  // Column by Column
  start = clock();
  for (int x = 0; x < 2048; x++) {
    for (int y = 0; y < 2047; y++) {
      array2[y][x] = array2[y + 1][x];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("4MB col time = %dmsec\n", elapsed);

  // 16MB Row by row.
  start = clock();
  for (int y = 0; y < 4096; y++) {
    for (int x = 0; x < 4095; x++) {
      array3[y][x] = array3[y][x + 1];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("16MB row time = %dmsec\n", elapsed);

  // Column by Column
  start = clock();
  for (int x = 0; x < 4096; x++) {
    for (int y = 0; y < 4095; y++) {
      array3[y][x] = array3[y + 1][x];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("16MB col time = %dmsec\n", elapsed);

  // 64MB Row by row.
  start = clock();
  for (int y = 0; y < 8192; y++) {
    for (int x = 0; x < 8191; x++) {
      array4[y][x] = array4[y][x + 1];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("64MB row time = %dmsec\n", elapsed);

  // Column by Column
  start = clock();
  for (int x = 0; x < 8192; x++) {
    for (int y = 0; y < 8191; y++) {
      array4[y][x] = array4[y + 1][x];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("64MB col time = %dmsec\n", elapsed);
  
  // 256MB Row by row.
  start = clock();
  for (int y = 0; y < 16384; y++) {
    for (int x = 0; x < 16383; x++) {
      array5[y][x] = array5[y][x + 1];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("256MB row time = %dmsec\n", elapsed);

  // Column by Column
  start = clock();
  for (int x = 0; x < 16384; x++) {
    for (int y = 0; y < 16383; y++) {
      array5[y][x] = array5[y + 1][x];
    }
  }
  end = clock();
  elapsed = (long) (end - start) / (CLOCKS_PER_SEC / 1000);
  printf("256MB col time = %dmsec\n", elapsed);

  return 0;
}

