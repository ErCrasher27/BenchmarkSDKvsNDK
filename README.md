Benchmark SDK vs NDK
=====================

Benchmark SDK vs NDK is a small Android app that lets you compare the performance of pure Kotlin (
SDK) implementations with native C++ (NDK) implementations of classic algorithms. The project is
intentionally simple and opinionated: it is meant as a **playground for benchmarking and extending
algorithms**, not as a full‑blown benchmarking framework.

The app currently ships with two reference algorithms:

- Merge sort
- Binary search

The real value, however, is in how easy it is to **add new algorithms and benchmark them** in both
SDK and NDK.

---

## Why this project exists

Android developers often hear “use NDK for performance”, but:

- JNI has a non‑trivial call overhead.
- For small workloads, native code can easily be *slower* than optimized JVM/Kotlin implementations.
- The trade‑offs (complexity, crashes, toolchain, CI) are not always obvious.

This app:

- Provides a concrete, runnable example of SDK vs NDK trade‑offs.
- Encourages experimentation with your own algorithms and data sizes.
- Shows how to structure an Android project so that adding new benchmark cases is straightforward.

The **goal** is not to crown an absolute winner, but to help you answer:
> “For *this* algorithm and *this* input size, does native actually make sense?”

***

## High‑level architecture

- **UI layer**
    - Home screen with teaching cards (high‑level guidelines about when NDK makes sense).
    - Benchmark screen where you choose algorithm and input size and run benchmarks.
    - Statistics screen that aggregates multiple runs (speedup, win percentages, average times).

- **Benchmark layer**
    - Kotlin implementations of algorithms (SDK).
    - Native C++ implementations exposed via JNI (NDK).
    - Shared benchmarking helpers that:
        - Generate inputs
        - Run both implementations on identical data
        - Measure execution time
        - Aggregate results across multiple runs

- **Extension points**
    - `// BENCHMARK-EXTENSION-POINT` comments mark the places where you plug in new algorithms.
    - `// TEACHING-CARD-EXTENSION-POINT` comments mark where you can add new teaching cards.

***

## Adding a new benchmark algorithm

The project is designed so that adding a new algorithm is as mechanical as possible. Look for the
`// BENCHMARK-EXTENSION-POINT` comments in the code: each one indicates a place where you need to
add or wire something for your new algorithm.

---

## Teaching cards and extending the “lessons”

The home screen shows “teaching cards” that summarize high‑level guidelines such as:

- When **not** to use NDK (small tasks, high JNI overhead, etc.).
- When NDK can be beneficial (large inputs, heavy numeric/codecs, reuse of existing C/C++
  libraries).

These cards are **optional** and purely educational. However, they are designed to be extended:

- `// TEACHING-CARD-EXTENSION-POINT` marks where the list of cards is defined.
- You can add new cards to:
    - Explain why a newly added algorithm behaves as observed.
    - Document patterns discovered from experiments (e.g. “Graph algorithms benefit more from NDK at
      input sizes above X”).

This keeps the app both a **benchmarking tool** and a **learning aid**.

---

## Usage

1. Choose an algorithm and input size.
2. Run the benchmark multiple times.
3. Once enough runs have been collected for a given (algorithm, input size) pair, open the
   statistics screen to see:
    - Speedup (SDK time / NDK time).
    - How often each side “wins”.
    - Average times for SDK and NDK.

Statistics are meant to be meaningful when you have **multiple runs of the same combination**, not
from a single measurement. The app enforces this by requiring a minimum number of runs before
statistics are available for a given configuration.

***

## Contributing

The project welcomes pull requests that:

- Add new algorithms and corresponding benchmarks (SDK and optionally NDK).
- Improve the UI/UX while keeping the extension points clear.
- Add teaching cards that explain when/why native code helps or hurts.

When contributing:

- Follow the existing naming conventions and keep strings in US English.
- Use the `// BENCHMARK-EXTENSION-POINT` and `// TEACHING-CARD-EXTENSION-POINT` markers as guides
  instead of introducing ad‑hoc wiring.
- Keep Kotlin and C++ implementations as comparable as reasonably possible (same inputs, comparable
  algorithms).

This structure should make it easy for anyone to fork the repo, add an algorithm, and open a pull
request without breaking existing functionality.