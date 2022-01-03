#include <iostream>
#include <string>

int main(int argc, char const *argv[])
{
    // parse argv to change sliding amount (no args defaults to 1)
    const int slidingAmount = argc == 2 ? std::stoi(argv[1]) : 1;

    int values[slidingAmount];

    int incrSolution = 0;

    int slidingPosition = 0;
    bool valuesFilled = false;
    int dragger = INT_MAX;
    int curr;
    for (std::string line; std::getline(std::cin, line);)
    {
        values[slidingPosition] = std::stoi(line);

        if (++slidingPosition == slidingAmount)
        {
            slidingPosition = 0;
            valuesFilled = true;
        }

        if (valuesFilled)
        {
            curr = 0;
            for (int v : values)
            {
                curr += v;
            }

            if (curr > dragger)
                incrSolution++;

            dragger = curr;
        }
    }

    std::cout << "Solution: " << incrSolution << std::endl;
}
