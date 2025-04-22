# SortAnalyse

## Description
SortAnalyse est un outil d'analyse et de visualisation d'algorithmes de tri. Il permet d'observer et de comparer les performances de différents algorithmes de tri à travers une interface graphique intuitive.

## Fonctionnalités
- Interface graphique Swing pour une utilisation desktop
- Interface web pour un accès via navigateur
- Visualisation en temps réel des algorithmes de tri
- Comparaison des performances entre différents algorithmes
- Paramètres configurables pour les tests

## Prérequis
- Java JDK 8 ou supérieur
- Apache Ant (pour la compilation)

## Installation

1. Clonez le repository :
```bash
git clone [url-du-repository]
```

2. Compilez le projet avec Ant :
```bash
ant compile
```

3. Générez le JAR :
```bash
ant jar
```

## Utilisation

### Version Desktop (Swing)
Lancez l'application avec :
```bash
java -jar dist/SortAnalyse.jar
```

### Version Web
Déployez l'application web sur votre serveur d'application préféré.

## Structure du Projet
```
.
├── src/
│   ├── main/
│   │   ├── swing/     # Interface graphique desktop
│   │   ├── web/       # Interface web
│   │   └── setting/   # Configuration
│   └── test/          # Tests unitaires
├── doc/               # Documentation
├── dist/             # Fichiers compilés
└── build.xml         # Script de build Ant
```

## Tests
Pour exécuter les tests :
```bash
ant test
```

## Documentation
La documentation détaillée est disponible dans le dossier `doc/`.

## Contribution
Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.


