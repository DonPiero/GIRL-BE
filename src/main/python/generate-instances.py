import random
import copy


def is_solvable(puzzle):
    inversions = 0
    for i in range(len(puzzle)):
        for j in range(i + 1, len(puzzle)):
            if puzzle[i] != 0 and puzzle[j] != 0 and puzzle[i] > puzzle[j]:
                inversions += 1
    return inversions % 2 == 0


def only_one_occurrence(puzzle):
    puzzle_copy = copy.deepcopy(puzzle)
    puzzle_copy.sort()
    return puzzle_copy == list(range(0,9))


def main():
    n = 100
    i = 0
    res = []
    while i <= n:
        puzzle = random.sample(range(0,9),9)
        if is_solvable(puzzle) and only_one_occurrence(puzzle):
            res.append(puzzle)
            i = i + 1
    # print(",".join(map(str, res)))
    print(res)


if __name__ == "__main__":
    main()