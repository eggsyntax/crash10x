(ns crash10x.events
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
    [re-frame.core :as rf]
    [reitit.frontend.controllers :as rfc]
    [reitit.frontend.easy :as rfe]))


;; dispatchers

(rf/reg-event-db
  :common/navigate
  (fn-traced [db [_ match]]
             (let [old-match (:common/route db)
                   new-match (assoc match :controllers
                                    (rfc/apply-controllers (:controllers old-match) match))]
               (assoc db :common/route new-match))))


(rf/reg-fx
  :common/navigate-fx!
  (fn-traced [[k & [params query]]]
             (rfe/push-state k params query)))


(rf/reg-event-fx
  :common/navigate!
  (fn-traced [_ [_ url-key params query]]
             {:common/navigate-fx! [url-key params query]}))


(rf/reg-event-db
  :set-docs
  (fn-traced [db [_ docs]]
             (assoc db :docs docs)))


(rf/reg-event-fx
  :fetch-docs
  (fn-traced [_ _]
             {:http-xhrio {:method          :get
                           :uri             "/docs"
                           :response-format (ajax/raw-response-format)
                           :on-success       [:set-docs]}}))


(rf/reg-event-db
  :common/set-error
  (fn-traced [db [_ error]]
             (assoc db :common/error error)))


(rf/reg-event-fx
  :page/init-home
  (fn-traced [_ _]
             {:dispatch [:fetch-docs]}))


;; subscriptions

(rf/reg-sub
  :common/route
  (fn [db _]
    (-> db :common/route)))


(rf/reg-sub
  :common/page-id
  :<- [:common/route]
  (fn [route _]
    (-> route :data :name)))


(rf/reg-sub
  :common/page
  :<- [:common/route]
  (fn [route _]
    (-> route :data :view)))


(rf/reg-sub
  :docs
  (fn [db _]
    (:docs db)))


(rf/reg-sub
  :common/error
  (fn [db _]
    (:common/error db)))
