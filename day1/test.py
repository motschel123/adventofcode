# only for puzzel1 a)

with open("input.txt", "r") as file:
    values = file.read()
    values = values.split('\n')

    drag: int = None
    i: int = 0
    for x in values:
        if drag != None and drag < x:
            i += 1

        drag = x

    print(i)
