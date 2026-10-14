# ES — Mindustry Project

![Java](https://img.shields.io/badge/Java-17-1f2328?style=flat-square&logo=openjdk&logoColor=white)
![Course](https://img.shields.io/badge/FCT%20NOVA-Software%20Engineering-1f2328?style=flat-square)
![Year](https://img.shields.io/badge/2025%2F26-3rd%20year%20%C2%B7%201st%20semester-1f2328?style=flat-square)

My individual contributions to the team project of **Software Engineering (Engenharia de Software, ES)** at NOVA FCT, Bachelor in Computer Science and Engineering (LEI).

## About

Working as a fictional six-person software company, the team had to understand and extend
[**Mindustry**](https://github.com/Anuken/Mindustry), a large open-source Java tower-defense / RTS game,
following a Scrum-inspired process: weekly sprints, a rotating Scrum Master, peer-reviewed commits, a Scrum board and burndown charts.

The project had three milestones:

1. **Requirements**: user stories to extend the game and the project management setup.
2. **Architecture and quality**: reverse-engineering the codebase (use cases, code metrics, design patterns and code smells with refactoring proposals). Each member analysed one package; mine was `mindustry.ai`.
3. **Implementation**: implementing the user stories in the game, documented with use case, class and sequence diagrams, plus a demo.

The whole team project lives in [tpoeira123/SE2526_65871_67940_68087_68231_68264_68681](https://github.com/tpoeira123/SE2526_65871_67940_68087_68231_68264_68681).
This repository collects only the parts I wrote.

## My contributions

### Milestone 2 — analysis of the `mindustry.ai` package

| Report | Contents |
| --- | --- |
| [Code metrics](milestone2/code-metrics/Code%20Metrics.pdf) ([data](milestone2/code-metrics/metrics.csv)) | Dependency metrics from MetricsReloaded and the coupling problems they reveal |
| [Code smells](milestone2/code-smells/Code%20Smells.pdf) | Long Method, God Class (`ControlPathfinder`, 1600+ lines) and Magic Numbers, each with a refactoring proposal |
| [Design patterns](milestone2/design-patterns/Design%20Patterns%20Identification%20Report.pdf) | Strategy, Factory Method and Observer, identified in the code |
| [Use cases](milestone2/use-cases/Use%20Cases.pdf) | Use case diagram and textual descriptions |

### Milestone 3 — Planet Statistics (User Story 3)

> **As a** player **I want** to see statistics tracked individually for each planet **so that** I can track my progress on each planet separately.

- A new `PlanetStats` model keeps per-planet counters: player deaths, enemies destroyed, waves, units created, buildings built, destroyed and deconstructed, time played and distance walked.
- `Logic` and `Control` listen to game events (`BlockBuildEndEvent`, `UnitDestroyEvent`, `GameOverEvent`, …) and update the current planet's stats, which `Planet` saves with `Core.settings` so they survive restarts.
- A new `PlanetStatsDialog` shows them with animated counters. Open it from the planet selection screen with the **Planet Statistics** button or the <kbd>S</kbd> key.
- **Bonus story — campaign progress:** the planet selection screen shows the sectors captured out of the total, with a progress bar and a percentage.

Documentation: [user story, use cases and diagrams](milestone3/UserStory3.md) · [bonus story](milestone3/UserStoryBonus.md) · [implementation notes](milestone3/PlanetStatsNotes.md) · [demo video](milestone3/demo-user-story-3.mp4)

The milestone documents are in Portuguese, as they were submitted.

## Code

[`mindustry/`](mindustry) contains the seven Mindustry files I created or changed, with the same paths as in the game
(`core/src/mindustry/...`). All my changes are also in a single patch, [`planet-stats.patch`](mindustry/planet-stats.patch).

To run the game with the feature, apply the patch to the Mindustry v8 (beta) source the project was based on and start the desktop build (JDK 17 to 21):

```bash
git clone https://github.com/Anuken/Mindustry.git
cd Mindustry
git checkout a01e4be75d                                    # the version used in the project (Dec 2025)
git apply /path/to/ES-Mindustry-Project/mindustry/planet-stats.patch
./gradlew desktop:run
```

If Gradle can't download the `Arc` dependency, clone [Anuken/Arc](https://github.com/Anuken/Arc) next to the `Mindustry` folder and check out commit `52269919b2`: the build then uses it automatically.

Then choose **Play → Campaign** and press <kbd>S</kbd> on the planet screen.

## What I learned

- Finding my way around a large, unfamiliar codebase, and using metrics to decide where to look.
- Spotting design patterns and code smells in real code, not in textbook examples.
- Adding a feature by hooking into an existing event system instead of rewriting the code around it.
- Documenting a feature with UML (use case, class and sequence diagrams).
- Working with Scrum in a six-person team: sprints, peer reviews and pull-based Git workflows.

## Team

- **65871 — Miguel Carmo** — [MCCProgrammer](https://github.com/MCCProgrammer)
- 68681 — Tomás Alves — [Alvesss04](https://github.com/Alvesss04)
- 67940 — Francisco Pereira — [Loupaaa](https://github.com/Loupaaa)
- 68264 — Manuel Figueiredo — [ManuFigas](https://github.com/ManuFigas)
- 68087 — Francisco Figueiredo — [Franiquilador](https://github.com/Franiquilador)
- 68231 — Tiago Poeira — [tpoeira123](https://github.com/tpoeira123)

## License

Mindustry is © Anuken and licensed under the [GNU GPL v3](mindustry/LICENSE). The code in `mindustry/` is derived from it and is shared under the same license.

---

<sub>The project statement is not included. This repository is shared for portfolio purposes.</sub>
