# EECS2311 Software Development Project

## Description

A desktop app that can draw customizable [Venn diagrams](https://en.wikipedia.org/wiki/Venn_diagram).

## Getting Started

To use our application first you much launch it. To do so, head to the exact path Venn/src/main/java/views/MainApp.java

Click "Create New" to create a new Venn Diagram. Fill out all information and you work will be automatically saved to a CSV File with a name of your choice.

### Retriving your Previous Applciation

To retrieve a previously worked on application, run MainApp.java and click "Retrieve Existing" and select an Existing project.
 

## Folder Structure
* [[Venn]](/Venn)
  * [[src/main/java]](/Venn/src/main/java)
    * [[controllers]](/Venn/src/main/java/controllers)
    * [[models]](/Venn/src/main/java/models)
    * [[resources]](/Venn/src/main/java/resources)
    * [[views]](/Venn/src/main/java/views)
* [[artifacts]](/artifacts)
  * [VennCreate.jar](/artifacts/VennCreate.jar)
* [[docs]](/docs)
  * [[Venn-RD]](/docs/Venn-RD)
    * [Venn-RD.pdf](/docs/Venn-RD/Venn-RD.pdf)
  * [Venn-TD](/docs/Venn-TD.pdf)
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

## Documents

* [Requirements Document](/docs/Venn-RD/Venn-RD.pdf)
* [User Manual](/docs/Venn-UM/Venn-UM.pdf)
* [Testing Document](/docs/Venn-TD.pdf)

## Group Memebers
* Chidalu Agbakwa (216337784)
* Shangru Li (214488993)
* Jihal Patel (216376436)
* Robert Suwary (215446016)

## License

See [LICENSE](/LICENSE).
