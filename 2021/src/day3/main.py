def find_common(verts: list[str], if_equals: str):
    common_bits: str = ""

    for vert in verts:
        if vert.count("1") > vert.count("0"):
            common_bits += "1"
        elif vert.count("1") < vert.count("0"):
            common_bits += "0"
        else:
            common_bits += if_equals

    return common_bits


def parse_horz_to_vert(horz: list[str]):
    # convert to vertical str list
    vs: list[str] = [""] * (horz[0].__len__()-1)

    for line in horz:
        for i in range(line.__len__()):
            if(line[i] != '\n'):
                vs[i] += line[i]

    return vs


with open("./src/day3/input.txt", "r") as file:
    lines_horz: list[str] = file.readlines()
    lines_vert = parse_horz_to_vert(lines_horz.copy())

    common_bits: str = find_common(lines_vert, "1")

    # parse to int
    gamma: int = int(common_bits, 2)
    epsilon: int = gamma ^ int("1"*common_bits.__len__(), 2)

    print("part1: ", gamma*epsilon)

    # PART 2
    lines_oxy: list[str] = lines_horz.copy()
    lines_co2: list[str] = lines_horz.copy()

    # filter based on common bit
    for i in range(common_bits.__len__()):
        if len(lines_co2) > 1:
            co2_common = find_common(parse_horz_to_vert(lines_co2), "1")
            for line in lines_co2.copy():
                if len(lines_co2) > 1 and line[i] == co2_common[i]:
                    lines_co2.remove(line)
        if len(lines_oxy) > 1:
            oxy_common = find_common(parse_horz_to_vert(lines_oxy), "1")
            for line in lines_oxy.copy():
                if len(lines_oxy) > 1 and line[i] != oxy_common[i]:
                    lines_oxy.remove(line)

    oxy: int = int(lines_oxy[0], 2)
    co2: int = int(lines_co2[0], 2)

    print("part2: ", oxy*co2)

    exit()
