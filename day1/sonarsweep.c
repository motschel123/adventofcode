#include "stdio.h"
#include "stdlib.h"
#include "sys/types.h"
#include "limits.h"
#include "errno.h"
#include "string.h"

int puzzle1(const long nums[], int length)
{
    // edge case with to little input
    if (length < 2)
        return 0;
    // vars
    long cur = 0;
    long dragger = 0;
    int counter = 0;
    int i = 0;
    // loop through all nums and count increases in numbers
    cur = nums[0];
    while (++i < length)
    {
        dragger = cur;
        cur = nums[i];

        if (dragger < cur)
        {
            counter++;
        }
    }
    return counter;
}

int puzzle2(const long nums[], int length)
{
    // edge case with to little input
    if (length < 4)
        return 0;
    // vars
    int counter = 0;
    long dragger_last_3 = nums[0] + nums[1] + nums[2];
    long drag2 = nums[1];
    long drag1 = nums[2];

    // loop through nums and count times where 3-pairs are increasing
    int i = 2;
    while (++i < length)
    {
        long num = nums[i];
        if (num + drag1 + drag2 > dragger_last_3)
        {
            counter++;
        }

        dragger_last_3 = num + drag1 + drag2;
        drag2 = drag1;
        drag1 = num;
    }

    return counter;
}

int main(int argc, char **argv)
{
    // switch puzzles with argument for argv
    int puzzle = 1;
    if (argc == 2)
    {
        if (strcmp(argv[1], "2") == 0)
        {
            puzzle = 2;
        }
    }
    // vars
    char buffer[6];
    int used = 0;
    int length = 50;
    long *nums = malloc(sizeof(long) * length);
    if (nums == NULL)
    {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    // read input file
    // parse to long and store in nums
    while (fgets(buffer, sizeof(buffer), stdin) != NULL)
    {
        errno = 0;
        long num = strtol(buffer, (char **)NULL, 10);
        if (errno != 0)
        {
            perror("strtol");
            exit(EXIT_FAILURE);
        }
        // realloc nums with more space if length of num is all used
        if (used >= length)
        {
            length += 50;
            nums = realloc(nums, sizeof(long) * length);
            if (nums == NULL)
            {
                perror("realloc");
                exit(EXIT_FAILURE);
            }
        }
        nums[used++] = num;
    }
    if (!feof(stdin))
    {
        perror("fgets");
        exit(EXIT_FAILURE);
    }

    // switch puzzles according to argv[1] if set

    int count;
    if (puzzle == 2)
    {
        count = puzzle2(nums, used);
    }
    else
    {
        count = puzzle1(nums, used);
    }

    printf("Counted: %i increases", count);

    free(nums);
    exit(EXIT_SUCCESS);
}
