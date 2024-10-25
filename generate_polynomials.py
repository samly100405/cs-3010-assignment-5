import sys
import random

def main():
    degree = int(sys.argv[1])
    count  = int(sys.argv[2])

    for i in range(count):
        f = open(f'data/func{i+1}.pol', 'w')
        f.write(str(degree) + '\n')
        for _ in range(degree+1):
            f.write(str(random.randrange(-99, 99)) + " ")

        f.close()


if __name__ == '__main__':
    main()