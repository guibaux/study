package main

import (
  "flag"
  "fmt"
  "net"
  "sort"
  "time"
)

var (
  host *string
  portsRange *int
  timeoutVal *int
)

func printHelp() {
  fmt.Println("Usage:")
  flag.PrintDefaults()
}

func worker(ports, results chan int) {
  for p := range ports {
    conn, err := net.DialTimeout("tcp", fmt.Sprintf("%s:%d", *host, p), 2 * time.Second)

    if err != nil {
      results <- 0
      continue
    }

    conn.Close()
    results <- p
  }
}

func main() {
  // Flags config
  flag.Usage = printHelp
  host = flag.String("a", "scanme.nmap.org", "set adress")
  portsRange = flag.Int("r", 1024, "set range of ports 0...")
  timeoutVal = flag.Int("t", 5, "set timeout value in seconds")

  channelBuf := flag.Int("b", 100, "set buffer size, high values = + speed but - reliability")
  unsortedResults := flag.Bool("u", false, "show open ports unsorted but more faster")
  flag.Parse()

  // Declarations
  ports := make(chan int, *channelBuf)
  results := make(chan int)
  var openPorts []int

  fmt.Printf("Scanning %s\n", *host)

  for i := 0; i < cap(ports); i++ {
    go worker(ports, results)
  }

  go func() {
    for i := 1; i <= *portsRange; i++ {
      ports <- i
    }
  }()

  //Main thread
  for i := 0; i < *portsRange; i++ {
    port := <-results
    if port != 0 {
      if *unsortedResults {
        fmt.Printf("%d open\n", port)
      }else {
        openPorts = append(openPorts, port)
      }
    }
  }
  close(ports)
  close(results)

  if !*unsortedResults {
    sort.Ints(openPorts)
    for _, port := range openPorts {
      fmt.Printf("%d open\n", port)
    }
  }
}
