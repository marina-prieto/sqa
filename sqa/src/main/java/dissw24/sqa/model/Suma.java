package dissw24.sqa.model;

import java.util.List;

public class Suma {

	private List<Sumando> sumandos;
	
	public Suma() {
		
	}

	public void add(Sumando... sumandos) {
		for (Sumando s : sumandos) {
			this.sumandos.add(s);
		}
	}
}
