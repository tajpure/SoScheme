; fact function
(newline)
(newline)
(display "Test Case for fact:")
(newline)
(define (fact n) 
			(if (= n 1) 
				n 
				(* (fact (- n 1)) n)))
				
(display (fact 10))

; 3628800