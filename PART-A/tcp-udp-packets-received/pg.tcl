
# Experiment 2: TCP and UDP

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

# Create four nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Create duplex links
$ns duplex-link $n0 $n2 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n2 $n3 1Mb 10ms DropTail

# Set queue limits
$ns queue-limit $n0 $n2 40
$ns queue-limit $n1 $n2 50
$ns queue-limit $n2 $n3 40

# TCP configuration
set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0

set sink0 [new Agent/TCPSink]
$ns attach-agent $n3 $sink0

$ns connect $tcp0 $sink0

# FTP application over TCP
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0
$tcp0 set packetSize_ 1000

# UDP configuration
set udp0 [new Agent/UDP]
$ns attach-agent $n1 $udp0

set null0 [new Agent/Null]
$ns attach-agent $n3 $null0

$ns connect $udp0 $null0

# CBR application over UDP
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0

# Schedule FTP
$ns at 0.75 "$ftp0 start"
$ns at 4.75 "$ftp0 stop"

# Schedule CBR
$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"

# Finish simulation
$ns at 5.0 "finish"

$ns run
