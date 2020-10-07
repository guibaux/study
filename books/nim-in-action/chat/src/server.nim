import asyncdispatch, asyncnet, protocol

type 
    Client = ref object
        socket: AsyncSocket
        address: string
        id: int 
        connected: bool
    Server = ref object
        socket: AsyncSocket
        clients: seq[Client]

proc newServer(): Server = Server(socket: newAsyncSocket(), clients: @[])
proc `$`(client: Client): string = $client.id & " -> " & client.address

proc processMessage(server: Server, client: Client) {.async.} = 
    while true:
        let line = await client.socket.recvLine()
        if len(line) == 0:
            echo client, " left!"
            client.connected = false
            client.socket.close()
            return
        echo client, " sent: ", line
        for c in server.clients:
            if c.id != client.id and c.connected:
                await c.socket.send(line & "\c\l")

proc loop(server: Server, port = 9111) {.async.} =
    server.socket.bindAddr(port.Port)
    server.socket.listen() 
    while true:
        let (address, clientSocket) = await server.socket.acceptAddr()
        echo "Connected to ", address
        let client = Client(
            socket: clientSocket,
            address: address,
            id: len(server.clients),
            connected: true
        )
        server.clients.add(client)            
        asyncCheck processMessage(server,client)
var server = newServer()
waitFor loop(server)
