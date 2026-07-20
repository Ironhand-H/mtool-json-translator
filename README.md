# MTool JSON Translator

🌍 Language:
**English** | [简体中文](README_zh.md) | [Français](README_fr.md)

# AI Localization Toolkit

A Java-based game localization tool powered by local Large Language Models.

The project translates game resource files through the OpenAI-compatible API, provided by LM Studio or online model. It is designed for offline AI-assisted localization with a focus on automated processing.

> **Current status:** a realease is availiable. This realease allows to run the software even on a PC with no JDK installed.

---

## Features

### ✔ Translation Modes

This tool have 3 diffrent modes which presents the different JSON format to be sent to the AI API. It is designed in order to save tokens. The pipeline automatically switches to a safer strategy when translation fails.

That is, first a json with fast format will be sent. If it failed, then a safe format json will be sent. If these 2 modes are all failed, the strict mode is the last ensurence. Strict mode will sent the translate request line by line, with a 3 times retry if the model goes mad.

```
Fast -> Safe -> Strict
```

- **Fast**
  - Batch translation
  - Highest throughput
```
Fast mode example:
        ["こんにちは","EV001","flower.png","100"]
```
- **Safe**
  - JSON-aware translation
  - Preserves object structure
```
Safe mode example:
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
  - Sentence-by-sentence fallback
  - Maximum translation reliability
```
Strict mode example:
        "こんにちは"
```

### How to use

#### Requirements

All you need is a JSON file that need to be translated and an enabled OpenAI Compatible API

#### Fast use

 - First choose the file that need to be translated. Then click to create a project. A new directory "AI Localization Toolkit" will be created in the "Documents" as the project. The file will be splited and saved in "tasks" under "AI Localization Toolkit".
   <img width="957" height="467" alt="image" src="https://github.com/user-attachments/assets/a0ba69fc-a923-4ce9-8223-54a714799f9a" />

 - Then click translate, and now it will work!
   <img width="876" height="432" alt="image" src="https://github.com/user-attachments/assets/36274cc5-b5af-4788-8661-7732bf68fc07" />

 - If you have already a project in progress, click on continue on the home page then clock on translate.

### ✔ JSON Processing

- Read JSON files
- Extract translatable text
- Preserve original JSON structure
- Generate translated JSON automatically

### ✔ Error Handling

- HTTP timeout detection (5 mins)
- Automatic retry(3 times max)
- Translation mode fallback
- JSON validation

---

## Current Architecture

```
JSON File
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
 Local LLM
      │
      ▼
 Result Parser
      │
      ▼
 Translated JSON
```

---

## Technologies

- Java
- Spring Boot
- RestClient
- Jackson
- Maven
- Electron

Local AI used for testing

- LM Studio
- OpenAI Compatible API
- Qwen3

IDE and other tools used

- Intellij
- Postman
- VS Code

## Current Supported Format

- ✔ MTool generated JSON file
```
JSON file supported example:
   {"砂漠迷宮へ": "砂漠迷宮へ",
    "巨大樹の森へ": "巨大樹の森へ",
    "マディラ氷雪山へ": "マディラ氷雪山へ",
    "アゲート鉱山へ": "アゲート鉱山へ"}
```
Planned in the future

- Unity
- RPG Maker
- Ren'Py

---

## Example Workflow

```
Input JSON

↓

Extract text and split to seperated files

↓

Batch translation

↓

Automatic validation

↓

Fallback if needed

↓

Generate translated JSON
```

---

## Project Status

Implemented

- JSON parser
- AI translation pipeline
- DTO mapping
- Prompt management
- Fast / Safe / Strict modes
- Automatic retry
- Automatic fallback
- Batch translation
- JSON reconstruction
- Checkpoint / resume
- Desktop GUI

Currently Improving

- Translation quality
- Long task stability
- Performance optimization
- Forge file after the translation
- Error alert

Planned

- Configuration file
- Additional game formats

