#include <iostream>
#include <vector>
#include <string>
#include <numeric>

int main(int argc, char const *argv[])
{
    std::vector<std::vector<char> > input;
    int lineLen = 0;
    // init vector for each bit of first input line
    std::string line;
    if (!std::getline(std::cin, line))
        return 0;
    for (char c : line)
    {
        input.push_back({c});
        lineLen++;
    }
    // parse input
    for (; std::getline(std::cin, line);)
    {
        for (int i = 0; i < lineLen; i++)
        {
            input[i].push_back(line[i]);
        }
    }

    // counting
    std::string binaryGamma;
    std::string binaryEpsilon;
    for (std::vector<char> column : input)
    {
        int zero_counter = 0;
        for (char c : column)
        {
            if (c == '0')
                zero_counter++;
        }
        if (zero_counter > column.size() / 2) // common '0'
        {
            binaryGamma.push_back('0');
            binaryEpsilon.push_back('1');
        }
        else // common '1'
        {
            binaryGamma.push_back('1');
            binaryEpsilon.push_back('0');
        }
    }
    int gamma = std::stoi(binaryGamma, nullptr, 2);
    int epsilon = std::stoi(binaryEpsilon, nullptr, 2);

    std::cout << "Power consumption: " << gamma * epsilon << std::endl;

    return 0;
}