(ns crash10x.app
  (:require [crash10x.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
