(ns uncore.collections.worm-ordered-multimap
  "A write-once ordered multimap (i.e. a map of key->ordered set).
  The vals can only be added to, but the keys can be dissoc'd.
  Note that the keys are not ordered, only the vals are."
  (:refer-clojure :exclude [get dissoc keys vals assoc empty])
  (require [clojure.core :as core])
  (require [uncore.collections.worm-ordered-set :as os]))

; ordered multimaps are just maps of key->ordered-set
(def empty {})

(defn get [mm k] (core/get mm k os/empty))

(defn keys [mm] (core/keys mm))

(defn vals [mm] (core/vals mm))

(defn assoc [mm k v]
  (core/assoc mm k (os/conj (get mm k) v)))

(def dissoc core/dissoc)

(defn get-vec [mm k]
  (os/vec (get mm k)))
