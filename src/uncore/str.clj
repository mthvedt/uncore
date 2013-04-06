(ns uncore.str)

(defn cutoff
  ([thestr] (cutoff thestr 80))
  ([thestr val]
   (if (< val (count thestr))
     (str (subs thestr 0 val) "(...)")
     thestr)))

(defn separate-str
  [separator theseq] (apply str (drop 1 (interleave (repeat separator) theseq))))
