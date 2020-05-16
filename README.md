## primality
primality testing for big numbers in Java - just exercising lang usage

**to recompile source tree**
```
$ cd src/org/eclipse/primality
$ javac Prime.java Test.java
...
$ java Test
```

**included**
- implementation of optimized trial division 
- deterministic Miller primality test

```java
import primality.Prime
...
    Prime p = new Prime();
    p.set(3829138120L);
    
    System.out.println(p.isPrime());
```
