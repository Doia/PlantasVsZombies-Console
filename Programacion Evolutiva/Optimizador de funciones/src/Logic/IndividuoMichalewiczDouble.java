package Logic;

public class IndividuoMichalewiczDouble extends IndividuoDouble {

		final static int NUMVAR = 0;
		final static String ID = "Funcion Michalewicz Double";
		final static double[] INTERVALOS = {0, Math.PI};
		
		public IndividuoMichalewiczDouble(double precision, int n) {
			super(ID,INTERVALOS, n, precision);
			
			double[] inter = new double[n*2];
			for (int i = 0; i < n*2; i +=2) {
				inter[i] = INTERVALOS[0];
				inter[i+1] = INTERVALOS[1];
			}	
			this.intervalo = inter;
			setSize(precision);
			genoma = new Double[size];
		}

		public IndividuoMichalewiczDouble() {
			super(ID, INTERVALOS, NUMVAR);
		}

		@Override
		public Individuo duplicate() {
			Individuo clon;
			
			clon = new IndividuoMichalewiczDouble(this.precision, this.numVar);
			clon.setGenoma(genoma);
			
			return clon;
			
		}

		@Override
		public double getFitness() {
			double[] var = this.getFenotipo();
			double pi = round (Math.PI);
			double res=0;

			for(int i=0;i<this.numVar;i++)
			{
				res=res + Math.sin(var[i])*Math.pow(Math.sin(((i+1)*Math.pow(var[i], 2))/pi), 20);
			}
			return round(-1.0 * res);
		}

		@Override
		public double getFitnessNormalizado() {
			// TODO Auto-generated method stub
			return -1.0*getFitness();
		}

		@Override
		public Individuo newInstance(double precision, int n) {
			// TODO Auto-generated method stub
			return new IndividuoMichalewiczDouble(precision, n);
		}

}
