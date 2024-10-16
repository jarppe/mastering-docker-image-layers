(ns explain-layers
  (:require [dockler.api :as api]
            [dockler.util.explain-image-layers :as layers]))


(let [[image] *command-line-args*]
  (when-not image
    (println "missing required argument: <image-name>")
    (System/exit 1))
  (with-open [conn (api/connect)]
    (-> (layers/image-layers-info conn (str image))
        (layers/explain-image-layers))))
