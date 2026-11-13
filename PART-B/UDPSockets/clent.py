import socket

expr = input("Enter the expression: ")
bytesToSend = str.encode(expr)
serverAddressPort = ("127.0.0.1", 20001)
bufferSize = 1024

UDPClientSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
UDPClientSocket.sendto(bytesToSend, serverAddressPort)

msgFromServer = UDPClientSocket.recvfrom(bufferSize)
print(expr, "=", msgFromServer[0].decode())