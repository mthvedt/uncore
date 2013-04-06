(ns uncore.throw)

(defmacro thrownew [extype & strs]
  `(throw (new ~extype (str ~@strs))))

(defmacro IAE [& strs]
  `(thrownew IllegalArgumentException ~@strs))

(defmacro RE [& strs]
  `(thrownew RuntimeException ~@strs))

(defmacro with-rethrow [ex-class form ex-str-form]
  `(try ~form
     (catch ~ex-class e#
       (throw (new ~ex-class ~ex-str-form e#)))))
