import json

type Message* = object
    nick*: string
    data*: string

proc parseMessage*(data: string): Message =
    try:          
        let dataJson = parseJson(data)
        result.nick = dataJson["nick"].getStr()
        result.data = dataJson["data"].getStr()
    except JsonParsingError:
        discard
    except KeyError:
        discard

proc createMessage*(nick, data: string): string = 
    return $ %{"nick": %nick, "data": %data} & "\c\l"


# Tests, only executed if not imported
when isMainModule:
    # Creating Messages
    const expected = """{"nick":"Ned","data":"Oi"}""" & "\c\l"
    doAssert createMessage("Ned", "Oi") == expected

    # Parsing Test
    const parsed = parseMessage(expected)
    doAssert parsed.nick == "Ned"
    doAssert parsed.data == "Oi"

    echo "Everything OK Sir!"

