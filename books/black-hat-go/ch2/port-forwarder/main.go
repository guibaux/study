package main

import (
  "net"
  "fmt"
  "io"
  "flag"
)

var (
  adress *string
  port *int
  dstPort *int
)

func handler(src net.Conn) {
  dst, err := net.Dial("tcp", fmt.Sprintf("%s:%d", *adress, *port))
  if err != nil {
    fmt.Println("Unable to connect")
  }
  defer dst.Close()

  go func() {
    if _, err := io.Copy(dst, src); err != nil {
      fmt.Println(err)
    }
  }()

  if _, err := io.Copy(src, dst); err != nil {
    fmt.Println(err)
  }
}

func main() {
  port = flag.Int("p", 80, "set port to forward")
  dstPort = flag.Int("d", 80, "set port to receive data")
  adress = flag.String("a", "sr.ht", "set adress to forward")
  flag.Parse()

  fmt.Printf("Forwarding %s:%d to 0.0.0.0:%d\n", *adress, *port, *dstPort)

  listener, err := net.Listen("tcp", fmt.Sprintf(":%d", *dstPort))
  if err != nil {
    fmt.Println("Unable to bind to port")
    return
  }
  for {
    conn, err := listener.Accept()
    if err != nil {
      fmt.Println("Unable to accept connection")
    }
    go handler(conn)
  }
}
