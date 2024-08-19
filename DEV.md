# Developer guide

## IDE

Feel free to use the one you prefer: JAVA is IDE agnostic after all :)

This doc will be based on IntelliJ feel free to adapt for your IDE.

Required plugins:

* lombok

## Code style

This project follow google java style guide: https://google.github.io/styleguide/javaguide.html

To ensure the code style is applied, mvn will automatically format the files at each execution.

You can if desired install the auto formatter in your IDE: https://github.com/google/google-java-format/blob/master/README.md#using-the-formatter

A few useful command lines to run before opening a PR (otherwise the CI will fail):

`mvn com.spotify.fmt:fmt-maven-plugin:format` - Autofix java files so that they follow google code style
`mvn license:format` - Autofix missing headers in files


## Makefile is your friend

If you want to run the project in pure CLI there is a `Makefile` available.

You can run:
* `make codestyle`
* `make jar`
* `make test`

These are a convenient way to invoke some maven commands and bash scripts. But if you are familiar
maven already, feel free to jump directly to maven general commands ;)
