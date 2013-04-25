(ns uncore.collections.worm-ordered-map
  "A write-once ordered map. It can only be added to."
  (:refer-clojure :exclude [get assoc empty keys vals count into map])
  (require [clojure.core :as core])
  (require [uncore.collections.worm-ordered-set]))

(defprotocol IOrderedMap
  (get [self k])
  (get-key [self i])
  (keys [self])
  (key-vec [self])
  (vals [self])
  (map [self])
  (assoc [self k v]))

; The keys in this map can only be added to or altered, never removed.
; i->k: a vector, k->v: a key-value map.
(deftype AOrderedMap [i->k k->v]
  IOrderedMap
  (get [self k]
    (core/get k->v k))
  (get-key [self i]
    (core/get i->k i))
  (keys [self]
    (uncore.collections.worm_ordered_set.AOrderedSet. i->k (core/keys k->v)))
  (key-vec [self]
    i->k)
  (vals [self]
    (core/map #(core/get k->v %) i->k))
  (map [self]
    k->v)
  (assoc [self k v]
    (AOrderedMap.
      (if (contains? k->v k) i->k (conj i->k k))
      (core/assoc k->v k v))))

(defn count [ordered-map] (core/count (key-vec ordered-map)))

(defn get-index [ordered-map i]
  (get ordered-map (get-key ordered-map i)))

(def empty (AOrderedMap. [] {}))
