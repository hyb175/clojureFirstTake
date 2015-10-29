(ns brainfuck.core
  (:require [clojure.java.io :as io]))
(defn processLine [brainfuckArray index line]
  (doseq [c line]
    (println c))
  )

(defn brainfuckLOL []
  (def brainfuckArray (byte-array 30000))
  (def index 0)
  (with-open [rdr (io/reader "/Users/yingbaihe/brainfuck")]
  (doseq [line (line-seq rdr)]
  (processLine brainfuckArray index line))))
