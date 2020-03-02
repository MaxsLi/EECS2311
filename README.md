# EECS2311 Software Development Project

## Description

A desktop app that can draw customizable [Venn diagrams](https://en.wikipedia.org/wiki/Venn_diagram).

* test

## Folder Structure
* [[Venn]](/Venn)
  * [[src/main/java]](/Venn/src/main/java)
    * [[application]](/Venn/src/main/java/application)
    * [[controllers]](/Venn/src/main/java/controllers)
    * [[models]](/Venn/src/main/java/models)
    * [[resources]](/Venn/src/main/java/resources)
* [[artifacts]](/artifacts)
  * [VennCreate.jar](/artifacts/VennCreate.jar)
* [[docs]](/docs)
  * [[Venn-RD]](/docs/Venn-RD)
    * [Venn-RD.pdf](/docs/Venn-RD/Venn-RD.pdf)
  * [[Venn-UM]](/docs/Venn-UM)
    * [Venn-UM.pdf](/docs/Venn-UM/Venn-UM.pdf)
  * [ideas.md](/docs/ideas.md)

## User Interface
```
MainFrame
┌─────────────────────────────────────┐
│               MenuBar               │
├─────────────────────────────────────┤
│                                     │
│                                     │
│              shapeScence            │
│                                     │
│                                     │
└─────────────────────────────────────┘
```
## Design Pattern

[Model–View–Controller](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

```
┌───────────┐     ┌───────────┐     ┌───────────────┐
│   Model   │     │   View    │     │  Controller   │
└───────────┘     └───────────┘     └───────────────┘
```

* **Model**: Mathematical sets serves as the primary logic of Venn diagrams.
* **View**: Implemented using JavaFX.
* **Controller**: Mouse and key listener.

## Installation

Please download the executable jar [here](/artifacts/VennCreate.jar).

To run, either double click or use command line `java -jar ./VennCreate.jar`.

## Usage


## Group Memebers
* Chidalu Agbakwa (216337784)
* Shangru(Max) Li (214488993)
* Jihal Patel (216376436)
* Robert (Robbie) Suwary (215446016)

## License

See [LICENSE](/LICENSE).
