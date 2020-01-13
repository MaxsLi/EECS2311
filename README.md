# EECS2311 Software Development Project
a
## Description

A desktop app that can draw customizable [Venn diagrams](https://en.wikipedia.org/wiki/Venn_diagram).

## Table of Contents
* [[Venn]](/Venn)
  * [[src/main/java]](/Venn/src/main/java)
    * [[controllers]](/Venn/src/main/java/controllers)
    * [[models]](/Venn/src/main/java/models)
    * [[venn]](/Venn/src/main/java/venn)
      * [Main.java](/Venn/src/main/java/venn/Main.java)
    * [[views]](/Venn/src/main/java/views)
* [[docs]](/docs)

## User Interface
```
MainFrame
┌─────────────────────────────────────┐
│               MenuBar               │
├─────────────────────────────────────┤
│                                     │
│                                     │
│              DiagramPanel           │
│                                     │
│                                     │
└─────────────────────────────────────┘
```
## Framework

[Model–View–Controller](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

```
┌───────────┐     ┌───────────┐     ┌───────────────┐
│   Model   │     │   View    │     │  Controller   │
└───────────┘     └───────────┘     └───────────────┘
```

* **Model**: Mathematical sets serves as the primary logic of Venn diagrams.
* **View**: Implemented using JFrame.
* **Controller**: Button click and key press action listener.

## Installation

## Usage

## Group Memebers
* Chidalu Agbakwa (216337784)
* Shangru(Max) Li (214488993)
* Jihal Patel (216376436)
* Robert (Robbie) Suwary (215446016)

## License

See [LICENSE](/LICENSE).
