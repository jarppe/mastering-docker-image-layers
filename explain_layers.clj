(ns explain-layers
  (:require [dockler.api :as api]
            [dockler.util.explain-image-layers :as layers]))


(let [image (first *command-line-args*)]
  (with-open [conn (api/connect)]
    (-> (layers/image-layers-info conn (str image))
        (layers/explain-image-layers))))
