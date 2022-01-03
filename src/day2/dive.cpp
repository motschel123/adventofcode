#include <iostream>
#include <string>
#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    int hPos = 0;
    int depth_aim = 0;
    int depth = 0;
    int aim = 0;

    for (std::string line; std::getline(std::cin, line);)
    {
        const size_t posSpace = line.find_first_of(' ');

        const int amount = std::stoi(line.substr(posSpace, line.length()));
        const std::string command = line.substr(0, posSpace);

        if (strcmp(command.c_str(), "forward") == 0)
        {
            hPos += amount;
            depth_aim += aim * amount;
        }
        else if (strcmp(command.c_str(), "up") == 0)
        {
            aim -= amount;
            depth -= amount;
        }
        else if (strcmp(command.c_str(), "down") == 0)
        {
            aim += amount;
            depth += amount;
        }
    }
    std::cout << "Solution without using aim (Part 1): " << depth * hPos << std::endl;

    std::cout << "Solution using aim (Part 2): " << depth_aim * hPos << std::endl;
    return 0;
}
