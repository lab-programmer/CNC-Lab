
# Experiment 3: Ethernet LAN and Throughput

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

# Create 10 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]
set n8 [$ns node]
set n9 [$ns node]

# Create Ethernet LAN
$ns make-lan "$n0 $n1 $n2 $n3 $n4 $n5 $n6 $n7 $n8 $n9" 10Mb 0.5ms LL Queue/DropTail Mac/802_3

# Create TCP sender
set tcp0 [new Agent/TCP]
$ns attach-agent $n3 $tcp0

# Create TCP receiver
set sink0 [new Agent/TCPSink]
$ns attach-agent $n9 $sink0

# Connect TCP sender and receiver
$ns connect $tcp0 $sink0

# Create FTP application
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

# Set packet size
$tcp0 set packetSize_ 1000

# Schedule FTP transmission
$ns at 0.75 "$ftp0 start"
$ns at 4.75 "$ftp0 stop"

# Finish simulation
$ns at 5.0 "finish"

# Run simulation
$ns run
