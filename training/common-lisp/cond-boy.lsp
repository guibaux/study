(defvar *defunto* nil) 

(defun batata (person)
  (cond 
    ((eq person 'u) 
      (setf *defunto* 'its_u) '(Why are you eating my potato???!!! ))
    ((eq person 'gui)
     (setf *defunto* 'thelovelycreator) '(Ohhh, eat my potato, please! ))
    (t '(Who are you? It's my mom? ))))