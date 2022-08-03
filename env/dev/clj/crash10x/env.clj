(ns crash10x.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [crash10x.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[crash10x started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[crash10x has shut down successfully]=-"))
   :middleware wrap-dev})
