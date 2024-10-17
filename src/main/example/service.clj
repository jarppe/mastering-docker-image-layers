(ns example.service
  (:require [clojure.string :as str]
            [ring.adapter.jetty9 :as jetty]))


(defn handler [req]
  (println "service: request:" (-> req :request-method (name) (str/upper-case)) (-> req :uri))
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "hello, world!\n"})


(defn -main [& _args]
  (let [host (System/getenv "HOST")
        port (parse-long (System/getenv "PORT"))]
    (println "service: starting service...")
    (jetty/run-jetty handler {:host  host
                              :port  port
                              :join? false})
    (println "service: service ready at" (str host ":" port))))
