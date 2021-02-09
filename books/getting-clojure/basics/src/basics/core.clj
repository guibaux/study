(ns basics.core
  (:gen-class))

(def an-var 3)
(defn average [a b]
  (/ (+ a b) 2.0))
;; Basics
(defn -main []
  (println "Hello, World!") ; Prints Hello
  (count "Hello") ; returns 5
  (str "One" " " 2 " " "Three" "!") ; concat strings and numbers
  (count [1 2 3 4]) ; returns 4
  (println an-var) ; returns 3
  (average an-var 5) ; returns 4.0
  (/ 3 8) ; returns 3/8
  ;; (/ 100 0) ; Divide By Zero error

  ;;   Vectors
  (vector) ; Same as []
  (first [2 3 5 6]); returns 2
  ([5 6 7 8] 3) ; returns 8, same as (nth ... 3)
  (rest [5 6 9]) ; returns (6 9), returns a sequence not a vector
  (rest [4]) ; returns an empty sequence, same as (rest [])
  (conj ["A" "list" "of"] "strings") ; returns ["A" "list" "of" "strings"]
  (cons "strings" ["A" "list" "of"]) ; returns ("strings" "A" "list" "of")
  (conj '("A" "list" "of") "strings") ; returns ("strings" "A" "list" "of")
  ;; Vectors are continuos blocks of memory, when new item is added a new block of memory is alloced and the vector is moved
  ;; Lists are Linked Lists, when new item is added the item only points to the first member of the list
  ;; The items of the list contains the size of the list
  (list) ; Same as '()

  ;; Maps
  {"key" 10 "value" 7} ; Creates a hash map with key and value as keys
  ;; or with this sintax
  (hash-map "key" 10
            "value" 7)
  ({"key" 10 "value" 7} "value") ; returns 7 or (get *map* *value*)
  ({"key" 10 "value" 7} "null") ; returns nil if not in the map
  (:key {:key 10 :value 8}) ; returns 10 or (*map* :*key*)
  
  )
