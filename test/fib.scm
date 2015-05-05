;(newline)
;(newline)
(display "Test Case for fibnacci:")
;(newline)
(define fib (lambda (n) 
				(if (or (= n 0) (= n 1)) 
					1 
					(+ (fib (- n 1)) (fib (- n 2))))))

(define (func start end) 
				(if (>= end start) 
					(begin (display (fib start) " ") 
					(func (+ start 1) end))))

(func 0 20)
; 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584 4181 6765 10946 