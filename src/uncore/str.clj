(ns uncore.str)

(defn cutoff
  ([obj] (cutoff obj 80))
  ([obj val]
   (let [thestr (str obj)]
     (if (< val (count thestr))
       (str (subs thestr 0 val) "(...)")
       thestr))))

(defn separate-str
  [separator theseq] (apply str (drop 1 (interleave (repeat separator) theseq))))
