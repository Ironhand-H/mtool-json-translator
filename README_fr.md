# Traducteur JSON pour MTool

🌍 Langue :
[English](README.md) | [简体中文](README_zh.md) | **Français**

# AI Localization Toolkit

Outil de localisation de jeux vidéo basé sur **Java** et **Spring Boot**.

Le projet utilise l'API compatible OpenAI de **LM Studio** afin de traduire automatiquement des fichiers de ressources de jeux avec un modèle de langage exécuté localement.

> **État actuel : MVP (Minimum Viable Product)**

---

# Fonctionnalités

## ✔ Pipeline de traduction IA

- Traduction avec un LLM local
- API OpenAI Compatible (LM Studio)
- Température configurable
- Plusieurs stratégies de traduction

---

## ✔ Trois modes de traduction

### Fast

- Traduction par lots
- Performances maximales
- Adapté à la majorité des textes

### Safe

- Préservation de la structure JSON
- Validation du format retourné
- Meilleure robustesse

### Strict

- Traduction phrase par phrase
- Mode de secours
- Priorité à la fiabilité

---

## ✔ Traitement JSON

Actuellement pris en charge :

- Lecture des fichiers JSON MTool
- Extraction automatique des textes à traduire
- Conservation de la structure d'origine
- Génération automatique du fichier traduit

---

## ✔ Basculement automatique

```
Fast
   ↓
Safe
   ↓
Strict
```

Le pipeline passe automatiquement à une stratégie plus sûre lorsqu'une traduction échoue.

---

## ✔ Gestion des erreurs

Fonctionnalités déjà implémentées :

- Détection des timeout HTTP
- Retry automatique
- Changement automatique de stratégie
- Validation du JSON

---

# Architecture

```
Fichier JSON
      │
      ▼
 JSON Parser
      │
      ▼
 Translation Pipeline
      │
      ▼
 Translation Service
      │
      ▼
 LM Studio Client
      │
      ▼
 Modèle de langage local
      │
      ▼
 Result Parser
      │
      ▼
 JSON traduit
```

---

# Technologies

Backend

- Java
- Spring Boot
- RestClient
- Jackson
- Maven

IA

- LM Studio
- API OpenAI Compatible
- Qwen3

---

# Formats pris en charge

✔ MTool JSON

Formats prévus :

- Unity
- RPG Maker
- Ren'Py

---

# Workflow

```
Fichier JSON

↓

Extraction des textes

↓

Traduction par lots

↓

Validation automatique

↓

Fallback si nécessaire

↓

Reconstruction du JSON

↓

Fichier traduit
```

---

# État du projet

## Fonctionnalités terminées

- Analyse des fichiers JSON
- Pipeline complet de traduction
- Mapping DTO
- Gestion centralisée des prompts
- Modes Fast / Safe / Strict
- Retry automatique
- Fallback automatique
- Traduction par lots
- Reconstruction du JSON

---

## En cours d'amélioration

- Qualité de traduction
- Robustesse des longues exécutions
- Reprise après interruption
- Optimisation des performances

---

## Feuille de route

- Interface graphique (GUI)
- Fichier de configuration
- Support d'autres formats de jeux
- Glossaire / dictionnaire terminologique

---

# Prérequis

- Java 21+
- LM Studio
- API OpenAI Compatible activée

---

# Objectif

L'objectif du projet est de construire un outil de localisation assistée par IA, capable de traiter automatiquement différents formats de ressources de jeux vidéo.

Les priorités sont :

- une architecture extensible ;
- un pipeline de traduction robuste ;
- une gestion automatique des erreurs ;
- un support progressif de nouveaux formats de fichiers ;
- une future interface graphique destinée à simplifier son utilisation.
