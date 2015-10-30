(ns brainfuck.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(def arrayIndex (atom 0))

(defn get-arrayIndex []
  @arrayIndex)

(def brainfuckArray (atom (vec (byte-array 1000))))

(defn get-brainfuckArray []
  @brainfuckArray)

(defn get-brainfuckArray-value []
  (nth @brainfuckArray (get-arrayIndex)))

(defn inc-arrayIndex []
  (reset! arrayIndex (inc @arrayIndex)))

(defn dec-arrayIndex []
  (reset! arrayIndex (dec @arrayIndex)))

(defn update-brainfuckArray [value]
  (swap! brainfuckArray assoc (get-arrayIndex) value))

(defn jump-to [line]
  (loop [x 0 matchCount 0]
    (when (and (< x (count line)) (> matchCount -1))
      (let [c (nth line x)]
        (case c
          \[ (recur (inc x) (inc matchCount))
          \] (if (= matchCount 0) x (recur (inc x) (dec matchCount)))
          (recur (inc x) matchCount))))))

(defn processLine [line]
  (loop [x 0 trackingStack ()]
    (let [lineChars (count line)]
      (when (< x lineChars)
        (let [c (nth line x)]
        (if (= c \])
          (if (> (get-brainfuckArray-value) 0) (recur (inc (peek trackingStack)) trackingStack) (recur (inc x) (pop trackingStack)))
          (if (= c \[) (if (> (get-brainfuckArray-value) 0) (recur (inc x) (conj trackingStack x)) (recur (inc (jump-to (subs line (inc x)))) (conj trackingStack x)))
            (do (case c
            \> (inc-arrayIndex)
            \< (dec-arrayIndex)
            \+ (update-brainfuckArray (inc (get-brainfuckArray-value)))
            \- (update-brainfuckArray (dec (get-brainfuckArray-value)))
            \. (print (char (get-brainfuckArray-value)))
            \, (let [ch (.read System/in)] (update-brainfuckArray ch))
            "default"
            )
        (recur (inc x) trackingStack)))))))))
  

(defn brainfuckLOL []  
  (processLine (slurp (io/reader "/Users/yingbaihe/brainfuck")))
  (println (get-brainfuckArray)))

(defn -main [& args]
  (brainfuckLOL))
