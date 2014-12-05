;merge-sort : List -> List
(define (merge-sort nums) 
   (cond
     ((null? nums) '())
     ((null? (cdr nums)) nums)
     (else (merge (merge-sort (odd-nums nums)) 
                  (merge-sort (even-nums nums))))))
    
(define (even-nums nums) 
  (cond
    ((null? nums) '())
    ((null? (cdr nums)) '())
    (else (cons (car (cdr nums)) (even-nums(cdr(cdr nums)))))))

(define (odd-nums nums)
  (cond
    ((null? nums) '())
    ((null? (cdr nums)) (list (car nums)))
    (else (cons (car nums) (odd-nums (cdr (cdr nums)))))))
;(equal? (merge-sort '(5 2)) '(2 5))
;(equal? (merge-sort '(3 1 6 3 8 9 2)) '(1 2 3 3 6 8 9))
;(equal? (merge-sort '(1)) '(1))
