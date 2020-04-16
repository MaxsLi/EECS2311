# EECS2311 Software Development Project

## Description
![ezgif com-video-to-gif](https://user-images.githubusercontent.com/50505942/79400126-4a131880-7f53-11ea-99bd-11883d83da3e.gif)
A desktop app that can draw customizable [Venn diagrams](https://en.wikipedia.org/wiki/Venn_diagram).

## Getting Started
Follow the installation intructions below, when installed and on the home page as shown above, click `Create New` to start a new VennCreate Project.

### Retrieve an Existing Project
On the home menu showed above, click "Get Existing". Your file explorer will then be opened and you may search for an existing venn diagram.

## Folder Structure
* [[VennCreate]](/)
  * [[src/main]](/src/main)
    * [[java]](/src/main/java)
      * [[controllers]](/src/main/java/controllers)
      * [[models]](/src/main/java/models)
      * [[utilities]](/src/main/java/utilities)
      * [[views]](/src/main/java/views)
    * [[resources]](/src/main/resources)
  * [[src/test]](/src/test)
    * [[java]](/src/test/java)
      * [[tests]](/src/test/java/tests)
* [[docs]](/docs)
  * [[Venn-RD]](/docs/Venn-RD)
    * [Venn-RD.pdf](/docs/Venn-RD/Venn-RD.pdf)
  * [Venn-TD.pdf](/docs/Venn-TD.pdf)
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

Please find the latest release in [here](https://github.com/MaxsLi/EECS2311/releases).

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
