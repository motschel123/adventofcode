function main(input: string, part: string) {
  let batchSize = part == "2" ? 14 : 4;
  outer: for (let i = batchSize; i < input.length; i++) {
    let string = input.slice(i - batchSize, i);

    const uniqueChars = new Set<string>();

    console.log(string);
    // Iterate over each character in the string
    for (const char of string) {
      // If the character is already in the set, it is a duplicate
      if (uniqueChars.has(char)) {
        continue outer;
      }
      // Otherwise, add the character to the set
      uniqueChars.add(char);
    }
    console.log(i);
    return;
  }
}

export default main;
