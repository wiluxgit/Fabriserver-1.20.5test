import numpy as np
import transforms3d.quaternions as quat

def sin(x):
    return np.sin(np.deg2rad(x))
def cos(x):
    return np.cos(np.deg2rad(x))

def fmt(vector):
    res = ",".join(f"{v:.10f}f" for v in vector.flatten())
    print(res)
    return f"[{res}]"

ry = 45
roty = np.asarray([
    [ cos(ry), 0,  sin(ry), 0],
    [ 0,       1,  0,       0],
    [-sin(ry), 0,  cos(ry), 0],
    [ 0,       0,  0,       1],
])
rx = 30
rotx = np.asarray([
    [1, 0,        0,       0],
    [0, cos(rx), -sin(rx), 0],
    [0, sin(rx),  cos(rx), 0],
    [0, 0,        0,       1],
])
m = 20
flatten = np.asarray([
    [m, 0,  0, 0],
    [0, m,  0, 0],
    [0, 0,  1, 0],
    [0, 0,  0, m],
])
s = 0.35
scale = np.asarray([
    [s, 0, 0, 0],
    [0, s, 0, 0],
    [0, 0, s, 0],
    [0, 0, 0, 1],
])
t = 0.5 - (1/16)/2
translate = np.asarray([
    [1, 0, 0, 0],
    [0, 1, 0, 0.1],
    [0, 0, 1, t],
    [0, 0, 0, 1],
])

A = translate @ scale @ flatten @ rotx @ roty

nbt = """{
    transformation:"""+fmt(A)+""",
    brightness:{sky:15,block:15},
    item:{id:"minecraft:sponge",Count:1b}}"""
nbt = nbt.replace(" ", "").replace("\n", "")

cmd = "/summon item_display ~ ~1 ~ " + nbt

print()
print(cmd)