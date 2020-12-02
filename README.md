# imageSegmentation
Takes an image and segments the different discernible regions. This apparently works better if the three separate color channels are considered rather than one single Euclidian distance.
May come back to this one day to see how substantial the change is.
Without making this readme an essay, the program essentially just groups together segments of an image that are roughly the same color and thus recognizes the separate regions of the image.

This works on pngs as well but I used jpgs because I'm dumb.
Check out the images for an idea of how a segmentation looks, make the different segments neon colors to highlight the areas of the image the program sees as a separate region. 
