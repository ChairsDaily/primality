## primality
primality testing for big numbers in Java - just exercising lang usage

**to recompile source tree**
```
$ cd src/org/eclipse/
$ javac primality/*.java
...
$ java Test
```

**included**
- implementation of optimized trial division 
- deterministic Miller primality test

the former will run for numbers under a certain size `primality.Prime.threshold`.

```java
import primality.Prime;
...
    Prime p = new Prime();
    p.set(3829138120L);
    
    System.out.println(p.isPrime());
```
