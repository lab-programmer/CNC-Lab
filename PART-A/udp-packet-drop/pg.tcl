
# Experiment 1: UDP - 3 Node Point-to-Point Network

set ns [new Simulator]

# Create trace and NAM files
set tf [open out.tr w]
set nf [open out.nam w]

$ns trace-all $tf
$ns namtrace-all $nf

# Finish procedure
proc finish {} {
    global ns tf nf

    $ns flush-trace
    close $tf
    close $nf

    exec nam out.nam &
    exit 0
}

# Create 3 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

# Create duplex links
$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail

# Set queue limits
$ns queue-limit $n0 $n1 50
$ns queue-limit $n1 $n2 50

# Create UDP agent
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0

# Create Null agent (receiver)
set null0 [new Agent/Null]
$ns attach-agent $n2 $null0

# Connect UDP sender and receiver
$ns connect $udp0 $null0

# Create CBR traffic
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0

# Schedule events
$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"
$ns at 5.0 "finish"

# Run simulation
$ns run
