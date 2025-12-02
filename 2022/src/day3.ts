function part1(input: string) {
  // Function to calculate the priority of an item type (a lowercase letter has
  // priority 1-26, an uppercase letter has priority 27-52)
  const getItemPriority = (item: string): number => {
    const code = item.charCodeAt(0);
    if (code >= "a".charCodeAt(0) && code <= "z".charCodeAt(0)) {
      // Lowercase letter
      return code - "a".charCodeAt(0) + 1;
    } else if (code >= "A".charCodeAt(0) && code <= "Z".charCodeAt(0)) {
      // Uppercase letter
      return code - "A".charCodeAt(0) + 27;
    } else {
      // Invalid input
      return 0;
    }
  };
  let sum = 0;

  // split a given string into an array of items at newline
  let rucksacks = input.split("\n");

  // Iterate through each rucksack
  for (const rucksack of rucksacks) {
    // Split the rucksack into its two compartments
    const comp1 = rucksack.slice(0, rucksack.length / 2);
    const comp2 = rucksack.slice(rucksack.length / 2);

    // Find the common item types in the two compartments
    for (const item of comp1) {
      if (comp2.includes(item)) {
        // This item type appears in both compartments, so add its priority to the sum
        sum += getItemPriority(item);
        break;
      }
    }
  }

  console.log(sum); // Outputs 157
}

function part2(input: string) {
  const rucksacks = input.split("\n");

  // Function to calculate the priority of an item type (a lowercase letter has
  // priority 1-26, an uppercase letter has priority 27-52)
  const getItemPriority = (item: string): number => {
    const code = item.charCodeAt(0);
    if (code >= "a".charCodeAt(0) && code <= "z".charCodeAt(0)) {
      // Lowercase letter
      return code - "a".charCodeAt(0) + 1;
    } else if (code >= "A".charCodeAt(0) && code <= "Z".charCodeAt(0)) {
      // Uppercase letter
      return code - "A".charCodeAt(0) + 27;
    } else {
      // Invalid input
      return 0;
    }
  };

  let sum = 0;

  // Split the list of rucksacks into groups of three
  const groups = [];
  for (let i = 0; i < rucksacks.length; i += 3) {
    groups.push(rucksacks.slice(i, i + 3));
  }

  // Iterate through each group of rucksacks
  for (const group of groups) {
    // Find the common item types in all three rucksacks in the group
    for (const item of group[0]) {
      if (group[1].includes(item) && group[2].includes(item)) {
        // This item type appears in all three rucksacks, so add its priority to the sum
        sum += getItemPriority(item);
        break;
      }
    }
  }

  console.log(sum); // Outputs 70
}

function main(input: string, part: string) {
  if (part === "1") {
    part1(input);
  } else if (part === "2") {
    part2(input);
  }
}

export default main;
