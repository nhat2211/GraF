package util;

import java.util.List;

import Model.Edge;
import Model.Vertex;

public class TikZData {

	public static StringBuilder handlerData(List<Vertex> vertices, List<Edge> edges) {

		StringBuilder sb = new StringBuilder();
		sb.append("\\documentclass{article} \n");
		sb.append("\\usepackage{tikz} \n");
		sb.append("\\begin{document} \n");

		// Mr Son will code here for export graph

		return sb;

	}

}
