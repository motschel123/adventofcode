#include <iostream>
#include <string>
#include <vector>
#include <array>
#include <sstream>
#include <algorithm>

#define RESET "\033[0m"
#define YELLOW "\033[33m"
#define RED "\033[31m"

#define NUMBERS_PICKED_INIT {{false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}};

typedef struct board
{
    int numbers[5][5]{};
    bool numbers_picked[5][5] = NUMBERS_PICKED_INIT;
} board;

void printBoard(board b)
{
    for (int i = 0; i < 5; i++)
    {
        for (int j = 0; j < 5; j++)
        {
            if (b.numbers_picked[i][j])
                std::cout << YELLOW;

            std::cout << b.numbers[i][j];

            std::cout << RESET;
            std::cout << ',';
        }
        std::cout << '\n';
    }
    std::cout << '\n';
}

std::vector<int> parseNumbersDrawn(std::string inputLine)
{
    std::vector<int> drawnNums;
    std::stringstream ss(inputLine);
    int tmp_num;

    while (ss >> tmp_num)
    {
        drawnNums.push_back(tmp_num);
        if (ss.peek() == ',')
            ss.ignore();
    }

    return drawnNums;
}

board parseBoardFromStdIn()
{
    board board;
    int row = 0;
    int col = 0;
    int tmp;
    std::string line;

    for (int i = 0; i < 5; i++)
    {
        std::getline(std::cin, line);
        std::stringstream ss(line);
        while (ss >> tmp)
        {
            board.numbers[row][col] = tmp;
            col++;
            if (col == 5)
            {
                row++;
                col = 0;
            }
            if (ss.peek() == ' ')
                ss.ignore();
        }
    }

    return board;
}

long calcPoints(board b, int numCalled)
{
    int sum = 0;
    for (int i = 0; i < 5; i++)
    {
        for (int j = 0; j < 5; j++)
        {
            if (!b.numbers_picked[i][j])
            {
                sum += b.numbers[i][j];
            }
        }
    }
    return sum * numCalled;
}

bool boardWon(board b)
{
    for (int i = 0; i < 5; i++)
    {
        bool rowWin = true;
        bool colWin = true;
        for (int j = 0; j < 5; j++)
        {
            if (!b.numbers_picked[i][j])
                rowWin = false;
            if (!b.numbers_picked[j][i])
                colWin = false;
        }
        if (rowWin || colWin)
            return true;
    }
    return false;
}

int main(int argc, char const *argv[])
{
    std::string lineBuffer;

    // parse drawn numbers
    if (!std::getline(std::cin, lineBuffer))
        return -1;

    auto drawnNums = parseNumbersDrawn(lineBuffer);

    // parse boards
    std::vector<board> boards;
    while (!std::cin.eof())
    {
        std::getline(std::cin, lineBuffer);

        if (lineBuffer.length() == 0)
            boards.push_back(parseBoardFromStdIn());
        else
            std::cout << RED << "Unexpected line: " << lineBuffer << RESET << std::endl;
    }

    // calculate first and last winner
    int numberCalledFirstWinner = -1;
    int numberCalledLastWinner = -1;
    std::vector<int> wonBoards;
    for (int num : drawnNums)
    {
        std::cout << "-------- " << num << std::endl;
        for (int i = 0; i < boards.size(); i++)
        {
            if (std::find(wonBoards.begin(), wonBoards.end(), i) != wonBoards.end())
                continue;
            board &b = boards.at(i);
            printBoard(b);
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                {
                    if (b.numbers[i][j] == num)
                        b.numbers_picked[i][j] = true;
                }
            if (boardWon(b))
            {
                if (numberCalledFirstWinner == -1)
                    numberCalledFirstWinner = num;
                wonBoards.push_back(i);
            }
            numberCalledLastWinner = num;
        }
    }
    std::cout << "First Winner: \n";
    printBoard(boards.at(wonBoards.front()));
    std::cout << "Final Score: " << calcPoints(boards.at(wonBoards.front()), numberCalledFirstWinner) << std::endl
              << std::endl;

    std::cout << "Last Winner: \n";
    printBoard(boards.at(wonBoards.back()));
    std::cout << "Final Score: " << numberCalledLastWinner << "|" << calcPoints(boards.at(wonBoards.back()), numberCalledLastWinner) << std::endl
              << std::endl;

    return 0;
}