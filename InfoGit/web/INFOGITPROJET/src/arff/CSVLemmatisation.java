package arff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Création d'un nouveau fichier CSV (traiter avec la ponctuation et les
 * smileys) pour lemmatisation via le script bash
 **/

public class CSVLemmatisation {

	public static String DATA_PATH_FILE = "./elements_projet/";
	public static String FILE_DEPART = "dataset.csv";
	public static String FILE_RESULT_SMILEYS_PONCTUATION = "dataset_for_lemmatisation_smileys_ponctuation.csv";
	public static String FILE_RESULT_SMILEYS_PONCTUATION_STOPWORDS = "dataset_for_lemmatisation_smileys_ponctuation_stopwords.csv";
	public static String FILE_LABELS = "labels.csv";

	public static String DATA_PATH_LEMMATISEES_SMILEYS_PONCTUATION = "./donnees_lemmatisees/ponctuation_smileys/";
	public static String DATA_PATH_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS = "./donnees_lemmatisees/ponctuation_smileys_stopwords/";

	// filtre servant à retirer les lemmes "parasites"
	public ArrayList<String> filtre;

	// filtre servant à garder les catégories qui nous intéressent
	public ArrayList<String> filtreCategorie;

	public static String DIRECTORY_ARFF = "./fichiers_arff/";
	public static String FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION = "LemmatiseesSmileyPonctuation.arff";
	public static String FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS = "LemmatiseesSmileyPonctuationStopwords.arff";
	public static String FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_FC = "LemmatiseesSmileyPonctuationFC.arff";
	public static String FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS_FC = "LemmatiseesSmileyPonctuationStopwordsFC.arff";

	public CSVLemmatisation() {
		this.filtre = new ArrayList<String>();

		// ajout des lemmes parasites
		this.filtre.add("@card@");
		this.filtre.add("<unknown>");
		this.filtre.add("'");
		this.filtre.add("\"");

		this.filtreCategorie = new ArrayList<String>();
		// ajout des catégories (ici Adjectifs, Adverbes et Verbes)
		// source : http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/Penn-Treebank-Tagset.pdf

		// catégorie adjectif
		this.filtreCategorie.add("JJ");
		this.filtreCategorie.add("JJR");
		this.filtreCategorie.add("JJS");

		// catégorie adverbes
		this.filtreCategorie.add("RB");
		this.filtreCategorie.add("RBR");
		this.filtreCategorie.add("RBS");
		this.filtreCategorie.add("RP");
		this.filtreCategorie.add("WRB");

		// catégorie verbes
		this.filtreCategorie.add("VB");
		this.filtreCategorie.add("VBD");
		this.filtreCategorie.add("VBG");
		this.filtreCategorie.add("VBN");
		this.filtreCategorie.add("VBP");
		this.filtreCategorie.add("VBZ");

		// catégorie noms
		// this.filtreCategorie.add("NN");
		// this.filtreCategorie.add("NNS");

	}

