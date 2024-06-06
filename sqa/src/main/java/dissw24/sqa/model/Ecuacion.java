package dissw24.sqa.model;

import java.util.ArrayList;
import java.util.List;

public class Ecuacion {

	private List<Sumando> sumandos;
	//private int lambda;

	public Ecuacion(String eq) {
		this.sumandos = new ArrayList<>();
		String[] tokens = eq.split("x");

		int i=0;
		String tokenIzdo = tokens[i];
		String tokenDcho;

		while (i<tokens.length) {
			i++;
			if (i<tokens.length) {
				tokenDcho = tokens[i];
				int posSigno = tokenDcho.indexOf('-');
				if (posSigno==-1)
					posSigno = tokenDcho.indexOf('+');
				int index;
				if (posSigno==-1)
					index = Integer.parseInt(tokenDcho);
				else
					index = Integer.parseInt(tokenDcho.substring(0, posSigno));
				Sumando sumando = new Simple();
				sumando.setFactor(Integer.parseInt(tokenIzdo));
				sumando.setIndex(index);
				this.add(sumando);
				if (posSigno!=-1)
					tokenIzdo = tokenDcho.substring(posSigno);
			}
		}
	}

	public void add(Sumando sumando) {
		this.sumandos.add(sumando);
	}

	public int calcular(List<Integer> x) {
		int result = 0;
		for (int i=0; i<this.sumandos.size(); i++)
			result = result + this.sumandos.get(i).calcular(x.get(i));
		return result;
	}

	//public void setLambda(int lambda) {
	//	this.lambda = lambda;	
	//}

}

