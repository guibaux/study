package main

import (
  "net"
  "log"
  "time"
  "net/http"
)

//Send requests
func stress(ip net.Conn) {
  req, err := http.NewRequest("GET", "192.168.0.1", nil)
  if err != nil {
    return
  }

  req.Header.Add("", value string)
}

func main() {}
