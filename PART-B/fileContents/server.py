import os
import socket

s = socket.socket()
s.bind(('localhost', 5000))
s.listen(1)

print('Server waiting for client...')

while True:
  c, addr = s.accept()
  print('Client connected')

  fname = c.recv(1024).decode()
  print('Requested file:', fname)

  if os.path.isfile(fname):
    with open(fname, 'rb') as f:
      data = f.read()

    # Send a fixed 9-byte status so it matches 'NOT FOUND' in length
    c.send(b'FOUND    ')
    c.sendall(data)
    print('File sent successfully')
  else:
    c.send(b'NOT FOUND    ')
    print('File not found')

  # Close connection outside if/else so the client knows transfer is complete
  c.close()