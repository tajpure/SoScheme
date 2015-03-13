; This is the test file for So-Scheme
(define x (lambda(a b) (+ a b)))
(display '(quote "hello world"))
(define x #f)
(define f
  (lambda (x)
    (g x)))
(define g
  (lambda (x)
    (+ x x)))