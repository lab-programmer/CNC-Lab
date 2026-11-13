
# Experiment 10: Ethernet LAN with Multiple TCP Traffic Sources

# Create simulator
set ns [new Simulator]

# Create trace files
set nf [open exp10.nam w]
set tf [open exp10.tr w]

$ns namtrace-all $nf
$ns trace-all $tf

# Finish procedure
proc finish {} {
    global ns nf tf cwnd0 cwnd1

    $ns flush-trace

    close $nf
    close $tf
    close $cwnd0
    close $cwnd1

    exec nam exp10.nam &
    exit 0
}

# Create 12 nodes
for {set i 0} {$i < 12} {incr i} {
    set n($i) [$ns node]
}

# Create Ethernet LAN
$ns make-lan "$n(0) $n(1) $n(2) $n(3) $n(4) $n(5) $n(6) $n(7) $n(8) $n(9) $n(10) $n(11)" 50Mb 10ms LL Queue/DropTail Mac/802_3

# --------------------------------
# First TCP connection: n0 to n3
# --------------------------------

set tcp0 [new Agent/TCP]
$ns attach-agent $n(0) $tcp0

set sink0 [new Agent/TCPSink]
$ns attach-agent $n(3) $sink0

$ns connect $tcp0 $sink0

# Trace congestion window of TCP 0
set cwnd0 [open tcp0.tr w]
$tcp0 attach $cwnd0
$tcp0 trace cwnd_

# FTP application over TCP 0
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

$tcp0 set packetSize_ 1000

# --------------------------------
# Second TCP connection: n1 to n3
# --------------------------------

set tcp1 [new Agent/TCP]
$ns attach-agent $n(1) $tcp1

set sink1 [new Agent/TCPSink]
$ns attach-agent $n(3) $sink1

$ns connect $tcp1 $sink1

# Trace congestion window of TCP 1
set cwnd1 [open tcp1.tr w]
$tcp1 attach $cwnd1
$tcp1 trace cwnd_

# FTP application over TCP 1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

$tcp1 set packetSize_ 1000

# --------------------------------
# Schedule traffic
# --------------------------------

$ns at 0.5 "$ftp0 start"
$ns at 0.5 "$ftp1 start"

$ns at 4.5 "$ftp0 stop"
$ns at 4.5 "$ftp1 stop"

# Finish simulation
$ns at 5.0 "finish"

# Run simulation
$ns run
