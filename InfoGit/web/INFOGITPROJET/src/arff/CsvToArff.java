package arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class CsvToArff {

	// Dossier dans lequel se trouvent les fichiers CSV à traiter
	public String DATAPATH = "./elements_projet/";

	// Dossier dans lequel se trouve la liste des stop-words
	public String STOP_LIST = "./stop_list/";

	// Dossier dans lequel se trouve les fichiers ARFF resultants des différents
	// traitements
	public static String DIRECTORY_ARFF = "./fichiers_arff/";

	// Liste de la ponctuation à traiter
	public String[] ponctuation = { ",", ".", "!", "[", "]", "-", "?", "_", "/", "*", "@", "(", ")", ":", "&", ";", "#", "<", ">" };

	// Dictionnaire concernant les smileys sans lettre
	public Hashtable<String, String> smileySansLettre;

	// Dictionnaire concernant les smileys avec lettre
	public Hashtable<String, String> smileyAvecLettre;

	// Liste concernant les stop-words à traiter
	public ArrayList<String> listStop;

	public CsvToArff() {
		listStop = new ArrayList<String>();

		smileySansLettre = new Hashtable();
		smileyAvecLettre = new Hashtable();
		smileySansLettre.put(":)", "happy");
		smileySansLettre.put(":-)", "happy");
		smileySansLettre.put("q:)", "happy");
		smileySansLettre.put(":(", "sad");
		smileySansLettre.put(":-(", "sad");
		smileyAvecLettre.put(":D ", "cheerful");
		smileyAvecLettre.put(":-D", "cheerful");
		smileySansLettre.put(":o)", "happy");
		smileySansLettre.put(";)", "happy");
		smileySansLettre.put(";-)", "happy");
	};

	public String header() {
		return "@RELATION donnees\n@ATTRIBUTE text STRING\n@ATTRIBUTE eval {-1,1}\n@data";
	}

	public void stopList() {
		String label = this.STOP_LIST + "list_stop_words_to_use.txt";

		String resultat = this.header();
		try {
			// Ouverture de label
			InputStream ipsLabel = new FileInputStream(label);
			InputStreamReader ipsrLabel = new InputStreamReader(ipsLabel);
			BufferedReader brLabel = new BufferedReader(ipsrLabel);

			String ligneLabel;
			while ((ligneLabel = brLabel.readLine()) != null) {
				listStop.add(ligneLabel);

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public String traitementStopList(String ligne) {
		for (int i = 0; i < listStop.size(); i++) {
			ligne = ligne.toLowerCase().replaceAll("[ ]+" + listStop.get(i) + "[ ]+", " ");
		}
		return ligne.replaceAll("[ ]+", " ");
	}

	public String echappementQuotes(String ligne) {
		return ligne.replace("\"", "\\\"");

	}

	public String traitementPonctuation(String ligne) {
		for (int i = 0; i < ponctuation.length; i++) {
			ligne = ligne.replace(ponctuation[i], " ");
		}
		return ligne.replaceAll("[ ]+", " ");
	}

	public String traitementSmiley(String ligne) {

		for (Map.Entry<String, String> entry : smileyAvecLettre.entrySet()) {
			ligne = ligne.replace(entry.getKey(), " " + entry.getValue() + " ");
		}

		for (Map.Entry<String, String> entry : smileySansLettre.entrySet()) {
			ligne = ligne.replace(entry.getKey(), " " + entry.getValue() + " ");
		}

		return ligne.replaceAll("[ ]+", " ");
	}

	public String lecture(String rep) {
		String data = this.DATAPATH + "dataset.csv";
		String label = this.DATAPATH + "labels.csv";
		String resultat = this.header();
		try {
			// Ouverture de data
			InputStream ipsData = new FileInputStream(data);
			InputStreamReader ipsrData = new InputStreamReader(ipsData);
			BufferedReader brData = new BufferedReader(ipsrData);

			// Ouverture de label
			InputStream ipsLabel = new FileInputStream(label);
			InputStreamReader ipsrLabel = new InputStreamReader(ipsLabel);
			BufferedReader brLabel = new BufferedReader(ipsrLabel);

			String ligneData;
			String ligneLabel;
			if (rep.equals("p")) {
				while ((ligneData = brData.readLine()) != null && (ligneLabel = brLabel.readLine()) != null) {
					resultat += "\n\"" + echappementQuotes(traitementPonctuation(ligneData)) + "\"," + ligneLabel;

				}
			}
			if (rep.equals("b")) {
				while ((ligneData = brData.readLine()) != null && (ligneLabel = brLabel.readLine()) != null) {
					resultat += "\n\"" + echappementQuotes(ligneData) + "\"," + ligneLabel;

				}
			}
			if (rep.equals("s")) {
				while ((ligneData = brData.readLine()) != null && (ligneLabel = brLabel.readLine()) != null) {
					resultat += "\n\"" + echappementQuotes(traitementPonctuation(traitementSmiley(ligneData))) + "\","
							+ ligneLabel;

				}
			}
			if (rep.equals("t")) {
				this.stopList();// on crée le tableau de stopWord
				while ((ligneData = brData.readLine()) != null && (ligneLabel = brLabel.readLine()) != null) {
					resultat += "\n\""
							+ echappementQuotes(traitementStopList(traitementPonctuation(traitementSmiley(ligneData))))
							+ "\"," + ligneLabel;

				}
			}

			brData.close();
			brLabel.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return resultat;
	}

	public void ecriture(String contenu, String nomFic) {
		try {
			FileWriter fw = new FileWriter(nomFic);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter fichierSortie = new PrintWriter(bw);
			fichierSortie.print(contenu);
			fichierSortie.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		Scanner option = new Scanner(System.in);
		System.out.println(
				"merci d'entrer l'option du fichier arff:\nb = text brut\np = sans ponctuation\ns = sans smiley et sans ponctuation\nt = sans smiley et sans ponctuation et sans stop words.");
		String rep = option.nextLine();
		CsvToArff a = new CsvToArff();

		Wait w = new Wait();
		w.start();

		String contenuArff = a.lecture(rep);
		option.close();

		String nomFichier = "";
		if (rep.equals("b"))
			nomFichier = DIRECTORY_ARFF + "Bruts.arff";
		if (rep.equals("p"))
			nomFichier = DIRECTORY_ARFF + "Ponctuations.arff";
		if (rep.equals("s"))
			nomFichier = DIRECTORY_ARFF + "PonctuationSmiley.arff";
		if (rep.equals("t"))
			nomFichier = DIRECTORY_ARFF + "Traitees.arff";

		a.ecriture(contenuArff, nomFichier);

		w.stop();
		System.out.println("\n\nDone");

	}

}
