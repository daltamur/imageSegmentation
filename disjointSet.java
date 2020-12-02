import javax.print.attribute.Size2DSyntax;
import java.util.Hashtable;
import java.util.Random;

public class disjointSet {
    Hashtable<Integer,Double>internalDif=new Hashtable<>();
    Hashtable<Integer,Integer>PARENT=new Hashtable<>();
    Hashtable<Integer,Integer>RANK=new Hashtable<>();
    Hashtable<Integer,Integer>SIZE=new Hashtable<>();

    public disjointSet(Integer[] universe){
        for(Integer x:universe){
            PARENT.put(x,x);
            RANK.put(x,0);
            internalDif.put(x,0.0);
            SIZE.put(x,1);
        }
    }

    public Integer find(Integer item){
        if(PARENT.get(item)==item){
            return item;
        }
        else{
            PARENT.replace(item,find(PARENT.get(item)));
            return find(PARENT.get(item));
        }
    }

    public int getSize(Integer item){
        return SIZE.get(find(item));
    }

    public void union(Integer a, Integer b,double edgeWeight){
        int x=find(a);
        int y=find(b);
        if(x==y){
            return;
        }
        if(RANK.get(x)>RANK.get(y)){
            int childSize=SIZE.get(y);
            PARENT.replace(y,x);
            int curSize=SIZE.get(x);
            curSize=curSize+childSize;
            SIZE.replace(x,curSize);
            setInternalDif(x,edgeWeight);
        }else if(RANK.get(y)> RANK.get(x)){
            int childSize=SIZE.get(x);
            PARENT.replace(x,y);
            int curSize=SIZE.get(y);
            curSize=curSize+childSize;
            SIZE.replace(y,curSize);
            setInternalDif(y,edgeWeight);
        }
        else {
            int childSize=SIZE.get(x);
            PARENT.replace(x,y);
            int curSize=SIZE.get(y);
            curSize=curSize+childSize;
            SIZE.replace(y,curSize);
            int replace=RANK.get(y);
            replace++;
            RANK.replace(y,replace);
            setInternalDif(y,edgeWeight);
        }

    }

    public void setInternalDif(Integer x,double value){
        Integer parent=find(x);
        internalDif.replace(parent,value);
    }

    public double getInternalDif(Integer x){
        Integer parent=find(x);
        return internalDif.get(parent);
    }

}
