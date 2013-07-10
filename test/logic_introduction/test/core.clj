(ns logic-introduction.test.core
  (:refer-clojure :exclude [==])
  (:require [clojure.test :as test])
  (:use     [clojure.core.logic]
            [logic-introduction.core]))

(test/deftest first-type-test

  (test/is (= '(_0)
              (run* [q]
                    (typedo [ ['f :- [Integer :> Integer] ] ;; 'f is of type Integer -> Integer
                              ['g :- Integer] ;; 'g is of type Integer
                              ] 
                            [:apply 'f 'g] ;; Determine the resulting type of ('f 'g) ..
                            Integer) ;;  and succeed if it is Integer
                    )))

  ;; Expect no solution to the following:
  (test/is (= '()
              (run* [q]
                    (typedo [ ['f :- [Float :> Integer] ] ;; 'f is of type Float -> Integer
                              ['g :- Integer] ;; 'g is of type Integer
                              ] 
                            [:apply 'f 'g] ;; Determine the resulting type of ('f 'g) ..
                            Integer) ;;  and succeed if it is Integer
                    )))

  ;; But, if we change g to be of type Float, all will be well:
  (test/is (= '(_0)
              (run* [q]
                    (typedo [ ['f :- [Float :> Integer] ] ;; 'f is of type Float -> Integer
                              ['g :- Float] ;; 'g is of type Float
                              ] 
                            [:apply 'f 'g] ;; Determine the resulting type of ('f 'g) ..
                            Integer) ;;  and succeed if it is Integer
                    )))

  ;; Adding another function to the flow also type-checks:
  (test/is (= '(_0)
              (run* [q]
                    (typedo [ ['f :- [Float :> Integer] ]
                              ['g :- Float]
                              ['h :- [Integer :> Float]]
                              ] 
                            [:apply 'h [:apply 'f 'g]] 
                            Float)
                    )))

  (test/is (= '(_0)
              (run* [q]
                    (typedo [ ['max :- [Integer :> [Integer :> Integer]]]
                              ['a :- Integer]
                              ['b :- Integer]
                              ]
                            [:apply [:apply 'max 'a] 'b]
                            Integer))))
  (test/is (= '(_0)
              (run* [q]
                    (typedo [ ['and :- [Boolean :> [Boolean :> Boolean]]]
                              ['a :- Boolean]
                              ['b :- Boolean]
                              ]
                            [:apply [:apply 'and 'a] 'b]
                            Boolean))))  )

