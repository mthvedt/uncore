(ns uncore.memo
  "Memoizing with contexts. More than just performance,
  these fns can be used e.g. to ensure one canonical version
  of something exists in a given context."
  (import java.util.concurrent.ConcurrentHashMap)
  (use uncore.core))

(def ^{:dynamic true
       :doc "The dynamic memo context. Bound to nil."}
  *memo* nil)

; === Setup

(defn memoizer [] (atom {}))

(defmacro with-memoizer [m & forms]
  `(binding [*memo* ~m] ~@forms))

; === Low level

(def empty-map {})

(defn cached?
  "Returns truthy if key1, key2 is memoed in the present context."
  [key1 key2]
  (and *memo* (contains? @(get @*memo* key1 #'empty-map) key2)))

(defn lookup [key1 key2]
  "Returns the mapping for key1, key2 in the present context. Fails if the context
  is not set"
  (get @(get @*memo* key1 #'empty-map) key2))

(defn- new-submem []
  ;(ConcurrentHashMap.))
  (atom {}))

(defn get-or-create-submem [key]
  (loop []
    (let [oldmem @*memo*]
      (if-let [keymem (get oldmem key)]
        keymem
        (let [keymem (new-submem)]
          (if (compare-and-set! *memo* oldmem (assoc oldmem key keymem))
            keymem
            (recur)))))))

(defn save! [key1 key2 result]
  "Saves the mapping for key1, key2 in the present context."
  (swap! (get-or-create-submem key1) assoc key2 result)
  result)

(defn save-or-get! [key1 key2 result]
  "Saves the mapping for key1, key2 and returns it, or returns the mapping
  already present."
  (loop []
    (if (cached? key1 key2)
      (lookup key1 key2)
      (let [mem2 (get-or-create-submem key1)
            oldmem @mem2]
        (if (compare-and-set! mem2 oldmem (assoc oldmem key2 result))
          result
          (recur))))))

(defn save!? [key1 key2 result]
  "Saves the mapping for key1, key2 if the context exists. Returns result."
  (if *memo* (save! key1 key2 result) result))


(defn save-count [key1]
  (if *memo* (count @(get @*memo* key1 #'empty-map)) 0))

; === High level

(defn fmem
  "Creates an optionally-memoized version of f. Passthrough if there is
  no context."
  [f]
  (fn g [& args]
    (if (cached? g args)
      (lookup g args)
      (let [r2 (apply f args)]
        (save!? g args r2)))))

(defn fcache [cache-key-fn val-fn]
  "Creates a version of val-fn that is memoized, with cache key determined
  by cache-key-fn. That is, the memo keys are (apply cache-key-fn args)
  and the values are (apply val-fn args)."
  (fn g [& args]
    (save-or-get! g (apply cache-key-fn args) (apply val-fn args))))

(defn generate
  "k: a key, v: a value, gen-fn: a fn mapping v, int -> generated value.
  Can be used to genarate e.g. unique IDs for values in a context, among other things"
  [k v gen-fn]
  (if (cached? k v)
    (lookup k v)
    (loop []
      (let [mem (get-or-create-submem k)
            oldmem @mem
            vgen (gen-fn v (count oldmem))
            newmem (assoc oldmem v vgen)]
        (if (compare-and-set! mem oldmem newmem)
          vgen
          (recur))))))

; TODO this doesn't actually work for some reason... causes infinite loops
(defmacro defnmem [sym args & body]
  `(def ~sym (fmem (fn ~args ~@body))))
