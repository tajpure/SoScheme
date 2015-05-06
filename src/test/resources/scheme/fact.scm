; factorial function
(display "Test Case for fact:")
(newline)
(define (fact n) 
			(if (= n 1) 
				n 
				(* (fact (- n 1)) n)))
				
(display (fact 10))

(define (factorial n)
  (define (iter product counter)
    (if (> counter n)
  		product
  		(iter (* counter product)
        (+ counter 1))))
  (iter 1 1))
(display (factorial 10))

; 3628800