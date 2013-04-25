(defproject eightnotrump/uncore "0.2.0-SNAPSHOT"
  :description "Useful clojure stuff I use."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:repositories {"stuartsierra-releases"
                                  "http://stuartsierra.com/maven2",
                                  "stuartsierra-snapshots"
                                  "http://stuartsierra.com/m2snapshots"}
                   :dependencies [[com.stuartsierra/lazytest "2.0.0-SNAPSHOT"]]
                   :plugins [[eightnotrump/lein-lazytest "1.0.5"]]}}
  :dependencies [[org.clojure/clojure "1.5.1"]])