	/**
	 * Permet de lire le fichier CSV contenant les données afin d'y traiter la
	 * ponctuation et les smileys
	 * 
	 * @param type
	 *            de traitement choisi (Smiley + ponctuation avec/sans
	 *            stopwords)
	 * @return contenu où l'on a retiré la ponctuation et interprété les smileys
	 */
	public String lectureEtTraitement(String traitement) {
		CsvToArff csvToArff = new CsvToArff();

		String content = null;
		try {
			content = new String(Files.readAllBytes(new File(DATA_PATH_FILE + FILE_DEPART).toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		if (traitement.equals("1")) {
			return csvToArff.traitementPonctuation(csvToArff.traitementSmiley(content));
		} else {
			csvToArff.stopList();
			return csvToArff.traitementStopList(csvToArff.traitementPonctuation(csvToArff.traitementSmiley(content)));
		}
	}

	/**
	 * Permet d'écrire le contenu dans un fichier CSV qui sera traité par le
	 * script bash de lemmatisation
	 * 
	 * @param contenu
	 *            : contenu où l'on a retiré la ponctuation et interprété les
	 *            smileys
	 * @param type
	 *            du traitement choisi
	 */
	public void ecriture(String contenu, String traitement) {
		try {
			FileWriter fw = null;

			if (traitement.equals("1")) {
				fw = new FileWriter(DATA_PATH_FILE + FILE_RESULT_SMILEYS_PONCTUATION);
			} else {
				fw = new FileWriter(DATA_PATH_FILE + FILE_RESULT_SMILEYS_PONCTUATION_STOPWORDS);
			}

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter fichierSortie = new PrintWriter(bw);
			fichierSortie.print(contenu);
			fichierSortie.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Permet de construire une chaine contenant les lemmes issus du contenu du
	 * fichier traité par lemmatisation
	 * 
	 * @param f
	 *            : fichier lemmatisé
	 * @param filtrageCategories
	 *            : option pour filtrer les catégories
	 * @return chaine lemmatisée
	 */
	public String lemmatisationChaine(File f, String filtrageCategories) {

		String chaineLemmatisee = null;
		try {
			String content = new String(Files.readAllBytes(f.toPath()));

			String[] dataSplit = content.split("\n");

			chaineLemmatisee = "";

			String termeLemmatise = "";
			String categorie = "";
			boolean termeValide = false;

			for (int i = 0; i < dataSplit.length; i++) {

				termeLemmatise = dataSplit[i].split("\t")[2];

				termeValide = false;

				if (filtrageCategories.equals("o")) {
					categorie = dataSplit[i].split("\t")[1];
					termeValide = (!this.filtre.contains(termeLemmatise)) && (this.filtreCategorie.contains(categorie));
				} else {
					termeValide = !this.filtre.contains(termeLemmatise);
				}

				if (termeValide) {
					if (chaineLemmatisee.isEmpty()) {
						chaineLemmatisee = termeLemmatise;
					} else {
						chaineLemmatisee = chaineLemmatisee + " " + termeLemmatise;
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// CsvToArff csvToArff = new CsvToArff();
		// csvToArff.stopList();

		// retour de la chaine lemmatisée avec traitement du cas " n't "
		return chaineLemmatisee.replace("n't", "not");
	}

	/**
	 * Permet de créer le contenu du fichier ARFF à partir des différents
	 * fichiers représentants les données traitées par lémmatisation
	 * 
	 * @param traitement
	 *            : type du traitement
	 * @param filtrageCategorie
	 *            : option pour filtrer les catégories
	 * 
	 * @return
	 * @throws IOException
	 */
	public String creationContenuArffLemmatisation(String traitement, String filtrageCategorie) throws IOException {
		CsvToArff csvToArff = new CsvToArff();

		String contentFileArff = csvToArff.header();

		File directoryDataLemmatisees = null;

		if (traitement.equals("1")) {
			directoryDataLemmatisees = new File(DATA_PATH_LEMMATISEES_SMILEYS_PONCTUATION);
		} else {
			directoryDataLemmatisees = new File(DATA_PATH_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS);
		}

		File[] filesLemmatisees = directoryDataLemmatisees.listFiles();

		filesLemmatisees = this.sortByNumber(filesLemmatisees);

		String contentLabels = new String(Files.readAllBytes(new File(DATA_PATH_FILE + FILE_LABELS).toPath()));
		String[] labelsArray = contentLabels.split("\n");

		String chaineLemmatisé = "";
		for (int i = 0; i < filesLemmatisees.length; i++) {
			chaineLemmatisé = this.lemmatisationChaine(filesLemmatisees[i], filtrageCategorie);

			contentFileArff += "\n\"" + chaineLemmatisé + "\"," + labelsArray[i];
		}

		return contentFileArff;
	}

	/**
	 * Permet la création du fichier ARFF contenant les données lemmatisées à
	 * partir du contenu généré par la méthode
	 * creationContenuArffLemmatisation()
	 * 
	 * @param traitement
	 *            : type du traitement
	 * @param filtrageCategorie
	 *            : option pour filtrer les catégories
	 */
	public void creationFichierArffLemmatisation(String traitement, String filtrageCategorie) {
		try {
			FileWriter fw = null;
			if (traitement.equals("1")) {
				if (filtrageCategorie.equals("o")) {
					fw = new FileWriter(DIRECTORY_ARFF + FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_FC);
				} else {
					fw = new FileWriter(DIRECTORY_ARFF + FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION);
				}

			} else {
				if (filtrageCategorie.equals("o")) {
					fw = new FileWriter(DIRECTORY_ARFF + FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS_FC);
				} else {
					fw = new FileWriter(DIRECTORY_ARFF + FILE_ARFF_LEMMATISEES_SMILEYS_PONCTUATION_STOPWORDS);
				}
			}

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter fichierSortie = new PrintWriter(bw);
			String contenu = this.creationContenuArffLemmatisation(traitement, filtrageCategorie);
			fichierSortie.print(contenu);
			fichierSortie.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Permet de trier la liste des fichiers par leurs noms (ici des nombres)
	 * 
	 * @param files
	 * @return liste des fichiers trier par ordre croissant de leurs noms
	 */
	public File[] sortByNumber(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumber(o1.getName());
				int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}

			private int extractNumber(String name) {
				int i = 0;
				String number = name.split(".txt")[0];
				i = Integer.parseInt(number);

				return i;
			}
		});

		return files;
	}

	/**
	 * METHODE MAIN pour tester
	 * 
	 * @param argv
	 */
	public static void main(String argv[]) {

		CSVLemmatisation csvLemmatisation = new CSVLemmatisation();

		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Choix du traitement pour la lemmatisation:\n - c = création du csv pour la lemmatisation \n - a = création du fichier ARFF contenant les données lemmatisées via le csv");

		String rep = sc.nextLine();

		System.out.println(
				"Choix du traitement sur les données:\n - 1 = Ponctuation + Smileys \n - 2 = Ponctuation + Smileys + StopWords");

		String traitementChoisi = sc.nextLine();

		System.out.println("Début du traitement");

		if (rep.equals("c")) {
			String contenu = csvLemmatisation.lectureEtTraitement(traitementChoisi);
			csvLemmatisation.ecriture(contenu, traitementChoisi);
		} else if (rep.equals("a")) {

			System.out.println("Filtrer les catégories (o/n) ?");

			String filtrageCategories = sc.nextLine();
			csvLemmatisation.creationFichierArffLemmatisation(traitementChoisi, filtrageCategories);

		} else {
			System.out.println("Erreur saisie");
		}

		System.out.println("Fin du traitement");

		sc.close();
	}

}
