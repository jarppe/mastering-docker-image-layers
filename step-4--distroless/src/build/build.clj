(ns build
  (:require [clojure.tools.build.api :as b]))


(def compile-ns  'example.service)
(def main-class  'example.service.Main)
(def target      "./target")
(def classes-dir (str target "/classes"))
(def jar-file    (str target "/service.jar"))


(defn build-uber [_]
  (let [basis (b/create-basis)]
    (b/compile-clj {:basis        basis
                    :ns-compile   [compile-ns]
                    :class-dir    classes-dir
                    :compile-opts {:elide-meta     [:doc :file :line :added]
                                   :direct-linking true}
                    :bindings     {#'clojure.core/*assert* false}})
    (b/uber {:basis     basis
             :class-dir classes-dir
             :main      main-class
             :uber-file jar-file})))
