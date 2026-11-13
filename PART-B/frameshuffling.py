import random as rd

msg = input("Enter the message: ")

frames = [
            [i//3, msg[i:i+3]] for i in range (0, len(msg), 3)
         ]
print("Frames: ", frames)

rd.shuffle(frames)

print ("Shuffled Frames: ", frames)

frames.sort()

print("Sorted Frames: ", frames)

mes = ''.join(frame[1] for frame in frames)

print("Message: ", mes)

