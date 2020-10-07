import os, threadpool, asyncdispatch, asyncnet, protocol

proc connect(socket: AsyncSocket, serverAddr: string,) {.async.} =
    echo "Connecting to ", serverAddr, "..."
    try:
        await socket.connect(serverAddr, 9111.Port)
    except:
        quit "Cannot connect"
    echo "Success!"
    while true:
        let line = await socket.recvLine()
        let parsed = parseMessage(line)
        if parsed.nick != "":
            echo parsed.nick, ": ", parsed.data

var address = "localhost"
var socket = newAsyncSocket()
var nick = "Anon"

if paramCount() != 0:
    nick = paramStr(1)
if paramCount() > 1:
    address = paramStr(2)

asyncCheck connect(socket, address)

    

var inputFtr = spawn stdin.readLine()
while true:
    if inputFtr.isReady():
        let msg = createMessage(nick, ^inputFtr)
        asyncCheck socket.send(msg)
        inputFtr = spawn stdin.readLine()
    asyncdispatch.poll()
