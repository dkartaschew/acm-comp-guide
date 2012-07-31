#include <iostream>

using namespace std;


int main() {

   int a, b, c;
   cin >> a;

   while (a != 0) {

      cin >> b >> c;

      // check progression: gap between a and b is same as between b and c
      if (b - a == c - b) {
         cout << a << " " << b << " " << c << " yes" << endl;
      } else {
         cout << a << " " << b << " " << c << " NO" << endl;
      }

      // get next line
      cin >> a;
   }
}
