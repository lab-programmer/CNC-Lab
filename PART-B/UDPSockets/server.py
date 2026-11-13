import socket
import re

localIP = "127.0.0.1"
localPort = 20001
bufferSize = 1024

UDPServerSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
UDPServerSocket.bind((localIP, localPort))

print("UDP server up and listening...")

while True:
    bytesAddressPair = UDPServerSocket.recvfrom(bufferSize)
    expression = bytesAddressPair[0].decode()
    clientAddress = bytesAddressPair[1]

    try:
        num1, num2 = re.split(r'\+|-|\*|/|%', expression.replace(' ', ''))
        num1 = float(num1)
        num2 = float(num2)
        result = 0

        if '+' in expression:
            result = num1 + num2
        elif '-' in expression:
            result = num1 - num2
        elif '*' in expression:
            result = num1 * num2
        elif '/' in expression:
            if num2 == 0:
                result = "Division by zero error"
            else:
                result = num1 / num2
        elif '%' in expression:
            result = num1 % num2
        else:
            result = "Invalid Input"
    except ValueError:
        result = "Invalid Input"

    print("Equation from Client:", clientAddress, ":", expression)
    print("Result to Client:", result)

    # Send the result back to the client
    UDPServerSocket.sendto(str.encode(str(result)), clientAddress)