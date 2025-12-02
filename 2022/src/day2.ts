let moveMap = new Map<string, number>([
  ["A", 1],
  ["B", 2],
  ["C", 3],
  ["X", 1],
  ["Y", 2],
  ["Z", 3],
]);

let scoreMap = new Map<string, number>([
  ["XB", 0],
  ["XC", 6],
  ["YA", 6],
  ["YC", 0],
  ["ZA", 0],
  ["ZB", 6],
]);

let winningMap = new Map<string, string>([
  ["A", "Y"],
  ["B", "Z"],
  ["C", "X"],
]);
let losingMap = new Map<string, string>([
  ["A", "Z"],
  ["B", "X"],
  ["C", "Y"],
]);
let evenMap = new Map<string, string>([
  ["A", "X"],
  ["B", "Y"],
  ["C", "Z"],
]);

function main(input: string, part: string) {
  let rounds = input.split("\n");
  let sum = 0;
  for (let round of rounds) {
    sum +=
      part == "1" ? calcRoundScorePart1(round) : calcRoundScorePart2(round);
  }
  console.log(sum);
}

function calcRoundScorePart1(round: string): number {
  if (round.length == 0) return 0;
  let opponentMove = round.split(" ")[0];
  let myMove = round.split(" ")[1];

  // get corresponding move numbers
  let opponentMoveNum: number = moveMap.get(opponentMove)!;
  let myMoveNum: number = moveMap.get(myMove)!;

  let score: number = myMoveNum;

  if (myMoveNum == opponentMoveNum) score += 3;
  else score += scoreMap.get(myMove + opponentMove)!;

  console.log(score);
  return score;
}

function calcRoundScorePart2(round: string): number {
  if (round.length == 0) return 0;
  let opponentMove = round.split(" ")[0];
  let task = round.split(" ")[1];

  // get corresponding move numbers
  let opponentMoveNum: number = moveMap.get(opponentMove)!;
  let myMove: string = "";

  switch (task) {
    case "X": // lose
      myMove = losingMap.get(opponentMove)!;
      break;
    case "Y": // draw
      myMove = evenMap.get(opponentMove)!;
      break;
    case "Z": // win
      myMove = winningMap.get(opponentMove)!;
      break;
  }
  let myMoveNum: number = moveMap.get(myMove)!;

  let score: number = myMoveNum;

  if (myMoveNum == opponentMoveNum) score += 3;
  else score += scoreMap.get(myMove + opponentMove)!;

  console.log(score);
  return score;
}

export default main;
