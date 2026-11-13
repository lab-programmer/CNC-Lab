
BEGIN {
}

{
    if (NF >= 2) {
        printf("%f %f\n", $1, $2)
    }
}

END {
}
