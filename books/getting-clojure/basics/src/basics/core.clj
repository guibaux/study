(ns basics.core
  (:gen-class))

(def a-var 3)
(def a-map {:key 10 :value 8})
(def a-set #{"Hello" "I" "Am" "Here"})

(defn average [a b]
  (/ (+ a b) 2.0))

(defn multiply 
  ([a] (multiply a 2))
  ([a b] (* a b)))
(defn variadic [& args]
  args)
(defn first-arg [x & args] x)

(defn multimethod-base [arg]
  (vector? arg) :vec
  (contains? arg :me) :me
  (contains? arg :other) :other)
(defmulti multimethod multimethod-base)
(defmethod multimethod :vec [arg]
  (println arg "is a vector"))
(defmethod multimethod :me [arg]
  (println arg "contains me"))
(defmethod multimethod :other [arg]
  (println arg "has" (+ 2 3) "friends"))

(defn sum-array [arr]
  (loop [arr arr total 0]
    (if (empty? arr)
      total
      (recur 
       (rest arr)
       (+ total (first arr))))))
(defn documented-fn 
  "I'm a doc"
  []
  "Nothing")

(defn pre-cond [arg]
  {:pre [(:me arg)]}
  (:me arg))
(defn post-cond [arg]
  {:post [(number? %)]}
  (:me arg))

;; Basics
(defn -main []
  (println "Hello, World!") ; Prints Hello
  (count "Hello") ; returns 5
  (str "One" " " 2 " " "Three" "!") ; concat strings and numbers
  (count [1 2 3 4]) ; returns 4
  (println a-var) ; returns 3
  (average a-var 5) ; returns 4.0
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
  (:key a-map) ; returns 10 or (*map* :*key*)
  (assoc a-map :key2 6) ; returns {:key 10, :value 8, :key2 6}
  (dissoc a-map :key) ; returns {:value 8}
  (dissoc a-map :notin) ; dont change anything, because dissoc ignores symbols that arent in the map
  (assoc [:key :here :value] 1 :changed) ; returns [:key :changed :value], vectors are maps with integers as keys
  (keys a-map) ; returns (:key :value), hash-maps keys arent in order, for this use sorted-map
  (vals a-map) ; returns (10 8)
  ;; Be Careful with maps with nil value keys

  ;; Sets
  ;;   Cannot Duplicate values
  (contains? a-set "Here") ; -> true
  (contains? a-set "Not in Here"); -> false
  (a-set "Here") ; -> "Here"
  (a-set "Not in here") ; -> nil
  (conj a-set "Hi!") ; -> #{"Am" "Hello" "Hi!" "I" "Here"}
  (disj a-set "Hello") ; -> #{"Am" "I" "Here"}

  ;; Logic
  (if true "True" "False") ; -> True
  (if false "True" "False") ; -> False
  (if 0 "True" "False") ; -> True
  (when 0 "True") ; -> True
  (when 10
    println "Do!"
    10) ; -> 10, same as (if 10 (do println "Do!" 10) "...")
  (cond
    (> 3 4) false
    (< 10 3) false
    (= 7 6) false
    :else true) ; -> true
  (case :hello
    :notme false
    :neitheri false
    :itsme) ; -> :itsme, the last expression always matches
  ;; Only false and nil evaluates as false
  (= 2 2) ; -> true
  (= 2 (- 6 (* 2 2)) 2 (* 2 1) (- 10 8) 2 (+ 1 1)) ; -> true
  (= 5 5 5 5 5 3 5); -> false
  (not= 5 6); -> false, same as (not (= ...))
  (number? 5); -> true
  (map? a-map); -> true
  (keyword? :keyword); -> true
  ;; string?, vector?...
  (> 3 2 1); -> true
  (> 3 1 2); -> false
  ;; x > y > z ...

  ;;   Errors
  (try
    (/ 0 0)
    (catch ArithmeticException e (println "Math errors...")))
  ;;(throw
  ;;  (ex-info "Generic Error" 20)) -> Exception type is clojure.lang.ExceptionInfo
  (and true "True"); -> "True"
  (and "True" nil 10) ; -> nil
  ;; doing (= (and x y) true) is wrong

  ;; Functions
  (multiply 2) ; -> 4
  (multiply 2 3) ; -> 6
  (variadic 10 30 true nil) ; -> (10 30 true nil)
  (first-arg 10 30 true nil); -> 10
  (multimethod [1 2 3])
  (multimethod {:me "Hello"})
  (multimethod {:other "I have friends!"})
  ;; Multimethods allow to modify the behaviour of the function by the argument characteristics
  ;; we can use the keys is multimethod definition to select based on this key
  (sum-array [3 4 5]); -> 12
  ;; recur is low level a better way to do this is:
  (reduce + [3 4 5]); -> 12

  ;; (doc documented-fn); -> basics.core/documented-fn
                        ;    ([])
                        ;      I'm a doc
                        ;    nil
  ;;(pre-cond {:not 12}); -> Assertion failure, :me not in the map
  ;;(post-cond {:me "String"}); -> Assertion Error, "String" is not a number
  )
