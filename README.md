# MTool JSON Translator

🌍 Language:
**English** | [简体中文](README_zh.md) | [Français](README_fr.md)

# AI Localization Toolkit

A Java-based game localization tool powered by local Large Language Models.

The project translates game resource files through the OpenAI-compatible API provided by LM Studio. It is designed for offline AI-assisted localization with a focus on stability, extensibility and automated processing.

> **Current status:** Prototype / MVP

---

## Features

### ✔ AI Translation Pipeline

- Local LLM translation through LM Studio
- OpenAI Compatible API
- Configurable temperature
- Multiple translation strategies

### ✔ Translation Modes

- **Fast**
  - Batch translation
  - Highest throughput

- **Safe**
  - JSON-aware translation
  - Preserves object structure

- **Strict**
  - Sentence-by-sentence fallback
  - Maximum translation reliability

### ✔ JSON Processing

- Read MTool JSON files
- Extract translatable text
- Preserve original JSON structure
- Generate translated JSON automatically

### ✔ Automatic Fallback

```
Fast
   ↓
Safe
   ↓
Strict
```

The pipeline automatically switches to a safer strategy when translation fails.

### ✔ Error Handling

- HTTP timeout detection
- Automatic retry
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

Local AI

- LM Studio
- OpenAI Compatible API
- Qwen3

---

## Current Supported Format

- ✔ MTool JSON

Planned

- Unity
- RPG Maker
- Ren'Py

---

## Example Workflow

```
Input JSON

↓

Extract text

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

Currently Improving

- Translation quality
- Long task stability
- Checkpoint / resume
- Performance optimization

Planned

- Desktop GUI
- Configuration file
- Additional game formats
- Terminology dictionary

---

## Requirements

- Java 21+
- LM Studio
- OpenAI Compatible API enabled
