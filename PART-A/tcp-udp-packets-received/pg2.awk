
BEGIN {
    tcp = 0
    udp = 0
}

{
    event = $1
    destination = $4
    packet = $5

    if (event == "r" && destination == 3) {

        if (packet == "tcp") {
            tcp++
        }

        if (packet == "cbr") {
            udp++
        }
    }
}

END {
    printf "Number of TCP packets received: %d\n", tcp
    printf "Number of UDP packets received: %d\n", udp
}
