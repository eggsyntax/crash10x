(ns crash10x.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[crash10x started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[crash10x has shut down successfully]=-"))
   :middleware identity})
