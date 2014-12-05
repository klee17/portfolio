;insertion-sort : List(numbers) -> List(numbers)
(define (insertion-sort nums)
  (insert-helper nums '()))
  
       
(define (insert-helper nums endnums)
  (if (null? nums) 
      endnums
      (insert-helper (cdr nums) (insert (car nums) endnums))))
  

(define (insert num nums)
  (cond 
    ((null? nums) (list num))
    ((<= num (car nums)) (cons num nums))
    ((> num (car nums)) (cons (car nums) (insert num (cdr nums))))))

;(equal? (insertion-sort '(1)) '(1))
;(equal? (insertion-sort '(5 1 4 3 2 6 5)) '(1 2 3 4 5 5 6))
