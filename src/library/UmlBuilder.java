package library;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.ArrayList;
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

    public static UmlBuilder getInstance()
    {
        if (instance == null)
            instance = new UmlBuilder();

        return instance;
    }

    public mxCell drawNode(String type, String name, String fields, String methods) {
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


        if (type != "class") {
             titleNode = (mxCell) graph.insertVertex(parent, null, "<<"+type+">>"+'\n'+name, 0, 0,
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
        ArrayList<mxCell> children = new ArrayList<>();
        children.add(fieldNode);
        children.add(methodNode);
        containerMap.put(name, titleNode);

        return titleNode;
    }

    public void drawRelation(String fromNode, String toNode, String relation) {
        String implementsStyle = "dashed=true;endArrow=block;endSize=16;endFill=0;html=1;";
        String extendsStyle    = "endArrow=block;endSize=16;endFill=0;html=1;";
        mxCell toCell = containerMap.get(toNode);
        mxCell fromCell = containerMap.get(fromNode);

        if (relation == "extends") {
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
    }

    public void exportPhoto() {
        // todo
    }

    public static void main(String[] args) {
        UmlBuilder frame = new UmlBuilder();
        mxCell v1  = frame.drawNode("intreface", "v1", "Hi", "HI()");
        mxCell v2  = frame.drawNode("class", "v2", "Hi", "HI()");
        mxCell v3 = frame.drawNode("class", "v3", "Hi", "HI()");
        mxCell v4 = frame.drawNode("class", "v4", "Hi", "HI()");
        mxCell v5 = frame.drawNode("class", "v5", "Hi", "HI()");
        mxCell v6 = frame.drawNode("class", "v6", "Hi", "HI()");
        mxCell v7 = frame.drawNode("class", "v7", "Hi", "HI()");

        frame.drawRelation("v2", "v1", "extends");
        frame.drawRelation("v3", "v1", "extends");
        frame.drawRelation("v7", "v1", "implements");
        frame.drawRelation("v6", "v3", "extends");
        frame.drawRelation("v4", "v2", "implements");
        frame.drawRelation("v5", "v4", "implements");

        frame.endUpdate();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }


}
