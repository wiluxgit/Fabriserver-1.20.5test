import os
import PIL
import PIL.Image
import PIL.ImageColor
import numpy as np
import itertools

def make_model_def(modelname):
    return """{
	"parent": "polyworks:guiitem/digit/_base",
	"textures": {
		"layer0": \"polyworks:"""+modelname+"""\"
	}
}"""


modelroot = "../assets/polyworks/models/guiitem/digit"
textureroot = "../assets/polyworks/textures/guiitem/digit"
os.makedirs(modelroot, exist_ok=True)
os.makedirs(textureroot, exist_ok=True)

def save(modelname, img):
    print(modelname)
    imgpath = f"{textureroot}/{modelname}.png"
    jsonpath = f"{modelroot}/{modelname}.json"
    with open(jsonpath, "w+") as f:
        f.write(make_model_def(f"guiitem/digit/{modelname}"))

    PIL.Image.fromarray(img).save(imgpath)

pil_img = PIL.Image.open("digits.png")
img = np.asarray(pil_img)
(y,x,c) = img.shape
dx = 6
for number, magnitude in itertools.product(range(1,10), range(3)):
    magnitude_letter = ["s","k","m"][magnitude]
    numx1 = dx*(number)
    numx2 = dx*(number+1)
    outimg = np.zeros((32, 32, c), dtype=np.uint8)
    outimg[:y,0:dx,:] = img[:,numx1:numx2,:]
    if magnitude > 0:
        magx1 = dx*(magnitude+9)
        magx2 = dx*(magnitude+10)
        outimg[:y,(dx*3):(dx*4),:] = img[:,magx1:magx2,:]

    fname = f"digit_{number}xx{magnitude_letter}"
    save(fname, outimg)

    fname = f"digit_{number}0x{magnitude_letter}"
    outimg[:y,(dx*1):(dx*2),:] = img[:,:dx,:]
    save(fname, outimg)

    fname = f"digit_{number}00{magnitude_letter}"
    outimg[:y,(dx*2):(dx*3),:] = img[:,:dx,:]
    save(fname, outimg)

# fix two special cases for 1k/1m and just k/m
for magnitude in range(1,3):
    magnitude_letter = ["s","k","m"][magnitude]
    outimg = np.zeros((32, 32, c), dtype=np.uint8)

    if magnitude > 0:
        magx1 = dx*(magnitude+9)
        magx2 = dx*(magnitude+10)
        outimg[:y,(dx*3):(dx*4),:] = img[:,magx1:magx2,:]

    fname = f"digit_special_{magnitude_letter}"
    save(fname, outimg)

    numx1 = dx*(1)
    numx2 = dx*(1+1)
    outimg[:y,(dx*2):(dx*3),:] = img[:,numx1:numx2,:]

    fname = f"digit_special_1{magnitude_letter}"
    save(fname, outimg)
