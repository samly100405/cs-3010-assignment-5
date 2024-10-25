# Numerical integration and differentiation

# How to run

## Running manually

1. Make sure you have maven (mvm) installed on your system.
2. Navigate to project root and run `mvn install`.
3. The compiled `.jar` file will be located in the `/target` directory.
4. Use `java -jar target/cs3010-assignment-5-1.0-SNAPSHOT.jar <input file>`. *Note: the other .jar file doesn't work*
5. Solutions and running time will be printed to `stdout`.

## Using provided scripts

1. Make sure you have run `mvn install`.
2. `python3 generate_polynomials.py <degree> <count>` generate `count` polynomials of degree `degree`.
3. `python3 solve.py <n>` will evaluate *n* polynomials `func1.pol, func2.pol, ...` at 10 random points each.