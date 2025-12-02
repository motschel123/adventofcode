let map = new Map<string, any>();
let currentDir = "";

function runCd(param: string) {
  if (param == "..") {
    let dirList = currentDir.split("/").slice(undefined, -1);
    dirList.pop();
    currentDir = dirList.join("/");
    currentDir += "/";
  } else if (param == "/") {
    currentDir = "/";
  } else {
    currentDir += `${param}/`;
  }
}

function calculateDirSize(map: Map<string, any>, summedDirs: number) {
  let size = 0;
  for (let [key, value] of map) {
    if (typeof value === "number") {
      size += value;
    } else if (value instanceof Map<string, any>) {
      let [dirSize, nextSummedDirs] = calculateDirSize(value, summedDirs);
      summedDirs = nextSummedDirs;
      size += dirSize;
    }
  }
  if (size < 100000) summedDirs += size;

  return [size, summedDirs];
}

function listOfDirsBiggerThen(
  size: number,
  result: number[],
  neededSpace: number
) {
  for (let [key, value] of map) {
    if (typeof value === "number") {
      if (value > size) result.push(value);
    } else if (value instanceof Map<string, any>) {
      let [dirSize, nextResult] = listOfDirsBiggerThen(
        size,
        result,
        neededSpace
      );
      size += dirSize as number;
      result = nextResult as number[];
    }
  }
  if (size > neededSpace) {
    result.push(size);
  }
  return [size, result];
}

function main(input: string, part: string) {
  let readFiles = false;

  for (let line of input.split("\n")) {
    if (line.charAt(0) === "$") {
      readFiles = false;
      // command
      let [_, cmd, param] = line.split(" ");
      if (cmd === "cd") {
        // cd
        runCd(param);
      } else {
        // ls
        readFiles = true;
        continue;
      }
    }
    if (readFiles) {
      // file
      let [type_size, name] = line.split(" ");
      let currMap = map;
      for (let dir of currentDir.split("/").slice(1, -1)) {
        currMap = currMap.get(dir);
      }
      if (type_size == "dir") {
        // dir
        currMap.set(`${name}`, new Map<string, any>());
      } else {
        // file
        currMap.set(name, parseInt(type_size));
      }
    }
  }

  if (part == "1") {
    let [_, result] = calculateDirSize(map, 0);
    console.log(result);
  } else {
    let diskSpace = 70000000;
    let updateSize = 30000000;
    let [usedSpace, _] = calculateDirSize(map, 0);
    let freeSpace = diskSpace - usedSpace;
    let neededSpace = updateSize - freeSpace;

    let [__, result] = listOfDirsBiggerThen(0, [], neededSpace);
    console.log(Math.min(...(result as number[])));
  }
}

export default main;
