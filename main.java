import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static jdk.nashorn.internal.objects.Global.Infinity;

public class main {
    public static void main(String[] args)throws IOException {
        BufferedImage image=null;
        BufferedImage image5=null;
        File file=null;
        try{
            file=new File("C:\\Users\\theif\\Downloads\\test3.jpg");
            image= ImageIO.read(file);
            image5=ImageIO.read(file);
        }catch (IOException e){
            System.out.println(e);
        }
        //this just turns the top half of the image red.
        BufferedImage image2=image;
        int width=image2.getWidth();
        int height=image2.getHeight();
        for(int y=0;y<height/2;y++){
            for (int x=0;x<width;x++){
               //int changedColor=image2.getRGB(x,y);
               //Color newColor=new Color(changedColor);
               //int redValue=newColor.getRed();
               //Color setColor=new Color(50,50,5);
               //int newC=setColor.getRGB();
                int R = (int)(Math.random()*256);
                int G = (int)(Math.random()*256);
                int B= (int)(Math.random()*256);
                Color color = new Color(R, G, B); //random color, but can be bright or dull

//to get rainbow, pastel colors
                Random random = new Random();
                final float hue = random.nextFloat();
                final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
                final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
                color = Color.getHSBColor(hue, saturation, luminance);
                int totalColor=color.getRGB();//vertices.get(0).getRgbValue();
               image2.setRGB(x,y,totalColor);
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
        ArrayList<vertex>vertices=new ArrayList<>();
        //we'll use this hashtable later on to find vertices that might connect to components we're comparing.
        Hashtable<String,ArrayList<edge>>edgesLeavingVertex=new Hashtable<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int rgbColorAnchored=image.getRGB(x,y);
                Color anchoredColor=new Color(rgbColorAnchored);
                vertex anchored=new vertex(x,y,anchoredColor);
                vertices.add(anchored);
            }
        }
        Hashtable<String, component>components=new Hashtable<>();
        component builderComponent;
        //Make the initital components
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int rgbColor=image.getRGB(x,y);
                Color anchoredColor=new Color(rgbColor);
                vertex addedVertex=new vertex(x,y,anchoredColor);
                builderComponent=new component(addedVertex);
                components.put(builderComponent.getKey(),builderComponent);
            }
        }
        ArrayList<edge>edges=new ArrayList<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++) {
                if (x == 0 && y!=height-1) {
                    //very first pixel
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    component currentComponent=components.get(x+","+y);
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    int varyingRGB=image.getRGB(x+1,y);
                    //get the other component to add the list of edges leaving it
                    component currentComponent2=components.get(String.valueOf(x+1)+","+y);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    components.replace(String.valueOf(x+1)+","+y,currentComponent2);
                    //directly underneath
                    currentComponent2=components.get(String.valueOf(x)+","+String.valueOf(y+1));
                    varyingRGB=image.getRGB(x,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace(String.valueOf(x)+","+String.valueOf(y+1),currentComponent2);
                    //bottom diagonal right
                    currentComponent2=components.get(String.valueOf(x+1)+","+String.valueOf(y+1));
                    varyingRGB=image.getRGB(x+1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x+1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    components.replace(String.valueOf(x+1)+","+String.valueOf(y+1),currentComponent2);
                    currentComponent.addExternalEdge(addedEdge);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                    components.replace(x+","+y,currentComponent);
                } else if (y != height - 1 && x == width - 1) {
                    //the ending node of pixel lines that arent the first or last line, same as first case, really. just now you get the left corner and bottom
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    component currentComponent=components.get(x+","+y);
                    component currentComponent2=components.get(String.valueOf(x)+","+String.valueOf(y+1));
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
                    edge addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace(String.valueOf(x)+","+String.valueOf(y+1),currentComponent2);
                    //directly underneath
                    //bottom diagonal left
                    currentComponent2=components.get(String.valueOf(x-1)+","+String.valueOf(y+1));
                    varyingRGB=image.getRGB(x-1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x-1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace(String.valueOf(x-1)+","+String.valueOf(y+1),currentComponent2);
                    components.replace(x+","+y,currentComponent);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                }else if(y==height-1&&x!=width-1){
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    component currentComponent=components.get(x+","+y);
                    //last line of pixels
                    //just get the rightmost pixel for each besides the last.
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    component currentComponent2=components.get(String.valueOf(x+1)+","+String.valueOf(y));
                    int varyingRGB=image.getRGB(x+1,y);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    currentEdges.add(addedEdge);
                    components.replace(String.valueOf(x+1)+","+String.valueOf(y),currentComponent2);
                    components.replace(x+","+y,currentComponent);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                }else if(y!=height-1&&x!=width-1){
                    ArrayList<edge>currentEdges=edgesLeavingVertex.get(String.valueOf(x)+","+String.valueOf(y));
                    if(currentEdges==null){
                        currentEdges=new ArrayList<>();
                    }
                    component currentComponent=components.get(x+","+y);
                    //middle elements
                    //very first pixel
                    int rgbColorAnchored=image.getRGB(x,y);
                    Color anchoredColor=new Color(rgbColorAnchored);
                    vertex anchored=new vertex(x,y,anchoredColor);
                    //directly to right
                    component currentComponent2=components.get((x+1)+","+y);
                    int varyingRGB=image.getRGB(x+1,y);
                    Color varyingColor=new Color(varyingRGB);
                    vertex variedVertex=new vertex(x+1,y,varyingColor);
                    edge addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    //directly underneath
                    components.replace((x+1)+","+y,currentComponent2);
                    currentComponent2=components.get(x+","+(y+1));
                    varyingRGB=image.getRGB(x,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace((x)+","+(y+1),currentComponent2);
                    //bottom diagonal right
                    currentComponent2=components.get((x+1)+","+(y+1));
                    varyingRGB=image.getRGB(x+1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x+1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace((x+1)+","+(y+1),currentComponent2);
                    currentComponent2=components.get((x-1)+","+(y+1));
                    varyingRGB=image.getRGB(x-1,y+1);
                    varyingColor=new Color(varyingRGB);
                    variedVertex=new vertex(x-1,y+1,varyingColor);
                    addedEdge=new edge(anchored,variedVertex);
                    edges.add(addedEdge);
                    currentEdges.add(addedEdge);
                    currentComponent2.addExternalEdge(addedEdge);
                    currentComponent.addExternalEdge(addedEdge);
                    components.replace((x-1)+","+(y+1),currentComponent2);
                    edgesLeavingVertex.replace(String.valueOf(x)+","+String.valueOf(y),currentEdges);
                    components.replace(x+","+y,currentComponent);
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
        boolean correctSort=true;
        for(int i=0;i<edges.size();i++){
            if(i+1!=edges.size()) {
                if (edges.get(i).getRedWeight() > edges.get(i + 1).getRedWeight()) {
                    correctSort = false;
                }
            }
        }
        if(correctSort){
            System.out.println("it worked");
        }
        int pixels=height*width;
        System.out.println("Correct initial components: "+pixels);
        System.out.println("Number of pixels: "+components.size());
        //int c=0;
        //while(enu.hasMoreElements()&&c<100){
        //    System.out.println("Vertice: "+enu.nextElement());
        //    c++;
        //}
        //these will hold our finished components
        ArrayList<component>newComponents=new ArrayList<>();
        for(edge currentEdge:edges){
            vertex vertex1=currentEdge.getVertex1();
            vertex vertex2=currentEdge.getVertex2();
            String key1=(vertex1.getxValue())+","+(vertex1.getyValue());
            String key2=(vertex2.getxValue())+","+(vertex2.getyValue());
            if(components.get(key1)!=components.get(key2)){
                component component1=components.get(key1);
                component component2=components.get(key2);
                int k1=300/component1.getSize();
                int k2=300/component2.getSize();

                //get possible minimum weights connecting the segments by checking the hash table that connects vertices
                if((currentEdge.getRedWeight()<=component1.getRedWeight()+k1)&&(currentEdge.getRedWeight()<=component2.getRedWeight()+k2)){
                    if((currentEdge.getBlueWeight()<=component1.getBlueWeight()+k1)&&(currentEdge.getBlueWeight()<=component2.getBlueWeight()+k2)) {
                        if((currentEdge.getGreenWeight()<=component1.getGreenWeight()+k1)&&(currentEdge.getGreenWeight()<=component2.getGreenWeight()+k2)) {
                            if (component1.getVertices().size() >= component2.getVertices().size()) {
                                component1.union(component2);
                                component1.changeLowestWeight(currentEdge.getRgbWeight(),currentEdge.getRedWeight(),currentEdge.getBlueWeight(),currentEdge.getGreenWeight());
                                components.replace(vertex1.getxValue() + "," + vertex1.getyValue(), component1);
                                components.replace(vertex2.getxValue() + "," + vertex2.getyValue(), component1);
                            } else {
                                component2.union(component1);
                                component2.changeLowestWeight(currentEdge.getRgbWeight(),currentEdge.getRedWeight(),currentEdge.getBlueWeight(),currentEdge.getGreenWeight());
                                components.replace(vertex1.getxValue() + "," + vertex1.getyValue(), component2);
                                components.replace(vertex2.getxValue() + "," + vertex2.getyValue(), component2);
                            }
                        }
                    }
                }
                // that are potential candidates for being connected components by edges
            }
        }

        //fill up the components arrayList with UNIQUE components
        Enumeration enu=components.keys();
        while(enu.hasMoreElements()){
            String key=enu.nextElement().toString();
            component current=components.get(key);
           if(newComponents.isEmpty()||!newComponents.contains(current)){
               newComponents.add(current);
           }
        }
        Hashtable<component,Color>colors=new Hashtable<>();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                component current=components.get(x+","+y);
                Color color=colors.get(current);
                if(color==null){
                    int R = (int)(Math.random()*256);
                    int G = (int)(Math.random()*256);
                    int B= (int)(Math.random()*256);
                    color = new Color(R, G, B); //random color, but can be bright or dull

//to get rainbow, pastel colors
                    Random random = new Random();
                    final float hue = random.nextFloat();
                    final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
                    final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
                    color = Color.getHSBColor(hue, saturation, luminance);
                    image.setRGB(x,y,color.getRGB());
                    colors.put(current,color);
                }else{
                    image.setRGB(x,y,color.getRGB());
                }

            }

        }
        System.out.println(newComponents.size());
        return image;
    }

    private static void greenmergeSort(ArrayList<edge> edges, int p, int r) {
        if(p<r) {
            int q = ((p + r) / 2);
            greenmergeSort(edges, p, q);
            greenmergeSort(edges, q + 1, r);
            mergeGreen(edges, p, q, r);
        }


    }

    private static void mergeGreen(ArrayList<edge> edges, int p, int q, int r) {
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
            if(L.get(i).getGreenWeight()<=R.get(j).getGreenWeight()){
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

    private static void mergeSortBlue(ArrayList<edge> edges, int p, int r) {
        if(p<r) {
            int q = ((p + r) / 2);
            mergeSortBlue(edges, p, q);
            mergeSortBlue(edges, q + 1, r);
            mergeBlue(edges, p, q, r);
        }
    }

    private static void mergeBlue(ArrayList<edge> edges, int p, int q, int r) {
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
            if(L.get(i).getBlueWeight()<=R.get(j).getBlueWeight()){
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
            if(L.get(i).getRedWeight()<=R.get(j).getRedWeight()){
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

