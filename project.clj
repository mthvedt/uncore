(defproject eightnotrump/uncore "0.2.1"
  :description "Useful clojure stuff I use."
  :url "https://github.com/mthvedt/uncore"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
    :scm {:name "git"
          :url "https://github.com/mthvedt/uncore"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:repositories {"stuartsierra-releases"
                                  "http://stuartsierra.com/maven2",
                                  "stuartsierra-snapshots"
                                  "http://stuartsierra.com/m2snapshots"}
                   :dependencies [[com.stuartsierra/lazytest "2.0.0-SNAPSHOT"]]
                   :plugins [[eightnotrump/lein-lazytest "1.0.5"]]}}
  :dependencies [[org.clojure/clojure "1.5.1"]])
