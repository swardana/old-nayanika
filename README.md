# Nayanika

Nayanika is a picture viewer application. 

## Building Nayanika

In case you want to fork and build your local version of Nayanika.

### Prerequisites

- A recent version of JDK 16 (The version provided by [AdoptOpenJDK](https://adoptopenjdk.net/) is an excellent choice).
- Apache Maven 3.8.1.

### How to run Nayanika

Before starting the app, all dependencies must be installed.

```bash
mvn install
```

The Nayanika can be started with Maven.

```bash
mvn exec:exec
```

### How to build Nayanika installer

Build the Nayanika installer based on host operating system.

```bash
mvn install -Pinstaller
```

This will build Nayanika at `target/installer` folder.

## Contributing

Help is welcome.
For major changes, please send an email first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Support

Any donations to support the project are accepted via.

- [Patreon](https://www.patreon.com/swardana)
- [Paypal](https://www.paypal.me/sukmawardana/10)

## License

[GNU General Public License, version 3 or later](COPYING)