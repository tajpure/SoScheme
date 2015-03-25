(define location?
  (lambda (x y z)
  ; test 
  (or (register? x) (frame-var? x) (uvar? x))))