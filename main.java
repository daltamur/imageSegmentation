import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args)throws IOException {
        BufferedImage image=null;
        BufferedImage image5=null;
        File file=null;
        try{
            file=new File("C:\\Users\\theif\\downloads\\bball.jpg");
            image= ImageIO.read(file);
            image5=ImageIO.read(file);
        }catch (IOException e){
            System.out.println(e);
        }
        //this just turns the top half of the image red.
        BufferedImage image2=image;
        assert image2 != null;
        int width=image2.getWidth();
        int height=image2.getHeight();
        for(int y=0;y<height/2;y++){
            for (int x=0;x<width;x++){
               int changedColor=image2.getRGB(x,y);
               Color newColor=new Color(changedColor);
               int redValue=newColor.getRed();
               Color setColor=new Color(0,0,0);
               int newC=setColor.getRGB();
               image2.setRGB(x,y,newC);
            }
        }

        try {
            File output = new File("C:\\Users\\theif\\Downloads\\romanceAlbum2.jpg");
            ImageIO.write(image, "jpg", output);
        }catch (IOException e){
            System.out.println(e);
        }
        BufferedImage image3=image5;
        image3=imageSegmentation(image3, height, width);
        try {
            File output = new File("C:\\Users\\theif\\Downloads\\romanceAlbum3.jpg");
            ImageIO.write(image3, "jpg", output);
        }catch (IOException e){
            System.out.println(e);
        }




    }

    private static BufferedImage imageSegmentation(BufferedImage image, int height, int width) {
        //height=2;
        //width=9;
        //create an arraylist to hold the edge objects;
        Integer[]universe=new Integer[width*height];
        int universeCounter=0;

        //we'll use this hashtable later on to find vertices that might connect to components we're comparing.
        Hashtable<String,ArrayList<edge>>edgesLeavingVertex=new Hashtable<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                universe[universeCounter]=x+(y*width);
                universeCounter++;
            }
        }
        disjointSet disjointSet=new disjointSet(universe);
        ArrayList<edge>edges=new ArrayList<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++) {
                if (x == 0 && y!=height-1) {
                    //very first pixel
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    int varyingRGB=image.getRGB(x+1,y);
                    //get the other component to add the list of edges leaving it
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    //directly underneath
                    varyingRGB=image.getRGB(x,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    //bottom diagonal right
                    varyingRGB=image.getRGB(x+1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x+1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                } else if (y != height - 1 && x == width - 1) {
                    //the ending node of pixel lines that arent the first or last line, same as first case, really. just now you get the left corner and bottom
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    int varyingRGB=image.getRGB(x,y+1);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x,y+1,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    //directly underneath
                    //bottom diagonal left
                    varyingRGB=image.getRGB(x-1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x-1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                }else if(y==height-1&&x!=width-1){
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    //last line of pixels
                    //just get the rightmost pixel for each besides the last.
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    int varyingRGB=image.getRGB(x+1,y);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                }else if(y!=height-1&&x!=width-1){
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    //middle elements
                    //very first pixel
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    int varyingRGB=image.getRGB(x+1,y);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    //directly underneath
                    varyingRGB=image.getRGB(x,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    //bottom diagonal right
                    varyingRGB=image.getRGB(x+1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x+1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    varyingRGB=image.getRGB(x-1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x-1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex,width);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                }
            }
        }
        int correctedges= (int) ((((width-1)*(height)))+(((height-1)*(width))+(2.0*(width-1)*(height-1))));
        System.out.println("Edges: "+edges.size());
        System.out.println("Correct number of edges: "+correctedges);
        System.out.println("Graph Created");
        //each edge holds the 2 vertices it connects, the vertices hold the rgb values and x,y coordinate of the pixel they reference.
        //next, we have to sort the edges in nondecreasiing order. we'll sort them based on the entire rgb number
        //sorting the edges from least to greatest
        int r=edges.size()-1;
        mergeSort(edges,0,r);
        System.out.println("Edges Sorted");

        int pixels=height*width;
        for(edge currentEdge:edges){
            int pixelPos1=currentEdge.getPixelPlacement1();
            int pixelPos2=currentEdge.getPixelPlacement2();
            if(!disjointSet.find(pixelPos1).equals(disjointSet.find(pixelPos2))){
                float size1F=Float.valueOf(disjointSet.getSize(pixelPos1));
                float size2F=Float.valueOf(disjointSet.getSize(pixelPos2));
                float k1=90000/size1F;
                float k2=90000/size2F;
                //get possible minimum weights connecting the segments by checking the hash table that connects vertices
                if((currentEdge.getRgbWeight()<=disjointSet.getInternalDif(pixelPos1)+k1)&&(currentEdge.getRgbWeight()<=disjointSet.getInternalDif(pixelPos2)+k2)){
                                disjointSet.union(pixelPos1,pixelPos2,currentEdge.getRgbWeight());
                }
            }
        }

        for(edge currentEdge:edges){
            int pixelPos1=currentEdge.getPixelPlacement1();
            int pixelPos2=currentEdge.getPixelPlacement2();
            if(!disjointSet.find(pixelPos1).equals(disjointSet.find(pixelPos2))&&((disjointSet.getSize(pixelPos1)<50)||disjointSet.getSize(pixelPos2)<50)) {
                disjointSet.union(pixelPos1,pixelPos2,currentEdge.getRgbWeight());
            }
        }


        //fill up the components arrayList with UNIQUE components
        Hashtable<Integer,Color>colors=new Hashtable<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int current=disjointSet.find(x+(y*width));
                Color color=colors.get(current);
                if(color==null){
                    int R = (int)(Math.random()*256);
                    int G = (int)(Math.random()*256);
                    int B= (int)(Math.random()*256);
                    color = new Color(R, G, B);
                    image.setRGB(x,y,color.getRGB());
                    colors.put(current,color);
                }else{
                    image.setRGB(x,y,color.getRGB());
                }

            }

        }
        return image;
    }



    //your basic mergesort, helps keep the runtime reasonable and around 10 seconds to sort for larger images
    private static void mergeSort(ArrayList<edge> edges, int p, int r) {
        if(p<r) {
            int q = ((p + r) / 2);
            mergeSort(edges, p, q);
            mergeSort(edges, q + 1, r);
            merge(edges, p, q, r);
        }
    }

    private static void merge(ArrayList<edge> edges, int p, int q, int r) {
        int n1=q-p+1;
        int n2=r-q;
        ArrayList<edge>L=new ArrayList<>(n1);
        ArrayList<edge>R=new ArrayList<>(n2);
        for(int i=0;i<n1;i++){
            L.add(edges.get(p+i));
        }
        for(int j=0;j<n2;j++){
            R.add(edges.get(q+j+1));
        }

        int i=0;
        int j=0;
        int k=p;
        while(i<n1&&j<n2){
            if(L.get(i).getRgbWeight()<=R.get(j).getRgbWeight()){
                edges.set(k,L.get(i));
                i++;
            }else{
                edges.set(k,R.get(j));
                j++;
            }
            k++;
        }

        while(i<n1){
            edges.set(k,L.get(i));
            i++;
            k++;
        }

        while(j<n2){
            edges.set(k,R.get(j));
            j++;
            k++;
        }
    }

}

