package main

import (
  "net"
  "bufio"
  "log"
  "flag"
  "fmt"
)

func echo(conn net.Conn) {
  defer conn.Close()

  reader := bufio.NewReader(conn)

  s, err := reader.ReadString('\n')

  if err != nil {
    log.Fatalln("Unable to read data")
  }

  log.Printf("Receive %d bytes from %s", len(s), s)
  log.Println("Echoing data")
  writer := bufio.NewWriter(conn)
  _, err = writer.WriteString(s)

  if err != nil {
    log.Fatalln("Unable to echo data")
  }
  writer.Flush()
}

func main() {
  port := flag.Int("p", 1337, "set port to echo data")
  flag.Parse()

  sv, err := net.Listen("tcp", fmt.Sprintf(":%d", *port))
  if err != nil {
    log.Fatalf("Unable to bind port %d\n", *port)
  }

  log.Printf("Echoing in port 0.0.0.0:%d\n", *port)

  for {
    conn, err := sv.Accept()
    log.Println("Received connection")
    if err != nil {
      log.Fatalln("Unable to accept connection")
    }
    go echo(conn)
  }
}
