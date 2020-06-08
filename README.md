## primality
primality testing for big numbers in Java - just exercising lang usage

**to recompile source tree**
```
$ javac src/org/eclipse/primality/Prime2.java
$ javac src/org/eclipse/primality/Test.java
...
$ java bin/org/eclipse/primality/Test
```

**included**
- implementation of optimized trial division 
- deterministic Miller primality test

the former will run for numbers under a certain size `primality.Prime.threshold`.

```java
import primality.Prime2;
...
final boolean prime = Prime2.isPrime(3829138120L);
```
