(define fib (lambda (n) 
				(if (or (= n 0) (= n 1)) 
					1 
					(+ (fib (- n 1)) (fib (- n 2))))))

(define (func start end) 
				(if (>= end start) 
					(begin (display (fib start) " ") 
					(func (+ start 1) end))))

(func 0 20)