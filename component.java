import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class component {
private int lowestWeight;
private int redWeight;
private int blueWeight;
private int greenWeight;
private ArrayList<vertex> vertices=new ArrayList<>();
private String key;
private ArrayList<edge> externalEdges=new ArrayList<>();
private int size;

    //for(component x:newComponents){
//to get rainbow, pastel colors
    //image=x.recolor(image);
    //}

    //for(component x:newComponents){
//to get rainbow, pastel colors
    //image=x.recolor(image);
    //}
//creates the component
public component(vertex initialComponent){
    lowestWeight=0;
    redWeight=0;
    blueWeight=0;
    greenWeight=0;
    vertices.add(initialComponent);
    size=1;
    this.key=String.valueOf(initialComponent.getxValue())+","+String.valueOf(initialComponent.getyValue());
}

public String getKey(){
    return key;
}

public int getSize(){
    return size;
}

public void changeLowestWeight(int wholeWeight, int redWeight, int blueWeight,int greenWeight){
    lowestWeight=wholeWeight;
    this.redWeight=redWeight;
    this.blueWeight=blueWeight;
    this.greenWeight=greenWeight;
}

public void changeRedWeight(int red){
    redWeight=red;
}

public void changeBlueWeight(int blue){
    blueWeight=blue;
}


public void changeGreenWeight(int green){
    greenWeight=green;
}
public int getRedWeight(){
    return redWeight;
}

public int getGreenWeight(){
    return greenWeight;
}

public int getBlueWeight(){
    return blueWeight;
}

public int getLowestWeight(){
    return lowestWeight;
}

public ArrayList<vertex> getVertices(){
    return vertices;
}

public void union(component y){
    this.vertices.addAll(y.vertices);
    size=size+y.size;
}

public BufferedImage recolor(BufferedImage image){
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
    for(vertex y:vertices){
        image.setRGB(y.getxValue(),y.getyValue(),totalColor);
    }
    return image;
}

public void addExternalEdge(edge edge){
    externalEdges.add(edge);
}

public ArrayList<edge> getExternalEdges(){
    return externalEdges;
}

public void replaceExternalEdges(ArrayList<edge>newList){
    externalEdges=newList;
}




}
