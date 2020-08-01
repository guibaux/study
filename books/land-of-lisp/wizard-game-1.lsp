(defparameter *nodes* 
  '((garden (You are in a beautiful garden
    
     there is a well in front of you))

  (living-room (You are in a living-room.
  
    a wizard is snoring loudly in a beautiful garden.))
    (attic (you are in the attic there is a giant welding torch in the corner)))) 
 
(defparameter *edges* 
  '((living-room (garden west door) (attic upstairs ladder)) 
    (garden (living-room east door)) 
    (attic (living-room downstairs ladder)))) 

(defparameter *objects* '(vodka mug frog chain)) 
(defparameter *objects-location* '((vodka living-room)
  (mug living-room) 
  (chain garden) 
  (frog garden)))
  
(defparameter *location* 'living-room) 
  
(defun desc-loc (location nodes)
  (cadr (assoc location nodes)))
 
(defun desc-path (edge)
  `(there is a ,(caddr edge) going ,(cadr edge) from here.))

(defun desc-paths (location edges)
  (apply #'append (mapcar #'desc-path (cdr (assoc location edges)))))

(defun objects-at (loc objs obj-loc)
  (labels ((at-loc-p (obj)
    (eq (cadr (assoc obj obj-loc)) loc)))
  (remove-if-not #'at-loc-p objs)))
  
(defun desc-objects (loc objs obj-loc) 
  (labels ((desc-obj (obj)
    `(you see a ,obj on the floor)))
  (apply #'append (mapcar #'desc-obj (objects-at loc objs obj-loc))))) 
 
 (defun look ()
   (append (desc-loc *location* *nodes*)
     (desc-paths *location* *edges*)
     (desc-objects *location* *objects* *objects-location*))) 