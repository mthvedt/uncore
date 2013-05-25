(ns uncore.rpartial
  "Deprecated, just hanging around.")

; TODO move back to clearley
(defn gen-interleave [coll i->vals]
  "Deprecated."
  (loop [r [] coll2 coll i 0]
    (if (contains? i->vals i)
      (recur (conj r (get i->vals i)) coll2 (inc i))
      (if (seq coll2)
        (recur (conj r (first coll2)) (rest coll2) (inc i))
        r))))

(defn gen-rpartial [f themap]
  "Deprecated."
  (fn [& args] (apply f (gen-interleave args themap))))
