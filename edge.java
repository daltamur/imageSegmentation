public class edge {
    private int greenWeight;
    private int redWeight;
    private int blueWeight;
    private int rgbWeight;
    private vertex vertex1;
    private vertex vertex2;

    //edge has a weight, and the 2 vertices it connects

    public edge(vertex vertex1, vertex vertex2){
        this.vertex1=vertex1;
        this.vertex2=vertex2;
        if(vertex1.getGreenValue()>vertex2.getGreenValue()){
            greenWeight=vertex1.getGreenValue()-vertex2.getGreenValue();
        }else{
            greenWeight=vertex2.getGreenValue()-vertex1.getGreenValue();
        }
        if(vertex1.getBlueValue()>vertex2.getBlueValue()){
            blueWeight=vertex1.getBlueValue()-vertex2.getBlueValue();
        }else{
            blueWeight=vertex2.getBlueValue()-vertex1.getBlueValue();
        }
        if(vertex1.getRedValue()>vertex2.getRedValue()){
            redWeight=vertex1.getRedValue()-vertex2.getRedValue();
        }else{
            redWeight=vertex2.getRedValue()-vertex1.getRedValue();
        }
        if(vertex1.getRgbValue()>vertex2.getRgbValue()){
            rgbWeight=vertex1.getRgbValue()-vertex2.getRgbValue();
        }else{
            rgbWeight=vertex2.getRgbValue()-vertex1.getRgbValue();
        }
    }

    public vertex getVertex1(){
        return vertex1;
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

    public int getRgbWeight(){
        return rgbWeight;
    }
}
