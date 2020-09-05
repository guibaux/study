package main

import (
  "flag"
  "fmt"
  "net"
)

func printHelp() {
  fmt.Println("Usage:")
  flag.PrintDefaults()
}

func main() {
  flag.Usage = printHelp
  host := flag.String("a", "scanme.nmap.org", "set adress")
  portsRange := flag.Int("r", 1024, "set range of ports 0...")
  flag.Parse()

  fmt.Printf("Scanning %s\n", *host)

  for i := 1; i <= *portsRange; i++ {
    conn, err := net.Dial("tcp", fmt.Sprintf("%s:%d", *host, i))

    if err != nil {
      continue
    }

    conn.Close()
    fmt.Printf("Port %d open\n", i)
  }
}
