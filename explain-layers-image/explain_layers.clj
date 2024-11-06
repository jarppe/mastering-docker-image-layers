(ns explain-layers
  (:require [dockler.api :as api]
            [dockler.util.explain-image-layers :as layers]))


(defn -main [& args]
  (with-open [conn (api/connect)]
    (layers/explain-image-layers conn
                                 (str (first args))
                                 {:columns 80})))
