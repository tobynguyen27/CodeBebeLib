# CodeBebeLib

<img src="./src/main/resources/assets/codebebelib/icon.png" />

---

[![Maven repo](https://maven.tobynguyen.dev/api/badge/latest/snapshots/dev/tobynguyen27/codebebelib?name=Version)](https://maven.tobynguyen.dev)
[![Build and Publish](https://github.com/tobynguyen27/CodeBebeLib/actions/workflows/build-publish.yml/badge.svg)](https://github.com/tobynguyen27/CodeBebeLib/actions/workflows/build-publish.yml)

> [!WARNING]
> THIS LIBRARY IS A WORK IN PROGRESS! USE THIS LIBRARY AT YOUR OWN RISK!

A fork of [CodeChickenLib](https://github.com/TheCBProject/CodeChickenLib) for Fabric.

At the moment, this library provides various methods for doing **3D math**, **transformations** and **rendering**.

Basic usage
---

### Build setup

Add a maven repository to your `build.gradle`.

```groovy
maven {
    url "https://maven.tobynguyen.dev/snapshots"
}
```

Then declare CodeBebeLib as a dependency

```groovy
dependencies {
    modImplementation("dev.tobynguyen27:codebebelib:<version>")
}
```

You can read some examples of using CodeBebeLib with well-explained [here](https://github.com/tobynguyen27/CodeBebeLib/tree/main/src/testmod/java/dev/tobynguyen27/testmod).

Pull requests that add more examples are also welcomed! 

License
---

CodeBebeLib is licensed under the LGPL-2.1 license. Check [LICENSE](./LICENSE) for further details.

CodeBebeLib is a modified distribution of CodeChickenLib created by [ChickenBones](https://github.com/Chicken-Bones) and [covers1624](https://github.com/covers1624) which licensed under the LGPL-2.1 license.