## brief analysis
The naive method involves testing for divisibility among 
all successive integers, by checking whether`s | n` 
holds for all  `2,...,sqrt(n)`. The improved upper-
bound is provided easily. Take the conjecture that all 
divisors of `n` are less than or equal to `n / 2`. 

**Theorem**. If `n` has prime divisors 
`s,t` where `st = n`, and `s <= t`, then `s <= sqrt(n)`. <br>
**Proof**. Assume `s > sqrt(n)`. It follows that 
`t >= s > sqrt(n)` and `st > n`, which directly
contradicts `st = n`. Thus, `s <= sqrt(n)` must be true. <br>

Where `s | n` is equivelant to `s = 0 (mod n)`, assuming
`=` represents congruency.

## computational complexity
Our naive trial division performs exactly `sqrt(n) - 2` steps. 
At each step, variable-time work is done. Finding remainder
after division between `n` and `s`, `r`, is simply `s = r (mod n)`
or syntactically `n % s`. Division does `O(m^2)` work, `m` 
as the size in digits. In base-10, any `n` is exactly `log (n) + 1`
digits, where `1` is constant and therefore negligible. Thus, division
in this case does ~`o(log (n)^2)` work. Done ~`sqrt(n)` times, 
`O(n^0.5 log (n)^2)` effectively models the growth of time in
the worst case relative to `n` for the unoptimized trial division
primality test.

## mathematical improvements
Naive trial division can be improved by examining the case
of even composites. If any even `s` divides `n`, so can `2`.
Thus, checking for divisibility between `n` and `2`, and skipping
every other successive integer onward from `2` reduces the steps by
1/2. Modeling this improved complexity is trivial `O((n^0.5)/2 log (n)^2)`. 
This improvement might be considered negligible relative to the worst case 
model, however pragmatically it yields an impressive loss in runtime.

Building off of the special case of evens, it is observed that all
primes `> 3` are of the form `6k +- 1`. This is because all integers can
be expressed `6k + i` for some `k` and `i` in `i = -1,...,4`; `2` will
divide all `6k + (0, 2, 4,)`, and `3` will divide all `6k + 3`, leaving only
`6k + 1`. It follows that in checking for divisibility among successive
integers, `6k + 1 <= sqrt(n) - 2` alone are to be checked. This is 3 times faster
than the naive method, and around `33%` faster than the previous improvement
noting the special case of evens. 

## programmatic improvements
It is trivial to generate a table of primes up to some pre-specified limit
and first check for divisibility. If `p = 0 (mod n)` stands for any prime `p`,
then `n` is surely composite, thus achieving early termination.
