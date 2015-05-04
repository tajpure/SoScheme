(newline)
(newline)
(display "Test Case for procedure:")
(newline)
(display ((lambda (y) (+ 1 y)) 2)) ;3
(newline)
(display ((lambda x x) 3 4 5 6)) ;(3 4 5 6)
(newline)
(display ((lambda (x y . z) z) 3 4 5 6 7)) ;(5 6 7)