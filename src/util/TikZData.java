package util;

import java.util.HashMap;
import java.util.List;
import javafx.scene.text.Text;
import model.Edge;
import model.Vertex;

public class TikZData {
	private static StringBuilder sb = new StringBuilder();

	public static StringBuilder handlerData(List<Vertex> vertices, List<Edge> edges, HashMap<Edge, Vertex> invisibleEdges) {

		sb.append("\\documentclass{article} \n");
		sb.append("\\usepackage[utf8]{inputenc} \n");
		sb.append("\\usepackage{tikz} \n");
		sb.append("\\usetikzlibrary{positioning} \n");
		sb.append("\\begin{document} \n");
		sb.append("\\begin{figure} \n");
		sb.append("\\begin{tikzpicture} \n");
		// START THE CODE FOR EXPORT GRAPH
		sb.append(
				"[bigNode/.style={circle, draw=green!60, fill=green!5, thick, minimum size=7mm}, smallNode/.style={circle, draw=blue!60, fill=blue!20, thick, minimum size=3mm},] \n");

		System.out.println("Size of vertices: " + vertices.size());
		for (Vertex v : vertices) {
			if (v.isIntermediatePoint()) {
				smallNode(v.getIndex(), v.getX(), v.getY());
			} else {
				bigNode(v.getIndex(), v.getX(), v.getY());
			}
		}

		System.out.println("Size of edges: " + edges.size());
		for (Edge e : edges) {
			if (e.getCircle() == null && e.getCurve() == null) {// draw the normal edge & segment edge
				if (e.getV2().isIntermediatePoint() || e.getV2().isIntermediatePoint()) {//draw the segment edge
					if(invisibleEdges.get(e) == null) {
						if(!e.getV2().isIntermediatePoint()) {
							drawNormalEdge(e.getDirection(), e.getV1().getIndex(), e.getV2().getIndex(), e.getTextWeight());
						} else {
							drawSegmentEdge(e.getDirection(), e.getV1().getIndex(), e.getV2().getIndex(), e.getTextWeight());
						}
					}
				} else {// draw normal edge and only invisible father edge that have intermediate points
					if(invisibleEdges.get(e) == null) {
						drawNormalEdge(e.getDirection(), e.getV1().getIndex(), e.getV2().getIndex(), e.getTextWeight());
					}
				}
			} else if (e.getCurve() == null) {// draw the circle (loop edge)
				drawLoopEdge(e.getDirection(), e.getV1().getIndex(), e.getTextWeight());
			} else { // draw the curve edge
				drawCurveEdge(e.getDirection(), e.getV1().getIndex(), e.getV2().getIndex(), e.getTextWeight());
			}
		}

		// END THE CODE
		sb.append("\\end{tikzpicture} \n");
		sb.append("\\end{figure} \n");
		sb.append("\\end{document} \n");
		return sb;

	}

	private static void bigNode(int index, double x, double y) {
		x = x/80;
		y = 10 - y/80;
		sb.append("		\\node[bigNode] (" + index + ") at ( " + x + ", " + y + ")" + " {" + index + "}; \n");
	}

	private static void smallNode(int index, double x, double y) {
		x = x/80;
		y = 10 - y/80;
		sb.append("		\\node[smallNode] (" + index + ") at ( " + x + ", " + y + ")" + " {}; \n");
	}

	// \draw[->] (e) to [loop above] node [midway,fill=red!20] {5} (e);
	private static void drawLoopEdge(boolean directed, int indexV1, Text textWeight) {
		if (directed) {
			sb.append("		\\draw [->] (" + indexV1 + ") to [loop above] node[midway,fill=red!20] {"
					+ textWeight.getText() + "} (" + indexV1 + "); \n");
		} else {
			sb.append("		\\draw [-] (" + indexV1 + ") to [loop above] node[midway,fill=red!20] {"
					+ textWeight.getText() + "} (" + indexV1 + "); \n");	
		}
	}

	// \draw[->] (g) to [bend left] node [midway,fill=red!20] {8} (h);
	private static void drawCurveEdge(boolean directed, int indexV1, int indexV2, Text textWeight) {
		if (directed) {
			sb.append("		\\draw [->] (" + indexV1 + ") to [bend left] node[midway,fill=red!20] {"
					+ textWeight.getText() + "} (" + indexV2 + "); \n");
		} else {
			sb.append("		\\draw [-] (" + indexV1 + ") to [bend left] node[midway,fill=red!20] {"
					+ textWeight.getText() + "} (" + indexV2 + "); \n");	
		}
	}

	private static void drawNormalEdge(boolean directed, int indexV1, int indexV2, Text textWeight) {
		if (directed) {
			sb.append("		\\draw [->] (" + indexV1 + ") -- (" + indexV2 + ") node[midway,fill=red!20] {"
					+ textWeight.getText() + "}; \n");
		} else {
			sb.append("		\\draw [-] (" + indexV1 + ") -- (" + indexV2 + ") node[midway,fill=red!20] {"
					+ textWeight.getText() + "}; \n");
		}
	}

	private static void drawSegmentEdge(boolean directed, int indexV1, int indexV2, Text textWeight) {
		if (directed) {
			sb.append("		\\draw [->] (" + indexV1 + ") -- (" + indexV2 + ") node[fill=red!20] {}; \n");
		} else {
			sb.append("		\\draw [-] (" + indexV1 + ") -- (" + indexV2 + ") node[fill=red!20] {}; \n");
		}
	}
}
