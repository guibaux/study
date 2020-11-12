#include <iostream>
#include <array>
#include <cstdlib>
#include <ctime>
#include <memory>
#include <vector>

// Activation Function
int sign(int n){
    if (n >= 0) return 1;
    else return -1;
}

struct Perceptron {
    std::vector<double> weights = {0, 0}; 
    float rate = 2;

    Perceptron(){
        srand(time(NULL));
        for(auto& e : weights){
                e = rand() ;
        }
    }
    int guess(double inputs[]) {
        double sum{0};
        for(int i{0}; i < weights.size(); i++){
            sum += inputs[i] * weights.at(i);
        }
        int output = sign(sum);
        return output;
    }

    void train(double inputs[], int target){
        int guess = this->guess(inputs);
        int error = target - guess;

        for(int i{0}; i < weights.size(); i++){
            weights[i] += inputs[i] * error * rate;
            
            std::cout << inputs[i] * error * rate << '\n';
            //std::cout << weights[i] << '\n';
        }
        
    }
};

struct Point {
    int x;
    int y;
    int label;

    Point(int width, int height)
    : x{rand() % width}, y{rand() % height}{
        if(x > y) this->label = 1;
        else this->label = -1;
    }

};

int main() {
    auto p = std::make_unique<Perceptron>();
    std::vector<std::unique_ptr<Point>> points;
    for(int i = 0; i < 100; i++){
        points.push_back(std::make_unique<Point>(300,300));
    };
    
    int corrects = 0;
    int wrongs = 0;
    
    for(auto& pt : points){
        double inputs[] = {static_cast<double>(pt->x), static_cast<double>(pt->y)};
        p->train(inputs, pt->label);
        int guess = p->guess(inputs); 
        if(guess == pt->label) corrects++;
        else wrongs++;
    }

    std::cout << "Corrects: " << corrects << '\n';
    std::cout << "Wrongs: " << wrongs << '\n';

    return 0;
}

