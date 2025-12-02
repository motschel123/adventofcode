#include <iostream>
#include <vector>
#include <string>
#include <numeric>

char commonChar(std::vector<char> column, char ifEquals)
{
    int zero_counter = 0;
    for (char c : column)
        if (c == '0')
            zero_counter++;
    if (zero_counter > column.size() / 2) // common '0'
        return '0';
    else if (zero_counter < column.size() / 2) // common '1'
        return '1';
    else // equally common
        return ifEquals;
}

/*std::string filterInput(std::vector<std::vector<char> > inputCopy, bool isMostCommon, char ifEquals)
{
    int colNum = 0;
    while (inputCopy[0].size() > 1)
    {
        const char comChar = commonChar(inputCopy[colNum], ifEquals);
        for (int line : validLineNumbers)
        {
            std::cout << inputCopy[0][line] << inputCopy[1][line] << inputCopy[2][line] << inputCopy[3][line] << inputCopy[4][line] << std::endl;
        }
        std::cout << "Common char: " << comChar << "Col: " << colNum << std::endl;

        for (int i = 0; i < inputCopy[0].size(); i++)
        {
            const bool remove = inputCopy
        }
        column.erase(
            std::remove_if(
                validLineNumbers.begin(),
                validLineNumbers.end(),
                [input, isMostCommon, comChar, colNum](int element) -> bool
                {
                    return (input[colNum][element] != comChar) == isMostCommon;
                }),
            validLineNumbers.end());
        for (int line : validLineNumbers)
        {
            std::cout << input[0][line] << input[1][line] << input[2][line] << input[3][line] << input[4][line] << std::endl;
        }
        std::cout << "-----" << std::endl;
        colNum++;
    }

    std::string leftOverBinary;
    for (std::vector<char> col : input)
    {
        leftOverBinary.push_back(col[validLineNumbers[0]]);
    }
    return leftOverBinary;
}*/

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

    // compute gamma & epsilon
    std::string binaryGamma;
    std::string binaryEpsilon;
    for (std::vector<char> column : input)
    {
        const char comChar = commonChar(column, '0');
        binaryGamma.push_back(comChar);
        binaryEpsilon.push_back((comChar == '0') ? '1' : '0');
    }
    // parse to int
    int gamma = std::stoi(binaryGamma, nullptr, 2);
    int epsilon = std::stoi(binaryEpsilon, nullptr, 2);
    // power consumption
    std::cout << "Power consumption: " << gamma * epsilon << std::endl;

    /*// preparation for part 2
    // oxygen most common & 1 if equal amounts
    // co2 least common & 0 if equal amounts

    // compute oxygen rating
    const std::string oxygenBinary = filterInput(input, true, '1');
    // compute co2 rating
    const std::string co2Binary = filterInput(input, false, '0');

    const int oxygen = std::stoi(oxygenBinary, nullptr, 2);
    const int co2 = std::stoi(co2Binary, nullptr, 2);

    std::cout << "Oxy: " << oxygenBinary << std::endl;
    std::cout << "CO2: " << co2Binary << std::endl;

    std::cout << "Life Support Rating: " << oxygen * co2 << std::endl;
*/
    return 0;
}