package client.view.drawing;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingInfo implements Cloneable{

    Point startPoint;
    Point endPoint;
    List<Point> pointList;

    public NwbDrawingInfo(){
        pointList = new ArrayList<Point>();
    }
    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void clearInfo(){
        startPoint = null;
        endPoint = null;
        pointList.clear();
    }

    public NwbDrawingInfo getClone(){
        NwbDrawingInfo info = null;
        try {
            info = (NwbDrawingInfo) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return info;
    }
}
