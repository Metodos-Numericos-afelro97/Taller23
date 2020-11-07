
package taller23;


public class Taller23 {


    public static void main(String[] args) {
        double[] x1 = {0,0,2,4,0,2,4,4};
		double[] x2 = {0,1,1,2,2,3,3,1};
		double[] y = {1.6,3,1.1,1.2,3.2,3.3,1.7,0.1*};
		double[] x_2 = new double[x1.length];
		double sumax1 = 0, sumax2 = 0, sumay = 0,sumax1c = 0, sumax2c = 0, sumaxx = 0, x1y = 0, x2y = 0;
		double a1 = 0, a0 = 0, a2 = 0, st = 0, sr = 0, sy = 0, sy_x = 0, r = 0;
		int tam = x1.length;

		System.out.println("\n Método de Regresión Polinomial \n");

		for(int i = 0; i < x1.length; i++) {
			sumax1  += x1[i];
			sumax1c += x1[i] * x1[i];
		}
		for(int i = 0; i < x2.length; i++) {
			sumax2 += x2[i];
			sumax2c += x2[i] * x2[i];
			sumaxx += x1[i] * x2[i];
		}
		for(int i = 0; i < y.length; i++) {
			sumay += y[i];
			x1y += x1[i] * y[i];
			x2y += x2[i] * y[i];
		}

		double[][] a = {{tam, sumax1, sumax2}, {sumax1, sumax1c, sumaxx}, {sumax2, sumaxx, sumax2c}};
		double[] b = {sumay, x1y, x2y};
		double[] resp = gaussJordan(a, b);

		for (int j = 0; j < resp.length; j++) {
			System.out.println(" a" + (j) + " = " + resp[j]);
		}
		System.out.println("");

		a0 = resp[0];
		a1 = resp[1];
		a2 = resp[2];

		for(int i = 0; i < y.length; i++) {
			st += Math.pow(y[i] - (sumay/tam), 2);
			sr += Math.pow(y[i] - a0 - a1*(x1[i]) - a2*x2[i], 2);
		}
		//desviación estandar
		sy = Math.pow(st/(tam-1), 0.5);
		//error estandar
		sy_x = Math.pow(sr/(tam-2), 0.5);
		//coeficiente de correlacion
		r = Math.pow((st - sr)/st, 0.5);

		for (int i = 0; i < resp.length; i++) {
			double test = 0;
			for (int j = 0; j < resp.length; j++) {
				test += a[i][j] * resp[j];
			}
			test -= b[i];
			//System.out.println(" Test " + (i + 1) + " = " + test );
		}
		System.out.println(" Sr = " + sr);
		System.out.println(" Sy = " + sy);
		System.out.println(" Sy/x = " + sy_x);
		System.out.println(" r = " + r*100 + "%");
		System.out.println("\n z = " + a0 + " + (" + a1 +("x") + ") + (" + a2 +("y)"));



	}
	private static double[] gaussJordan(double[][] a, double[] b) {

		double[][] aAux = arreglo(a);
		double[] bAux = arreglo(b);

		int n = bAux.length;

		System.out.println("Ecuaciones");
		imp_ecuaciones(aAux, bAux);
		System.out.println();

		for (int i = 0; i < n ; i++) {

			if (aAux[i][i] == 0) {

				int fila = i;
				for (int j = i + 1; j < aAux.length; j++) {

					if ((aAux[j][i]) != 0) {
						fila = j;
						break;
					}
				}

				double t = bAux[i];
				bAux[i] = bAux[fila];
				bAux[fila] = t;

				for (int k = 0; k < aAux.length; k++) {

					t = aAux[i][k];
					aAux[i][k] = aAux[fila][k];
					aAux[fila][k] = t;

				}

				//System.out.println("Pivoteo:");
				//imp_ecuaciones(aAux, bAux);
				//System.out.println("------------------------------------------------");
				//System.out.println("");
			}

			double mayor = (aAux[i][i]);

			bAux[i] = bAux[i] / mayor;
			for (int l = 0; l < aAux.length; l++) {

				aAux[i][l] = aAux[i][l] / mayor;

			}

			//System.out.println("Escalonamiento:");
			//imp_ecuaciones(aAux, bAux);
			//System.out.println("");

			for (int j = 0; j < n; j++) {

				if(i!=j)
				{

					double fact = aAux[j][i] / aAux[i][i];

					for (int k = 0; k < n; k++) {
						aAux[j][k] -= aAux[i][k] * fact;
					}

					bAux[j] -= bAux[i] * fact;

					//System.out.println((j + 1) + " con " + (i + 1));
					//imp_ecuaciones(aAux, bAux);
					//System.out.println();
				}
			}
		}



		return bAux;
	}

	private static double[][] arreglo(double[][] m) {
		double[][] duplicado = new double[m.length][m[0].length];
		for (int i = 0; i < m.length; i++) {
			System.arraycopy(m[i], 0, duplicado[i], 0, m[i].length);
		}

		return duplicado;
	}

	private static double[] arreglo(double[] v) {
		double[] duplicado = new double[v.length];
		System.arraycopy(v, 0, duplicado, 0, v.length);

		return duplicado;
	}

	private static void imp_ecuaciones(double[][] a, double[] b) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "     ");
			}
			System.out.println(" | " +  b[i]);
		}

	}

}