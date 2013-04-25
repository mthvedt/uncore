(ns uncore.test.utils
  (require [clojure.java.io :as io]
           [uncore.throw :as t])
  (:use uncore.core lazytest.deftest))

(defmacro is= [& forms]
  `(is (= ~@forms)))

(defmacro isnt [form]
  `(is (not ~form)))

(defmacro isnt= [& forms]
  `(is (not (= ~@forms))))
