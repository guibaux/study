import os, parseutils

type
  Page = object
    domainCode: string
    title: string
    views: int
    size: int

proc parse(line: string, domaincode, pageTitle: var string, countViews, totalSize: var int) =
  var  i = 0
  domainCode.setLen(0)
  i.inc parseUntil(line, domainCode, {' '}, i)
  i.inc
  pageTitle.setLen(0)
  i.inc parseUntil(line, pageTitle, {' '}, i)
  i.inc
  countViews = 0
  i.inc parseInt(line, countViews, i)
  i.inc
  totalSize = 0
  i.inc parseInt(line, totalSize, i)

proc readPageCounts(filename: string, lang = "pt", top = 10) =
  var domainCode = ""
  var pageTitle  = ""
  var countViews = 0
  var totalSize  = 0
  var topPages   = newSeq[Page](top)

  for line in filename.lines:
    parse(line, domainCode, pageTitle, countViews, totalSize)
    if domainCode == lang:
      for i in 0..<top:
        if countViews > topPages[i].views:
          topPages[i] = Page(domainCode: domainCode, title: pageTitle, views: countViews, size: totalSize)
          break
  for i in 0..<top:
    echo i+1, "#: ", topPages[i].title, " views: ", topPages[i].views

when isMainModule:
  var filename: string
  let params = paramCount()
  if params < 1:
    filename = getCurrentDir() / "wiki_data"
  else:
    filename = getCurrentDir() / paramStr(1)

  if params < 3:
    readPageCounts(filename)
  else:
    var top: int
    discard parseInt(paramStr(2), top)
    readPageCounts(filename, paramStr(3), top)
