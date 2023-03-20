SHELL := /bin/bash

compile:
	./gradlew clean build

publish-local: compile
	./gradlew publishToMavenLocal --stacktrace

publish:
	./gradlew publish --stacktrace