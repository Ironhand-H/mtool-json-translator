# MTool JSON 翻译器

🌍 语言：
[English](README.md) | **简体中文** | [Français](README_fr.md)

# AI Localization Toolkit

一个基于 **Java + Spring Boot** 开发的游戏 AI 本地化工具。

本项目通过 **LM Studio 提供的 OpenAI Compatible API** 调用本地大语言模型，对游戏资源文件进行自动翻译，目标是构建一个稳定、可扩展的离线 AI 本地化工具。

> **当前状态：MVP（最小可用版本）**

---

# 功能

## ✔ AI 翻译流程

- 支持本地大语言模型翻译
- 基于 LM Studio OpenAI Compatible API
- 可配置 Temperature
- 多种翻译策略

---

## ✔ 三种翻译模式

### Fast

- 批量翻译
- 速度最快
- 适合绝大多数文本

### Safe

- JSON 结构保护
- 保证返回合法 JSON
- 提高稳定性

### Strict

- 单句翻译
- 最终兜底方案
- 优先保证翻译成功

---

## ✔ JSON 处理

目前支持：

- 读取 MTool JSON
- 提取待翻译文本
- 保留原始 JSON 结构
- 自动生成翻译后的 JSON 文件

---

## ✔ 自动降级机制

```
Fast
   ↓
Safe
   ↓
Strict
```

当翻译失败时，程序会自动切换到更安全的翻译策略。

---

## ✔ 异常处理

目前已实现：

- HTTP Timeout 检测
- 自动重试（Retry）
- 翻译模式自动切换
- JSON 格式校验

---

# 项目架构

```
JSON 文件
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
 本地大语言模型
      │
      ▼
 Result Parser
      │
      ▼
 翻译后的 JSON
```

---

# 技术栈

后端

- Java
- Spring Boot
- RestClient
- Jackson
- Maven

AI

- LM Studio
- OpenAI Compatible API
- Qwen3

---

# 当前支持

✔ MTool JSON

计划支持：

- Unity
- RPG Maker
- Ren'Py

---

# 工作流程

```
输入 JSON

↓

提取文本

↓

批量翻译

↓

自动校验

↓

失败自动降级

↓

重建 JSON

↓

生成翻译文件
```

---

# 当前完成进度

## 已完成

- JSON 文件解析
- AI 翻译流程
- DTO 映射
- Prompt 管理
- Fast / Safe / Strict 三种翻译模式
- 自动重试
- 自动降级
- 批量翻译
- JSON 重建

---

## 正在优化

- 翻译质量
- 长时间任务稳定性
- 断点续翻
- 性能优化

---

## 后续计划

- 图形界面（GUI）
- 配置文件
- 更多游戏格式支持
- 术语词典
- 更多本地模型适配

---

# 运行环境

- Java 21+
- LM Studio
- 开启 OpenAI Compatible API

---

# 项目目标

本项目不仅仅是一个 JSON 翻译脚本，而是希望构建一个能够用于游戏本地化工作的 AI 工具。

开发重点包括：

- 稳定可靠的翻译流程
- 可扩展的文件解析架构
- 自动容错与降级机制
- 支持多种游戏资源格式
- 后续提供桌面 GUI，提升实际使用体验

