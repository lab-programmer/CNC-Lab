
# Create simulator
set ns [new Simulator]

# Create trace files
set nf [open out.nam w]
$ns namtrace-all $nf

set tf [open out.tr w]
$ns trace-all $tf

# Create finish procedure
proc finish {} {
    global ns nf tf cfile0 cfile1

    $ns flush-trace

    close $nf
    close $tf
    close $cfile0
    close $cfile1

    exec nam out.nam &
    exit 0
}

# Create 12 nodes
for {set i 0} {$i < 12} {incr i} {
    set n($i) [$ns node]
}

# Create Ethernet LAN
$ns make-lan "$n(0) $n(1) $n(2) $n(3) $n(4) $n(5) $n(6) $n(7) $n(8) $n(9) $n(10) $n(11)" 50Mb 10ms LL Queue/DropTail Mac/802_3

# Set TCP packet size
Agent/TCP set packetSize_ 1000

# -------------------------------
# First TCP connection
# -------------------------------

set tcp0 [new Agent/TCP]
$ns attach-agent $n(0) $tcp0

set sink0 [new Agent/TCPSink]
$ns attach-agent $n(3) $sink0

$ns connect $tcp0 $sink0

# Trace congestion window of TCP0
set cfile0 [open tcp0.tr w]
$tcp0 attach $cfile0
$tcp0 trace cwnd_

# Create FTP application
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

# -------------------------------
# Second TCP connection
# -------------------------------

set tcp1 [new Agent/TCP]
$ns attach-agent $n(1) $tcp1

set sink1 [new Agent/TCPSink]
$ns attach-agent $n(3) $sink1

$ns connect $tcp1 $sink1

# Trace congestion window of TCP1
set cfile1 [open tcp1.tr w]
$tcp1 attach $cfile1
$tcp1 trace cwnd_

# Create second FTP application
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

# -------------------------------
# Schedule FTP traffic
# -------------------------------

$ns at 0.5 "$ftp0 start"
$ns at 1.0 "$ftp1 start"

$ns at 4.5 "$ftp0 stop"
$ns at 4.5 "$ftp1 stop"

$ns at 5.0 "finish"

# Run simulation
$ns run
