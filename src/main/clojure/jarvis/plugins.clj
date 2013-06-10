(ns jarvis.plugins
  (:require [fs.core :as fs]
            [clojure.java.classpath :as classpath]
            [clojure.string :as s]
            [clojure.tools.logging :as log]
            [clojure.set :as set])
  (:import [com.rallydev.jarvis Groovy Plugin]
           [groovy.lang Closure]))

(def ^:const plugin-pattern #".*jarvis/plugins/.*")
(def not-nil (complement nil?))

(def plugins-atom (atom #{}))
(def meta-mapping {"command" :command
                   "description" :description
                   "author" :author})

(defn- apply-meta [f meta-data]
  (-> (zipmap (.keySet meta-data) (.values meta-data))
    (set/rename-keys meta-mapping)
    (->>
      (with-meta f))))

(defn- save-plugin [f meta-data]
  (swap! plugins-atom conj (apply-meta f meta-data)))

(defn command-name [plugin]
  (:command (meta plugin)))

(defprotocol PluginAdapter
  "Provides adapters for plugins from different JVM languages"
  (add-plugin [plugin plugin-metadata]))

(extend-type Plugin
  PluginAdapter
  (add-plugin [plugin plugin-metadata]
    (save-plugin #(.invoke plugin %) plugin-metadata)))

(extend-type Closure
  PluginAdapter
  (add-plugin [plugin plugin-metadata]
    (save-plugin #(.call plugin (object-array [%])) plugin-metadata)))

(defn plugin? [file]
  (->> file
    fs/absolute-path
    (re-matches plugin-pattern)
    not-nil))

(defn relative-to [directory-name file-name]
  (if (.endsWith directory-name "/")
    (s/replace-first file-name directory-name "")
    (s/replace-first file-name (str directory-name "/") "")))

(defn absolute-path [file]
  (if (fs/directory? file)
    (fs/absolute-path file)
    (fs/absolute-path (fs/file "."))))

(defn find-plugins [file]
  (->> file
    file-seq
    (filter plugin?)
    (map fs/absolute-path)
    (map #(relative-to (absolute-path file) %))))

(defn classpath-jar-plugins []
  (->> (classpath/classpath-jarfiles)
    (map #(classpath/filenames-in-jar %))
    flatten
    (map #(fs/file %))
    (map find-plugins)))

(defn classpath-file-plugins []
  (map find-plugins (classpath/classpath)))

(defn classpath-plugins []
  (let [file-plugins (classpath-file-plugins)
        jar-plugins (classpath-jar-plugins)
        plugins (flatten (conj file-plugins jar-plugins))]
    (log/info "Found plugins in -- [" plugins "]")
    plugins))

(defn load-java-plugin [plugin]
  (-> plugin
    (s/replace ".class" "")
    (s/replace "/" ".")
    Class/forName))

(defn require-clojure-plugin [plugin]
  (require (fs/path-ns plugin)))

(defn load-plugin [plugin]
  (log/info "Loading plugin: " plugin)
  (cond
    (.endsWith plugin ".class") (load-java-plugin plugin)
    (.endsWith plugin ".groovy") (Groovy/loadGroovyPlugin (str "/" plugin))
    (.endsWith plugin ".clj") (require-clojure-plugin plugin)))

(defn- load-clojure-plugins []
  (->> (all-ns)
    (map (comp vals ns-publics))
    flatten
    (filter (comp :plugin meta))
    (apply swap! plugins-atom conj)))

(defn plugins->map [plugins]
  (into {} (for [p plugins] [(-> p meta :command ) p])))

(defn load-plugins []
  (log/info "Loading Plugins...")
  (dorun (map load-plugin (classpath-plugins)))
  (load-clojure-plugins)
  (plugins->map @plugins-atom))
