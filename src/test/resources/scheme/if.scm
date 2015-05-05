(newline)
(newline)
(display "Test Case for if:")
(newline)
(display (if (> 3 2) 'yes 'no)) ; yes
(newline)
(display (if (> 2 3) 'yes 'no)) ; no
(newline)
(display (if (> 3 2)
(- 3 2)
(+ 3 2))) ; 1