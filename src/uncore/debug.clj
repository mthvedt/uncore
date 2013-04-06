(ns uncore.debug)

(def ^:dynamic *debug* true)

(defmacro debug
  ([statement]
   `(debug ~statement ~statement))
  ([statement & forms]
   (if *debug*
     `(do (println ~statement) ~@forms)
     `(do ~@forms))))

(defmacro idebug [& forms]
  `(debug ~forms))
