(define (func1 x) 
	(begin (define (func2 y) (x + y))
	(func2 1)))