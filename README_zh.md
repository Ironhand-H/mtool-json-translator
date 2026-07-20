# MTool JSON 翻译器

🌍 语言：
[English](README.md) | **简体中文** | [Français](README_fr.md)

# AI Localization Toolkit

一款基于 Java 开发、由本地大语言模型驱动的游戏本地化工具。

本项目通过 LM Studio 或在线模型提供的 OpenAI Compatible API 对游戏资源文件进行翻译，专注于离线 AI 辅助本地化，并强调自动化处理流程。

> **当前状态：** 已提供可下载的 Release 版本。即使目标电脑未安装 JDK，也可以直接运行本软件。

---

## 功能

### ✔ 翻译模式

本工具提供 **3 种翻译模式**，分别对应发送给 AI API 的不同 JSON 格式，目的是尽可能节省 Token，并提高翻译稳定性。

翻译流程会自动根据失败情况切换策略。

首先会使用 **Fast** 模式进行批量翻译；如果失败，则自动切换到 **Safe** 模式；如果仍然失败，则进入最后的 **Strict** 模式。

Strict 模式会逐句发送翻译请求，并在模型输出异常时最多自动重试 3 次。

```
Fast -> Safe -> Strict
```

- **Fast**
  - 批量翻译
  - 最高翻译速度

```
Fast 模式示例：
        ["こんにちは","EV001","flower.png","100"]
```

- **Safe**
  - 基于 JSON 结构进行翻译
  - 保留对象结构

```
Safe 模式示例：
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
  - 逐句翻译作为最终回退方案
  - 提供最高的翻译可靠性

```
Strict 模式示例：
        "こんにちは"
```

### 使用方法

#### 运行要求

你只需要准备：

- 一个需要翻译的 JSON 文件
- 一个可用的 OpenAI Compatible API

#### 使用步骤

- 首先选择需要翻译的文件，然后点击 **Create Project**。

  程序会在 **文档 (Documents)** 目录下创建一个名为 **AI Localization Toolkit** 的项目目录。

  原始 JSON 文件会被拆分，并保存到 **AI Localization Toolkit/tasks** 目录中。

  <img width="957" height="467" alt="image" src="https://github.com/user-attachments/assets/a0ba69fc-a923-4ce9-8223-54a714799f9a" />

- 点击 **Translate** 即可开始翻译。

  <img width="876" height="432" alt="image" src="https://github.com/user-attachments/assets/36274cc5-b5af-4788-8661-7732bf68fc07" />

- 如果之前已有未完成的项目，可在首页点击 **Continue**，随后点击 **Translate** 继续之前的任务。

### ✔ JSON 处理

- 读取 JSON 文件
- 提取可翻译文本
- 保留原始 JSON 结构
- 自动生成翻译后的 JSON

### ✔ 错误处理

- HTTP 超时检测（5 分钟）
- 自动重试（最多 3 次）
- 自动切换翻译模式
- JSON 校验

---

## 当前架构

```
JSON 文件
      │
      ▼
 JSON 解析器
      │
      ▼
 翻译流水线
      │
      ▼
 翻译服务
      │
      ▼
 LM Studio Client
      │
      ▼
 本地大语言模型
      │
      ▼
 结果解析器
      │
      ▼
 翻译后的 JSON
```

---

## 使用技术

- Java
- Spring Boot
- RestClient
- Jackson
- Maven
- Electron
- React

用于测试的本地 AI

- LM Studio
- OpenAI Compatible API（chat/completions）
- 模型：qwen/qwen3-14b

开发工具

- IntelliJ IDEA
- Postman
- VS Code

## 当前支持格式

- ✔ MTool 生成的 JSON 文件

```
支持的 JSON 示例：

{
    "砂漠迷宮へ": "砂漠迷宮へ",
    "巨大樹の森へ": "巨大樹の森へ",
    "マディラ氷雪山へ": "マディラ氷雪山へ",
    "アゲート鉱山へ": "アゲート鉱山へ"
}
```

未来计划支持

- Unity
- RPG Maker
- Ren'Py

---

## 工作流程示例

```
输入 JSON

↓

提取文本并拆分为多个文件

↓

批量翻译

↓

自动校验

↓

必要时自动回退

↓

生成翻译后的 JSON（GUI 暂未支持）
```

---

## 项目状态

### 已实现

- JSON 解析器
- AI 翻译流水线
- DTO 映射
- Fast / Safe / Strict 三种翻译模式
- 自动重试与回退
- 批量翻译
- JSON 重构
- 断点续传 / 继续翻译
- 桌面 GUI

### 正在改进

- 翻译质量
- 长时间任务稳定性
- 性能优化
- 翻译完成后自动生成最终 JSON 文件
- 错误提示
- Prompt 管理

### 计划中

- 配置文件
- 支持更多游戏格式
