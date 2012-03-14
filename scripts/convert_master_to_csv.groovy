def file = new File('c:/tswana_master_list.txt')
def csvfile = new File('c:/tswana.csv')
def results = new HashMap()
def q

file.eachLine { line ->
    if (line.length() == 0){
        q = null
    }
    else{
         if (q == null){
            q = line
        }
        else {
            results.put(q,line)
            println "added"
        }
    }
    println line
}


csvfile.write("\"question\",\"answer\"\r\n")
results.each{ 
   csvfile.append("\"" + it.key + "\",\"" + it.value + "\"\r\n")
}
