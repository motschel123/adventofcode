function decodeMove(line: string) {
  let arr: string[] = line.split(" ");
  let amount = parseInt(arr[1]);
  let from = parseInt(arr[3]) - 1;
  let to = parseInt(arr[5]) - 1;
  return [amount, from, to];
}

function parseCrates(creates: string): string[][] {
  let lines = creates.split("\n");
  let stacks: string[][] = lines[lines.length - 1]
    .split("   ")
    .map((_) => Array());

  for (let i = lines.length - 2; i >= 0; i--) {
    let line = lines[i];
    for (let j = 0; j < stacks.length; j++) {
      let char = line.charAt(1 + j * 4);
      if (char != " ") stacks[j].push(char);
    }
  }

  return stacks;
}

function part1(input: string) {
  // Part 1
  let [creates, moves] = input.split("\n\n");

  let stacks = parseCrates(creates);

  for (let move of moves.split("\n")) {
    let [amount, from, to] = decodeMove(move);
    amount = Math.min(amount, stacks[from].length);
    for (let i = 0; i < amount; i++) {
      stacks[to].push(stacks[from].pop()!);
    }
  }

  let result = "";
  for (let stack of stacks) {
    if (stack.length > 0) result += stack[stack.length - 1];
  }

  console.log(result);
  return 0;
}

function part2(input: string) {
  // Part 1
  let [creates, moves] = input.split("\n\n");

  let stacks = parseCrates(creates);

  for (let move of moves.split("\n")) {
    let [amount, from, to] = decodeMove(move);
    amount = Math.min(amount, stacks[from].length);

    stacks[to] = stacks[to].concat(
      stacks[from].splice(stacks[from].length - amount, amount)
    );
  }

  let result = "";
  for (let stack of stacks) {
    if (stack.length > 0) result += stack[stack.length - 1];
  }

  console.log(result);
  return 0;
}

function main(input: string, part: string) {
  if (part === "1") {
    part1(input);
  } else {
    part2(input);
  }
}

export default main;
