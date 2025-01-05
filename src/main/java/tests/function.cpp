#include <iostream>
#include <vector>


int fun(int a, int b){

    int z = a + b;

    return z;
}


int main(int argv, char* argv){


    int a = 5;
    int b = 10;

    int z = fun(a,b);

    std::cout << "This is sum :" << z << std::endl;


    return 0;
}