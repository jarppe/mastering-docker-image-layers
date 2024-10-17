(ns build
  (:require [clojure.tools.build.api :as b])
  (:import (java.io File)))


(def src-dir     "./src/main")
(def target      "./target")
(def jar-file    (str target "/service.jar"))
(def libs-dir    (str target "/libs"))


(defn build-uber [_]
  (let [basis (b/create-basis)]
    (b/uber {:basis     basis
             :class-dir src-dir
             :uber-file jar-file})))


(defn copy-deps [_]
  (let [basis  (b/create-basis)
        jars   (->> basis
                    :classpath
                    (keep (fn [[lib-id {:keys [lib-name]}]]
                            (when lib-name
                              lib-id))))
        target (fn [jar]
                 (str libs-dir
                      "/"
                      (-> jar (File.) (.getName))))]
    (doseq [jar jars]
      (b/copy-file {:src    jar
                    :target (target jar)}))))


(defn build-jar [_]
  (let [basis (b/create-basis)]
    (b/jar {:basis     basis
            :class-dir src-dir
            :jar-file  jar-file})))


