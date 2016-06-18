;;; Tests for read-file module

(define-test read-content
  (assert-equal "Hi there!" (get-content "t-assets/hello.txt")))