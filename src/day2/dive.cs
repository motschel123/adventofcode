class Dive
{
    static void Main(string[] args)
    {
        // read input.txt
        string[] input = File.ReadAllLines("input.txt");
        int solution = 0;
        // choose which part of puzzle to solve
        if (args.Length == 1 && string.Compare(args[0], "2") == 0)
            solution = part2(input);
        else
            solution = part1(input);
        // output solution
        Console.WriteLine("Solution: " + solution.ToString());
    }

    static int part1(string[] input)
    {
        // vars
        int horz = 0;
        int depth = 0;
        // calculate solution
        foreach (string line in input)
        {
            string[] tmp = line.Split(' ');
            int tmpInt = int.Parse(tmp[1]);
            switch (tmp[0])
            {
                case "forward":
                    horz += tmpInt;
                    break;
                case "down":
                    depth += tmpInt;
                    break;
                case "up":
                    depth -= tmpInt;
                    break;
                default:
                    Console.WriteLine("NONONO");
                    break;
            }
        }
        return horz * depth;
    }

    static int part2(string[] input)
    {
        // vars
        int aim = 0;
        int horz = 0;
        int depth = 0;
        // compute solution
        foreach (string line in input)
        {
            string[] tmp = line.Split(' ');
            int tmpInt = int.Parse(tmp[1]);
            switch (tmp[0])
            {
                case "forward":
                    horz += tmpInt;
                    depth += aim * tmpInt;
                    break;
                case "down":
                    aim += tmpInt;
                    break;
                case "up":
                    aim -= tmpInt;
                    aim = Math.Max(0, aim);
                    break;
                default:
                    break;
            }
        }
        return horz * depth;
    }
}