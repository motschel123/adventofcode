import * as fs from "fs";

if (process.argv.length <= 2) {
  console.log("Please provide a day to run");
  process.exit(1);
}

let day: string = `day${process.argv[2]}`;
let part: string = process.argv[3] || "1";
let input: string = readFromFile(`./input/${day}.txt`);

import(`./${day}`).then((module) => {
  console.log(`Running ${day}`);

  module.default(input, part);
});

function readFromFile(filepath: string): string {
  return fs.readFileSync(filepath, "utf8");
}
