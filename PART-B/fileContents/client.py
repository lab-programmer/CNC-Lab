import socket

s = socket.socket()
# Port matched to 5000 (was 8888 in notes)
s.connect(('localhost', 5000))

fname = input('Enter file name: ')
s.send(fname.encode())

# Receives exact 9-byte status ('FOUND    ' or 'NOT FOUND')
status = s.recv(9)

if status == b'FOUND    ':
  print('File Contents:', end=' ')
  data = s.recv(1024)
  while data:
    print(data.decode(), end='')
    data = s.recv(1024)
  print()  # Clean newline after file output
else:
  print('File not found')

s.close()