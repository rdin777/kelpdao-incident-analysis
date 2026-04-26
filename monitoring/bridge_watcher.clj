(ns bridge-watcher.core
  (:require [clojure.math :as math]
            [aleph.http :as http]
            [cheshire.core :as json]
            [clojure.string :as str]))

;; --- Configuration ---
(def config
  {:l1 {:rpc "https://eth-mainnet.g.alchemy.com/v2/YOUR_API_KEY"
        :bridge "0x..."} ; KelpDAO Bridge address on Ethereum
   :l2 {:rpc "https://arb-mainnet.g.alchemy.com/v2/YOUR_API_KEY"
        :token "0x..."}  ;rsETH address on Arbitrum
   :interval 12000})     ; 12 seconds (block time)

;; --- Ethereum RPC Helpers ---
(defn- eth-call
  [rpc-url address data]
  (try
    (let [payload {:jsonrpc "2.0"
                   :id 1
                   :method "eth_call"
                   :params [{:to address :data data} "latest"]}
          response @(http/post rpc-url {:body (json/generate-string payload)})]
      (-> response :body json/parse-string (get "result")))
    (catch Exception e
      (println "RPC Error:" (.getMessage e))
      nil)))

(defn- hex->bigint [hex]
  (if (and hex (str/starts-with? hex "0x"))
    (BigInteger. (subs hex 2) 16)
    BigInteger/ZERO))

;; --- Invariant Logic ---
(defn check-solvency
"Compares locked assets on L1 with the total supply on L2."
  []
  (let [;; Function signatures (first 4 bytes of cache)
        total-locked-sig "0xad400bc0" ; totalLocked()
        total-supply-sig "0x18160ddd" ; totalSupply()
        
        l1-raw (eth-call (:rpc (:l1 config)) (:bridge (:l1 config)) total-locked-sig)
        l2-raw (eth-call (:rpc (:l2 config)) (:token (:l2 config)) total-supply-sig)
        
        l1-locked (hex->bigint l1-raw)
        l2-supply (hex->bigint l2-raw)]
    
    (println (format "[%s] Audit Log | L1 Locked: %s | L2 Supply: %s" 
                     (java.util.Date.) l1-locked l2-supply))
    
    (if (and (> l2-supply 0) (< l1-locked l2-supply))
      (do
        (println "!!! ALERT !!!")
        (println "INVARIANT VIOLATION DETECTED: Phantom assets found on L2.")
        (println "Potential Exploit in progress or Oracle Failure."))
      (println "System Status: SECURE"))))

;; --- Entry Point ---
(defn -main [& args]
  (println "Starting KelpDAO Invariant Watcher...")
  (while true
    (check-solvency)
    (Thread/sleep (:interval config))))
	
