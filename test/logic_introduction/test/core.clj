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
                    ))))

