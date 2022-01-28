-------------------

Bienvenue au fichier readme.txt de la partie mécatronique

Pour commander l'araignée à distance, suivre la démarche suivante:

1- Ouvrir le fichier serveur.uno avec une ide arduino
2- Remplir les coordonnées du réseau WIFI sur les lignes dédiées

	const char* ssid = "nom du wifi";
	const char* password = "mot de passe";
	
3- Ouvrir le moniteur série
4- Brancher l'arduino à l'ordinateur avec un câble USB mini
5- Upload le code sur l'arduino 
6- Récupérer l'adresse IP du serveur sur le moniteur série 
7- Ouvrir le fichier ClientRobot.java
8- Coller l'adresse IP sur la ligne dédiée 
	private String serverHostname = new String ("adresse IP");
9- Compiler le fichier java 
10- Vous pouvez désormais débrancher l'araignée et la contrôler à distance grâce au clavier midi/votre clavier/l'interface graphique :) 


🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 🕷️ 