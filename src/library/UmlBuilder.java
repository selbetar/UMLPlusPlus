package library;

import ast.ClassDec;
import ast.Relationship;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UmlBuilder extends JFrame {
    private static UmlBuilder instance = null;
    private mxGraph graph;
    private Object parent;
    private Map<String, mxCell> containerMap;

    private UmlBuilder() {
        graph = new mxGraph() {
            public boolean isCellFoldable(Object cell, boolean collapse) {
                return false;
            }

            public boolean isCellSelectable(Object cell) {
                return !model.isEdge(cell);
            }
        };
        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        containerMap = new HashMap<>();

    }

    public static UmlBuilder getInstance() {
        if (instance == null)
            instance = new UmlBuilder();

        return instance;
    }

    public mxCell drawNode(ClassDec.ClassType type, String name, String fields, String methods) {
        int width = 100;
        int height = 65;
        String titleStyle = "text;html=1;align=center;verticalAlign=top;spacingLeft=4;spacingRight=4;" +
                "overflow=hidden;rotatable=0;";
        String attrStyle = "text;html=1;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;" +
                "whiteSpace=wrap;overflow=hidden;rotatable=0;portConstraint=eastwest;";
        mxGeometry fieldGeo = new mxGeometry(0, 0.3, width, height);
        mxGeometry methodGeo = new mxGeometry(0, 0.75, width, height);
        fieldGeo.setRelative(true);
        methodGeo.setRelative(true);
        mxCell titleNode;


        if (!type.equals(ClassDec.ClassType.CLASS)) {
            titleNode = (mxCell) graph.insertVertex(parent, null, "<<" + type + ">>" + '\n' + name, 0, 0,
                    width, width, titleStyle);
        } else {
            titleNode = (mxCell) graph.insertVertex(parent, null, name, 0, 0, width, width, titleStyle);

        }

        mxCell fieldNode = new mxCell(fields, fieldGeo, attrStyle);
        fieldNode.setVertex(true);
        mxCell methodNode = new mxCell(methods, methodGeo, attrStyle);
        methodNode.setVertex(true);

        graph.addCell(fieldNode, titleNode);
        graph.addCell(methodNode, titleNode);
        containerMap.put(name, titleNode);

        return titleNode;
    }

    public void drawClass(ClassDec.ClassType type, String className) {
        int width = 120;
        int height = 65;
        String titleStyle = "align=center;verticalAlign=top;spacingLeft=4;spacingRight=4;overflow=hidden;";

        String attrStyle = "text;align=left;verticalAlign=top;spacingLeft=4;spacingRight=4;" +
                "whiteSpace=wrap;overflow=hidden;portConstraint=eastwest;";
        mxGeometry fieldGeo = new mxGeometry(0, 0.3, width, height);
        mxGeometry methodGeo = new mxGeometry(0, 0.75, width, height);
        fieldGeo.setRelative(true);
        methodGeo.setRelative(true);
        mxCell titleNode;


        if (!type.equals(ClassDec.ClassType.CLASS)) {
            titleNode = (mxCell) graph.insertVertex(parent, null, "<<" + type + ">>" + '\n' + className, 0, 0,
                    width, width, titleStyle);
        } else {
            titleNode = (mxCell) graph.insertVertex(parent, null, className, 0, 0, width, width, titleStyle);

        }

        mxCell fieldNode = new mxCell(null, fieldGeo, attrStyle);
        fieldNode.setVertex(true);
        mxCell methodNode = new mxCell(null, methodGeo, attrStyle);
        methodNode.setVertex(true);

        graph.addCell(fieldNode, titleNode);
        graph.addCell(methodNode, titleNode);
        containerMap.put(className, titleNode);
    }

    public void addField(String className, String fields) {
        mxCell classCell = containerMap.get(className);
        mxCell fieldCell = (mxCell) classCell.getChildAt(0);
        fieldCell.setValue(fields);
    }

    public void addMethod(String className, String methods) {
        mxCell classCell = containerMap.get(className);
        mxCell methodCell = (mxCell) classCell.getChildAt(1);
        methodCell.setValue(methods);
    }

    public void drawRelation(String fromNode, String toNode, Relationship.relationshipType relation) {
        String implementsStyle = "dashed=true;endArrow=block;endSize=16;endFill=0;fillColor=black";
        String extendsStyle = "endArrow=block;endSize=15;endFill=0;";
        mxCell toCell = containerMap.get(toNode);
        mxCell fromCell = containerMap.get(fromNode);

        if (Relationship.relationshipType.EXTENDS.equals(relation)) {
            graph.insertEdge(parent, null, null, fromCell, toCell, extendsStyle);
        } else {
            graph.insertEdge(parent, null, null, fromCell, toCell, implementsStyle);
        }
    }

    public mxGraph getGraph() {
        return graph;
    }

    public void beginUpdate() {
        graph.getModel().beginUpdate();
    }

    public void endUpdate() {
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.setOrientation(SwingConstants.WEST);
        layout.setMoveParent(false);
        layout.setResizeParent(false);
        layout.setFineTuning(true);
        layout.setInterRankCellSpacing(90);
        layout.setIntraCellSpacing(90);
        layout.execute(parent);

        graph.getModel().endUpdate();

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        graphComponent.doLayout();
        exportPhoto();
    }

    public void exportPhoto() {
       String path = new File("").getAbsolutePath();
        File directory = new File("diagrams");

        if (!directory.exists()){
            directory.mkdir();
        }

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true,
                null);
        try {
            ImageIO.write(image, "PNG", new File(path + "/diagrams/" + new Date().toString() + ".png"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UmlBuilder frame = new UmlBuilder();
        ClassDec.ClassType c = ClassDec.ClassType.CLASS;
        ClassDec.ClassType i = ClassDec.ClassType.INTERFACE;
        ClassDec.ClassType ab = ClassDec.ClassType.ABSTRACT_CLASS;


        /*mxCell v1  = frame.drawNode(ab, "v1", "Hi", "HI()");
        mxCell v2  = frame.drawNode(c, "v2", "Hi", "HI()");
        mxCell v3 = frame.drawNode(c, "v3", "Hi", "HI()");
        mxCell v4 = frame.drawNode(c, "v4", "Hi", "HI()");
        mxCell v5 = frame.drawNode(c, "v5", "Hi", "HI()");
        mxCell v6 = frame.drawNode(c, "v6", "Hi", "HI()");
        mxCell v7 = frame.drawNode(i, "v7", "Hi", "HI()");*/

        frame.drawClass(c, "v1");
        frame.drawClass(c, "v2");
        frame.drawClass(c, "v3");
        frame.drawClass(c, "v4");
        frame.drawClass(c, "v5");
        frame.drawClass(c, "v6");
        frame.drawClass(i, "v7");
        Relationship.relationshipType extendA = Relationship.relationshipType.EXTENDS;
        Relationship.relationshipType implementA = Relationship.relationshipType.IMPLEMENTS;

        frame.drawRelation("v2", "v1", extendA);
        frame.drawRelation("v3", "v1", extendA);
        frame.drawRelation("v7", "v1", extendA);
        frame.drawRelation("v6", "v3", extendA);
        frame.drawRelation("v4", "v2", implementA);
        frame.drawRelation("v5", "v4", implementA);

        frame.addField("v1", "+ test: String");
        frame.addMethod("v5", "- test(): void");

        frame.endUpdate();
     //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //   frame.setSize(400, 320);
      //  frame.setVisible(true);
    }


}
