import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws Exception {
		/*
		 * Parametri generator de clustere:
		 * N - numarul de puncte generate.
		 * k - numarul de clustere generate.
		 * minRadius - raza minima a unui cluster generat.
		 * maxRadius - raza maxima a unui cluster generat.
		 *
		 * Parametri Kmeans:
		 * k - numarul de clustere ce trebuie identificate.
		 * maxIterations - numarul maxim de iteratii ce se vor executa.
		 *
		 * Sunteti incurajati sa modificati aceste valori pentru a observa
		 * comportarea algoritmului in cat mai multe cazuri.
		 */
		final int N = 1000;
		final int k = 5;
		final double minRadius = 0.0;
		final double maxRadius = 0.2;
		final int maxIterations = 100;

		ArrayList<Point> points = Generator.generate(N, k, minRadius, maxRadius);
		kmeans(points, k, maxIterations);
	}

	/**
	 * Atribuie fiecarui punct din setul primit indexul clusterului caruia apartine.
	 *
	 * @param points
	 *            setul de puncte ce trebuie clusterizat
	 * @param k
	 *            numarul de clustere
	 * @param iterations
	 *            numarul de iteratii dupa care algoritmul se opreste
	 */
	public static void kmeans(ArrayList<Point> points, int k, int iterations) {
		/* Initializam punctele cu UNKNOWN_CLUSTER. */
		for (Point point : points) {
			point.clusterIndex = Point.UNKNOWN_CLUSTER;
		}

		/* Construim un obiect Painter pentru a reprezenta vizual procesul. */
		Painter picasso = new Painter(points);

		/* Initializam centroizii. */
		ArrayList<Point> centroids = randomCentroids(points, k);

		/*
		 * TODO Algoritmul Kmeans:
		 * 1. Atribuiti fiecarui punct indexul celui mai apropiat centroid.
		 * 2. Recalculati pozitiile centroizilor. Noua pozitie a unui centroid
		 *    este centrul de masa al punctelor ce au fost marcate cu indexul lui
		 *    la pasul anterior. Pasii 1 si 2 se vor repeta de un numar finit de
		 *    ori (variablia iterations).
		 */

		while (true) {
			
			for (Point punct : points) {
				
				double distanta_punct_centroid;
				double distanta_minima = Integer.MAX_VALUE;
				int centroid_selectat = 0;
				
				for (int i = 0; i < centroids.size(); i++) {
					
					distanta_punct_centroid = punct.distanceTo(centroids.get(i));
					
					if (distanta_minima > distanta_punct_centroid) {
						
						distanta_minima = distanta_punct_centroid;
						centroid_selectat = i;
						
					}
					
				}
				
				punct.clusterIndex = centroid_selectat;
				
			}
			
			ArrayList<Double> x = new ArrayList<Double>();
			ArrayList<Double> y = new ArrayList<Double>();
			ArrayList<Integer> nr = new ArrayList<Integer>();
			
			for (int i = 0; i < centroids.size(); i++) {
				
				x.add(0.0);
				y.add(0.0);
				nr.add(0);
				
			}
			
			for (Point punct : points) {
				
				int centroid = punct.clusterIndex;
				double suma = x.get(centroid);
				x.set(centroid, suma + centroids.get(centroid).x);
				suma = y.get(centroid);
				y.set(centroid, suma + centroids.get(centroid).y);
				int nr_vechi = nr.get(centroid);
				nr.set(centroid, nr_vechi + 1);
				
			}
			
			@SuppressWarnings("unchecked")
			ArrayList<Point> new_centroids = (ArrayList<Point>) centroids.clone();
			
			for (int i = 0; i < centroids.size(); i++) {
				
				double mx = x.get(i) / nr.get(i);
				double my = y.get(i) / nr.get(i);
				
				new_centroids.get(i).x = mx;
				new_centroids.get(i).y = my;
				
			}
			
			boolean identice = true;
			
			for (int i = 0; i < centroids.size(); i++)
				if (centroids.get(i).clusterIndex != new_centroids.get(i).clusterIndex) {
					identice = false;
					break;
				}
			
			if (identice)
				break;
			else
				centroids = new_centroids;
			
		}
		
		for (int i = 0; i < iterations; i++) {

			

			picasso.setPoints(points);
			picasso.setCentroids(centroids);

			/* Sleep 1s to give us a chance to take a look at the image. */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initializeaza aleator cei k centroizi in patratul [0, 1] x [0, 1].
	 *
	 * @param points
	 *            setul de date ce trebuie clusterizat (nu este folosit in aceasta functie)
	 * @param k
	 *            numarul de clustere
	 * @return
	 */
	static ArrayList<Point> randomCentroids(ArrayList<Point> points, int k) {
		ArrayList<Point> centroids = new ArrayList<Point>();

		/*
		 * TODO Construiti aleator cei k centroizi.
		 * Este indicat sa folositi campul clusterIndex pentru a retine
		 * indexul fiecarui cluster.
		 */

		for (int i = 0; i < k; i++)
			centroids.add(points.get(i));

		return centroids;
	}

	/**
	 * Initializeaza cei k centroizi dupa metoda specifica Kmeans++.
	 *
	 * @param points
	 *            setul de date ce trebuie clusterizat
	 * @param k
	 *            numarul de clustere
	 * @return
	 */
	static ArrayList<Point> kmeansppCentroids(ArrayList<Point> points, int k) {
		/*
		 * TODO Implementati algoritmul de initializare a centroizilor specific Kmeans++.
		 * http://en.wikipedia.org/wiki/K-means%2B%2B#Initialization_algorithm
		 */

		return randomCentroids(points, k);
	}
}

