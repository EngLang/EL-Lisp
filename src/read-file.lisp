;;;; read-file.lisp - Module for manipulating our files

;;; Methods for reading and parsing our resource files
;;; Also includes some methods for parsing EngLang files

(defun get-content(file)
  "Get the contents of the file 'file'."
  (let ((in (open file :if-does-not-exist nil)))
    (when in 
      (loop for line = (read-line in nil)
            while line do (format t "~a~%" line))
      (close in))))