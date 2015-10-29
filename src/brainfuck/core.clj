(ns brainfuck.core
  (:require [clojure.java.io :as io]))

(def arrayIndex (atom 0))

(defn get-arrayIndex []
  @arrayIndex)

(defn inc-arrayIndex []
  (reset! arrayIndex (inc @arrayIndex)))

(defn dec-arrayIndex []
  (reset! arrayIndex (dec @arrayIndex)))

(def brainfuckArray (atom (vec (byte-array 100))))

(defn get-brainfuckArray []
  @brainfuckArray)

(defn get-brainfuckArray-value []
  (nth @brainfuckArray (get-arrayIndex)))

(defn update-brainfuckArray [value]
  (swap! brainfuckArray assoc (get-arrayIndex) value))


(defn processLine [line]
  (let [trackingStack ()]
    (loop [x 0]
      (let [lineChars (count line)]
        (when (< x lineChars)
          (let [c (nth line x)]
          (if (= c \])
            (if (> (get-arrayIndex) 0) (recur (inc (peek trackingStack))) (do (pop trackingStack) (recur (inc x))))
            (do (case c
              \> (inc-arrayIndex)
              \< (dec-arrayIndex)
              \+ (update-brainfuckArray (inc (get-brainfuckArray-value)))
              \- (update-brainfuckArray (dec (get-brainfuckArray-value)))
              \. (println (get-brainfuckArray-value))
              \, (update-brainfuckArray (.read System/in))
              \[ (cons trackingStack x)
              )
          (recur (inc x))))))))))
  

(defn brainfuckLOL []  
  (processLine (slurp (io/reader "/Users/yingbaihe/brainfuck")))
  (println (get-brainfuckArray)))
