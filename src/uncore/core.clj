(ns uncore.core)

(defn update [map key f] (update-in map [key] f))

(defn update-all [map keyvals]
  (reduce (fn [m [k f]] (update m k f)) map keyvals))

; Eagerly evaluated map
(defn domap [& args] (doall (apply map args)))

; Eagerly evaluated map, no memory leaks
(defn runmap [& args] (dorun (apply map args)))

(defmacro map-> [coll & forms]
  `(map
     (fn [x#] (-> x# ~@forms))
     ~coll))

(defmacro fn-> [& forms]
  `(fn [x#] (-> x# ~@forms)))

(defmacro let->
  ([binding form] `(let ~binding ~form))
  ([binding form & forms]
   (let [v (first binding)]
     `(let-> [~v (let-> ~binding ~form)] ~@forms))))

(defn hexhash [obj]
  (Integer/toHexString (hash obj)))
