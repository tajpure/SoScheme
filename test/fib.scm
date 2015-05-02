;(define fib (lambda (n) (if (or (= n 0) (= n 1)) 1 (+ (fib (- n 1)) (fib (- n 2))))))
(define (func n) (if (>= n 0) ((display n) (newline) (func (- n 1)))))
;(display (fib 4))