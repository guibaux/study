import times
import db_sqlite

type
  User* = object
    username*: string
    following*: seq[string]
  Message = object
    username*: string
    time*: Time
    msg*: string
  Database* = ref object
    db: DbConn

proc newDatabase(filename = "nimter.db"): Database =
  new result
  result.db = open(filename, "", "", "")

proc post*(database: Database, message: Message) =
  if message.msg.len > 140:
    raise newException(ValueError, "Message has to be less than 140 characters.")
  database.db.exec(sql"INSERT INTO Message VALUES (?, ?, ?);",
                      message.username, $message.time.toSeconds().int, message.msg)

proc follow*(database: Database, follower: User, user: User) =
  database.db.exec(sql"INSERT INTO Following VALUES (?, ?);",
                   follower.username, user.username)

proc create*(database: Database, user: User) =
  database.db.exec(sql"INSERT INTO User VALUES (?);", user.username)

var message = Message(
  username: "d0m96",
  time: parse("18:16 - 23 Feb 2016", "H:mm - d MMM yyyy").toTime,
  msg: "Hello to all Nim in Action readers!"
)

var user = User(
  username: "dom96",
  following: @["nim_lang","ManningBooks"]
)

proc post(msg: Message)
proc follow(follower: User ,usr: User)
proc create(usr: User)
