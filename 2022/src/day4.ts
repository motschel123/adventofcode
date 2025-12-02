function part1(input: string) {
  const pairs = input.split("\n");

  let count = 0;

  // Iterate through each pair of section assignments
  for (const pair of pairs) {
    // Split the pair into its two ranges
    const [range1, range2] = pair.split(",");

    // Extract the start and end of each range
    const [start1, end1] = range1.split("-").map(Number);
    const [start2, end2] = range2.split("-").map(Number);

    // Check if one range fully contains the other
    if (
      (start1 <= start2 && end1 >= end2) ||
      (start2 <= start1 && end2 >= end1)
    ) {
      count++;
    }
  }

  console.log(count); // Outputs 2
}

function part2(input: string) {
  const pairs = input.split("\n");

  let count = 0;

  // Iterate through each pair of section assignments
  for (const pair of pairs) {
    // Split the pair into its two ranges
    const [range1, range2] = pair.split(",");

    // Extract the start and end of each range
    const [start1, end1] = range1.split("-").map(Number);
    const [start2, end2] = range2.split("-").map(Number);

    // Check if the ranges overlap
    if (start1 <= end2 && start2 <= end1) {
      count++;
    }
  }

  console.log(count); // Outputs 4
}

function main(input: string, part: string) {
  if (part === "1") {
    part1(input);
  } else if (part === "2") {
    part2(input);
  }
}

export default main;
