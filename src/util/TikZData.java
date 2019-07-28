package util;

import java.util.List;

import javafx.scene.text.Text;
import model.Edge;
import model.Vertex;

public class TikZData {
	private static StringBuilder sb = new StringBuilder();

	public static StringBuilder handlerData(List<Vertex> vertices, List<Edge> edges) {

		sb.append("\\documentclass{article} \n");
		sb.append("\\usepackage[utf8]{inputenc} \n");
		sb.append("\\usepackage{tikz} \n");
		sb.append("\\usetikzlibrary{positioning} \n");
		sb.append("\\begin{document} \n");
		sb.append("\\begin{figure} \n");
		sb.append("\\begin{tikzpicture} \n");
		//START THE CODE FOR EXPORT GRAPH
		sb.append("[bigNode/.style={circle, draw=green!60, fill=green!5, thick, minimum size=7mm}, smallNode/.style={circle, draw=blue!60, fill=blue!20, thick, minimum size=3mm},] \n");
		sb.append("\\draw (0,0) -- (4,0) -- (4,4) -- (0,4) -- (0,0); \n");//test graph
		
		System.out.println("Size of vertices: " + vertices.size());
		System.out.println("Size of edges: " + edges.size());
		for (Vertex v : vertices) {
			if(v.isIntermediatePoint()) {
				smallNode(v.getIndex(),v.getX(),v.getY());
			} else {
				bigNode(v.getIndex(), v.getX() , v.getY());
			}
		}
		
		//END THE CODE
		sb.append("\\end{tikzpicture} \n");
		sb.append("\\end{figure} \n");
		sb.append("\\end{document} \n");
		return sb;

	}
	
	// \node[bigNode] (e) at ( 1, 2) {e};
	private static void bigNode(int index, double x, double y) {
		x = x/100;
		y = 10 - y/100;
		sb.append("		\\node[bigNode] (" + index + ") at ( " + x + ", " + y + ")" + " {" + index + "}; \n");
	}
	
	// \node[smallNode] (f) at (-1, 2) {};
	private static void smallNode(int index, double x, double y) {
		x = x/100;
		y = 10 - y/100;
		sb.append("		\\node[smallNode] (" + index + ") at ( " + x + ", " + y + ")" + " {" + index + "}; \n");
	}
	
	//  \draw[->] (e) to [loop above] node [midway,fill=red!20] {5} (e);
	private static void drawLoopEdge(boolean directed, Text textWeight, double x, double y) {
		
	}
	
	//   \draw[->] (g) to [bend left] node [midway,fill=red!20] {8} (h);
	private static void drawCurveEdge(boolean directed, Text textWeight, double x1, double y1, double x2, double y2) {
		
	}
	
	// \draw [->] (c) -- (d) node[midway,fill=green!20] {9};
	private static void drawNormalEdge(boolean directed, Text textWeight, double x1, double y1, double x2, double y2) {
		
	}
}
