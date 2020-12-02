public class edge {
    private int greenWeight;
    private int redWeight;
    private int blueWeight;
    private int rgbWeight;
    private vertex vertex1;
    private vertex vertex2;
    private int pixelPlacement1;
    private int pixelPlacement2;

    //edge has a weight, and the 2 vertices it connects

    public edge(vertex vertex1, vertex vertex2,int width){
        this.vertex1=vertex1;
        this.vertex2=vertex2;
        this.pixelPlacement1=vertex1.getxValue()+(vertex1.getyValue()*width);
        this.pixelPlacement2=vertex2.getxValue()+(vertex2.getyValue()*width);
        greenWeight=vertex2.getGreenValue()-vertex1.getGreenValue();
        redWeight=vertex2.getRedValue()-vertex1.getRedValue();
        blueWeight=vertex2.getBlueValue()-vertex1.getBlueValue();
        int redDif=(vertex2.getRedValue()-vertex1.getRedValue())*(vertex2.getRedValue()-vertex1.getRedValue());
        int blueDif=(vertex2.getBlueValue()-vertex1.getBlueValue())*(vertex2.getBlueValue()-vertex1.getBlueValue());
        int greenDif=(vertex2.getGreenValue()-vertex1.getGreenValue())*(vertex2.getGreenValue()-vertex1.getGreenValue());
        rgbWeight=redDif+blueDif+greenDif;
    }

    public vertex getVertex1(){
        return vertex1;
    }

    public int getPixelPlacement1(){
        return pixelPlacement1;
    }

    public int getPixelPlacement2(){
        return pixelPlacement2;
    }

    public vertex getVertex2(){
        return vertex2;
    }

    public int getGreenWeight(){
        return greenWeight;
    }

    public int getRedWeight(){
        return redWeight;
    }

    public int getBlueWeight(){
        return blueWeight;
    }

    public double getRgbWeight(){
        return rgbWeight;
    }
}
