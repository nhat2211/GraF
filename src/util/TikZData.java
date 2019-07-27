package util;

import java.util.List;

import Model.Edge;
import Model.Vertex;

public class TikZData {

	public static StringBuilder handlerData(List<Vertex> vertices, List<Edge> edges) {

		StringBuilder sb = new StringBuilder();
		sb.append("\\documentclass{article} \n");
		sb.append("\\usepackage[utf8]{inputenc} \n");
		sb.append("\\usepackage{tikz} \n");
		sb.append("\\begin{document} \n");
		sb.append("\\begin{tikzpicture} \n");
		//START THE CODE FOR EXPORT GRAPH
		sb.append("\\draw (0,0) -- (4,0) -- (4,4) -- (0,4) -- (0,0); \n");//test graph
		
		
		
		
		
		//END THE CODE
		sb.append("\\end{tikzpicture} \n");
		sb.append("\\end{document} \n");
		return sb;

	}

}
