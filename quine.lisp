(defun print-file ()
    (let ((in (open "quine.lisp" :if-does-not-exist nil)))
          (when in
            (loop for line = (read-line in nil)
                 while line do (format t "~a~%" line))
            (close in)))
)

(print-file)