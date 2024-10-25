import os
import sys
import random

def main():
    for i in range(1, int(sys.argv[1]) + 1):
        inputs = [('d' if random.random() > 0.5 else 'i', (random.random() - 1) * 100) for _ in range(10)]

        input = ""
        for (op, val) in inputs:
            input = input + str(op) + " " + str(val) + " "
        input += 'q'

        os.system(f'echo "{input}" | java -jar target/cs3010-assignment-5-1.0-SNAPSHOT-runnable.jar data/func{i}.pol >> data/output{i}.txt')


if __name__ == '__main__':
    main()