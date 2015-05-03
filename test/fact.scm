; fact function

(define (fact n) 
			(if (= n 1) 
				n 
				(* (fact (- n 1)) n)))
				
(display (fact 10))

; 3628800