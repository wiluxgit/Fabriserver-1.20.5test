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
scale = np.asarray([
    [1, 0, 0, 0],
    [0, 1, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 1],
])

A = scale @ rotx @ roty

nbt = """{
    transformation:"""+fmt(A)+""",
    item:{id:"minecraft:furnace",Count:1b}}"""
nbt = nbt.replace(" ", "").replace("\n", "")

cmd = "/summon item_display ~ ~1 ~ " + nbt

print()
print(cmd)