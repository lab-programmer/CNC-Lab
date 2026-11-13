
BEGIN {
    startTime = 0
    stopTime = 0
    size = 0
    count = 0
}

{
    event = $1
    time = $2
    destination = $4
    packet = $5
    pktSize = $6

    # Count only TCP packets received at node 9
    if (event == "r" && destination == 9 && packet == "tcp") {

        if (count == 0) {
            startTime = time
        }

        stopTime = time
        size += pktSize
        count++
    }
}

END {
    if (stopTime > startTime) {
        throughput = (size * 8) / ((stopTime - startTime) * 1000)

        printf "Total packets received: %d\n", count
        printf "Total data received: %d bytes\n", size
        printf "Throughput: %.2f Kbps\n", throughput
    }
    else {
        print "No TCP packets received."
    }
}
