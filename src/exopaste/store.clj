(ns exopaste.store
  (:require [com.stuartsierra.component :as component]))

(defn add-new-paste
  "Inserts new paste in the database, then returns UUID"
  [store content]
  (let [uuid (.toString (java.util.UUID/randomUUID))]
    (swap! (:data store) assoc (keyword uuid) {:content content})
    uuid))

(defn get-paste-by-uuid
  "Find the paste corresponding to the passed-in uuid, then return it"
  [store uuid]
  ((keyword uuid) @(:data store)))

(defrecord InMemoryStore [data]
  
  component/Lifecycle
  
  (start [this]
    (assoc this :data (atom {})))
  
  (stop [this] this))

(defn make-store []
  (map->InMemoryStore {}))
