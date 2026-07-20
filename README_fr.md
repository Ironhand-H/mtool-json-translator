# AI Localization Toolkit

🌍 Langue :
[English](README.md) | [简体中文](README_zh.md) | **Français**

Un outil de localisation de jeux développé en Java et alimenté par des modèles de langage locaux (LLM).

Ce projet traduit les fichiers de ressources d'un jeu grâce à une API compatible OpenAI, fournie par LM Studio ou par un modèle en ligne. Il est conçu pour faciliter la localisation assistée par IA avec un traitement automatique.

> **État actuel :** une première version est disponible. Cette version peut être utilisée même sur un ordinateur sans JDK installé.

---

## Fonctionnalités

### ✔ Modes de traduction

Cet outil propose **3 modes de traduction**, qui utilisent différents formats JSON envoyés à l'API d'IA. L'objectif est de réduire le nombre de tokens tout en gardant une bonne stabilité.

Le programme change automatiquement de stratégie lorsqu'une traduction échoue.

Il commence par le mode **Fast**. Si la traduction échoue, il passe au mode **Safe**. Si cela échoue encore, il utilise le mode **Strict**.

Le mode Strict envoie les phrases une par une et peut réessayer jusqu'à **3 fois** si le modèle produit une mauvaise réponse.

```
Fast -> Safe -> Strict
```

- **Fast**
  - Traduction par lots
  - Vitesse maximale

```
Exemple du mode Fast :
        ["こんにちは","EV001","flower.png","100"]
```

- **Safe**
  - Traduction basée sur la structure JSON
  - Conservation de la structure des objets

```
Exemple du mode Safe :
        [
          {
            "id":0,
            "text":"こんにちは"
          },
          {
            "id":1,
            "text":"EV001"
          }
        ]
```

- **Strict**
  - Traduction phrase par phrase
  - Fiabilité maximale

```
Exemple du mode Strict :
        "こんにちは"
```

### Utilisation

#### Prérequis

Vous avez seulement besoin de :

- Un fichier JSON à traduire
- Une API compatible OpenAI disponible

#### Comment utiliser

- Choisissez d'abord le fichier à traduire, puis cliquez sur **Create Project**.

  Un dossier **AI Localization Toolkit** sera créé dans le dossier **Documents**.

  Le fichier JSON sera découpé et enregistré dans **AI Localization Toolkit/tasks**.

  <img width="957" height="467" alt="image" src="https://github.com/user-attachments/assets/a0ba69fc-a923-4ce9-8223-54a714799f9a" />

- Cliquez ensuite sur **Translate** pour lancer la traduction.

  <img width="876" height="432" alt="image" src="https://github.com/user-attachments/assets/36274cc5-b5af-4788-8661-7732bf68fc07" />

- Si un projet existe déjà, cliquez sur **Continue** sur la page d'accueil puis sur **Translate** pour reprendre la traduction.

### ✔ Traitement JSON

- Lecture des fichiers JSON
- Extraction du texte à traduire
- Conservation de la structure JSON
- Génération automatique du JSON traduit

### ✔ Gestion des erreurs

- Détection du délai d'attente HTTP (5 minutes)
- Nouvelle tentative automatique (3 fois maximum)
- Changement automatique du mode de traduction
- Validation du JSON

---

## Architecture actuelle

```
Fichier JSON
      │
      ▼
 Analyseur JSON
      │
      ▼
 Pipeline de traduction
      │
      ▼
 Service de traduction
      │
      ▼
 Client LM Studio
      │
      ▼
 LLM local
      │
      ▼
 Analyseur du résultat
      │
      ▼
 JSON traduit
```

---

## Technologies

- Java
- Spring Boot
- RestClient
- Jackson
- Maven
- Electron
- React

IA utilisée pour les tests

- LM Studio
- API compatible OpenAI (chat/completions)
- Modèle : qwen/qwen3-14b

Outils de développement

- IntelliJ IDEA
- Postman
- VS Code

## Format actuellement pris en charge

- ✔ Fichiers JSON générés par MTool

```
Exemple de JSON pris en charge :

{
    "砂漠迷宮へ": "砂漠迷宮へ",
    "巨大樹の森へ": "巨大樹の森へ",
    "マディラ氷雪山へ": "マディラ氷雪山へ",
    "アゲート鉱山へ": "アゲート鉱山へ"
}
```

Formats prévus à l'avenir

- Unity
- RPG Maker
- Ren'Py

---

## Exemple du workflow

```
JSON d'entrée

↓

Extraction du texte et découpage en plusieurs fichiers

↓

Traduction par lots

↓

Validation automatique

↓

Retour à un autre mode si nécessaire

↓

Génération du JSON traduit (pas encore disponible dans l'interface graphique)
```

---

## État du projet

### Déjà implémenté

- Analyseur JSON
- Pipeline de traduction IA
- Mapping des DTO
- Modes Fast / Safe / Strict
- Nouvelle tentative automatique et changement de mode
- Traduction par lots
- Reconstruction du JSON
- Reprise d'une traduction interrompue
- Interface graphique (GUI)

### En cours d'amélioration

- Qualité de la traduction
- Stabilité des longues tâches
- Optimisation des performances
- Génération automatique du JSON final après la traduction
- Messages d'erreur
- Gestion des prompts

### Prévu

- Fichier de configuration
- Support de nouveaux formats de jeux
