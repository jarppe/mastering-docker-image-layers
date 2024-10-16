(ns example.service
  (:require [clojure.string :as str]
            [ring.adapter.jetty9 :as jetty]))


(defn handler [req]
  (println "service: request:" (-> req :request-method (name) (str/upper-case)) (-> req :uri))
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "hello, world!"})


(defn main [{:syms [host port]
             :or   {host "0.0.0.0"
                    port 8080}}]
  (println "service: starting service...")
  (jetty/run-jetty handler {:host  (str host)
                            :port  port
                            :join? false})
  (println "service: service ready at" (str host ":" port)))
