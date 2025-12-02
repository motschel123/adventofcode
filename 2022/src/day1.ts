function main(input: string, part: string) {
  let sums: Array<number> = [];
  let groups = input.split("\n\n");

  for (let group of groups) {
    let sum = 0;
    let lines = group.split("\n");
    console.log(lines);
    for (let line of lines) {
      sum += parseInt(line);
    }
    sums.push(sum);
  }

  let top3 = sums.sort((a, b) => b - a).slice(0, 3);

  // sum the top 3
  let sum = 0;
  for (let num of top3) {
    sum += num;
  }
  console.log(sum);
}

export default main;
