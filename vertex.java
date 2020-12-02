import java.awt.*;

public class vertex {
    private int redValue;
    private int blueValue;
    private int greenValue;
    private int rgbValue;
    private int xValue;
    private int yValue;


    //get the coordinates of the pixel to refer to later, get the color to derive rgb values from
    public vertex(int xValue, int yValue, Color color){
        this.xValue=xValue;
        this.yValue=yValue;
        redValue=color.getRed();
        greenValue=color.getGreen();
        blueValue=color.getBlue();
        rgbValue=color.getRGB();
    }

    //methods return references to the variables. the x and y will be used to change the color of the
    //pixel after they're segmented into their respective components.

    public int getRedValue(){
        return redValue;
    }

    public int getBlueValue(){
        return blueValue;
    }

    public int getGreenValue(){
        return greenValue;
    }

    public int getRgbValue(){
        return rgbValue;
    }

    public int getxValue(){
        return xValue;
    }

    public int getyValue(){
        return yValue;
    }

}
