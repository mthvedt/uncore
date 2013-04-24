(ns uncore.rpartial)

(defn gen-interleave [coll i->vals]
  (loop [r [] coll2 coll i 0]
    (if (contains? i->vals i)
      (recur (conj r (get i->vals i)) coll2 (inc i))
      (if (seq coll2)
        (recur (conj r (first coll2)) (rest coll2) (inc i))
        r))))

(defn gen-rpartial [f themap]
  (fn [& args] (apply f (gen-interleave args themap))))

#_(defn gen-rpartial [f arg-count themap]
  (let [gensyms (zipmap (range arg-count) (repeatedly gensym))
        arg-gensyms (reduce dissoc gensyms (keys themap))]
    `(let ~(vec (mapcat (fn [i] `(~(get gensyms i) (get themap ~i))) (keys themap)))
       (fn ~(vec (remove nil? (map arg-gensyms (range arg-count))))
         (f ~@(map gensyms (range arg-count)))))))
