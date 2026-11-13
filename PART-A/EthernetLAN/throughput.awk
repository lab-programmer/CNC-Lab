
BEGIN {
    ssize = 0;
    startTime = -1;
    stopTime = 0;
    tput = 0;
}

{
    event = $1;
    time = $2;
    size = $6;

    if (event == "+") {
        if (startTime == -1 || time < startTime) {
            startTime = time;
        }
    }

    if (event == "r") {
        if (time > stopTime) {
            stopTime = time;
        }

        ssize += size;
    }
}

END {
    if (startTime >= 0 && stopTime > startTime) {
        tput = (ssize / (stopTime - startTime)) * (8 / 1000);

        printf("Throughput = %.2f Kbps\n", tput);
    }
    else {
        print "Cannot calculate throughput";
    }
}
