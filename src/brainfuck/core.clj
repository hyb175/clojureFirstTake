(ns brainfuck.core
  (:require [clojure.java.io :as io]))

(def arrayIndex (atom 0))

(defn get-arrayIndex []
  @arrayIndex)

(defn inc-arrayIndex []
  (reset! arrayIndex (inc @arrayIndex)))

(defn processLine [brainfuckArray arrayIndex line]
  (doseq [c line]
    (case c
      ">" (+ arrayIndex 1)
      "<" (println "arrayIndex")
      "+"
      "-"
      "."
      ","
      "["
      "]"
      "default"
      )
    (println c))
  )

(defn brainfuckLOL []
  (def brainfuckArray (byte-array 30000))
  (println arrayIndex)
  (with-open [rdr (io/reader "/Users/yingbaihe/brainfuck")]
  (doseq [line (line-seq rdr)]
  (processLine brainfuckArray arrayIndex line))))
